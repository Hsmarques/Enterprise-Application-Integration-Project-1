package JMS;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.jms.ConnectionFactory;
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
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import priceReqKeep.PriceKeeper;

public class ReceiverTopic extends PriceKeeper implements MessageListener {
	
	JMSContext jcontext;
	private ConnectionFactory cf;
	private Topic topic;
	private static JMSContext jcontext_topic;
	private static JMSConsumer consumer_topic;
	

	public ReceiverTopic() throws NamingException {
		this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
		this.topic = InitialContext.doLookup("jms/topic/TopicProject");
		
	}

	@Override
	public void onMessage(Message msg) {
		TextMessage tmsg = (TextMessage) msg;
		try {
			validator(tmsg.getText());
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
			
		} catch (JMSRuntimeException re) {
			re.printStackTrace();
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
			unmarshall(xml);
		} catch (SAXException | IOException e) {
			System.out.println("XML file is NOT valid");
			System.out.println("Reason: " + e.getLocalizedMessage());
		}
	}
	
	public void close(){
		jcontext_topic.close();
	}

}
