package priceReqKeep;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import generated.Smartphones;

public class PriceKeeper implements MessageListener {

		private ConnectionFactory cf;
		private Topic topic;
		private Destination dest;
		private static JMSContext jcontext_topic;
		private static JMSConsumer consumer_topic;
		private JMSContext jcontext_queue;
		private JMSConsumer consumer_queue;
		private Smartphones smartphones;

		public PriceKeeper() throws NamingException {
			this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
			this.topic = InitialContext.doLookup("jms/topic/TopicProject");
			this.dest = InitialContext.doLookup("jms/queue/QueueProject");
		}

		@Override
		public void onMessage(Message msg) {
			TextMessage tmsg = (TextMessage) msg;
			try {
				validator(tmsg.getText());
				System.out.println("sup");
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

		public void launch_and_wait() {
			try {
				jcontext_topic = cf.createContext("pricereq", "secret");
				jcontext_topic.setClientID("pricereq");
				consumer_topic = jcontext_topic.createDurableConsumer( topic, "pricereq");
				consumer_topic.setMessageListener(this);
				
				jcontext_queue = cf.createContext("pricereq", "secret");
				consumer_queue = jcontext_queue.createConsumer(dest);
				consumer_queue.setMessageListener(this);
			} catch (JMSRuntimeException re) {
				re.printStackTrace();
			}
		}
		
		public void unmarshall(String xml){
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Smartphones.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                smartphones = (Smartphones) jaxbUnmarshaller.unmarshal(new StringReader(xml));
                System.out.println("Data loaded");

          } catch (JAXBException e) {
                e.printStackTrace();
          }
		}
		
		public void validator(String xml) {
			Source schemaFile = new StreamSource(new File("smartphone.xsd"));
			Source xmlFile = new StreamSource(new StringReader(xml));
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			try {
				Schema schema = schemaFactory.newSchema(schemaFile);
				Validator validator = schema.newValidator();
				validator.validate(xmlFile);
				System.out.println("XML file is valid");
			} catch (SAXException | IOException e) {
				System.out.println("XML file is NOT valid");
				System.out.println("Reason: " + e.getLocalizedMessage());
			}
		}
		

		public static void main(String[] args) throws NamingException {
			PriceKeeper pricekeeper = new PriceKeeper();
			pricekeeper.launch_and_wait();
			try {
				System.out.println("Press enter to finish...");
				System.in.read();
				jcontext_topic.close();
				consumer_topic.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}



}
