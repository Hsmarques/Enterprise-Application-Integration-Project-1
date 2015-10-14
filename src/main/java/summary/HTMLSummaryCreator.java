package summary;

import java.io.File;
import java.io.IOException;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import org.xml.sax.SAXException;

public class HTMLSummaryCreator implements MessageListener {
		private ConnectionFactory cf;
		private Destination d;

		public HTMLSummaryCreator() throws NamingException {
			this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
			this.d = InitialContext.doLookup("jms/topic/TopicProject");
		}

		@Override
		public void onMessage(Message msg) {
			TextMessage tmsg = (TextMessage) msg;
			try {
				System.out.println("Got message: " + tmsg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

		public void launch_and_wait() {
			try (JMSContext jcontext = cf.createContext("summary", "secret");) {
				jcontext.setClientID("summary");
				JMSConsumer consumer = jcontext.createDurableConsumer((Topic) d,"summary");
				consumer.setMessageListener(this);
				System.out.println("Press enter to finish...");
				System.in.read();
			} catch (JMSRuntimeException | IOException re) {
				re.printStackTrace();
			}
		}

		public static void main(String[] args) throws NamingException {
			HTMLSummaryCreator summary = new HTMLSummaryCreator();
			summary.launch_and_wait();
		}
		
		public void validator(String xml){
			Source schemaFile = new StreamSource(new File("smartphone.xsd"));
			Source xmlFile = new StreamSource(new File("web.xml"));
			SchemaFactory schemaFactory = SchemaFactory
			    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			try {
			  validator.validate(xmlFile);
			  System.out.println(xmlFile.getSystemId() + " is valid");
			} catch (SAXException e) {
			  System.out.println(xmlFile.getSystemId() + " is NOT valid");
			  System.out.println("Reason: " + e.getLocalizedMessage());
			}
		}

	}