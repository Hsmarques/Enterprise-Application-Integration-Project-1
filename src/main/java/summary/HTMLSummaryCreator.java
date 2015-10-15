package summary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;

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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import org.xml.sax.SAXException;

public class HTMLSummaryCreator implements MessageListener {
	private ConnectionFactory cf;
	private Topic d;

	public HTMLSummaryCreator() throws NamingException {
		this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
		this.d = InitialContext.doLookup("jms/topic/TopicProject");
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
		try (JMSContext jcontext = cf.createContext("summary", "secret");) {
			jcontext.setClientID("summary");
			JMSConsumer consumer = jcontext.createDurableConsumer(d, "summary");
			consumer.setMessageListener(this);
			System.out.println("Press enter to finish...");
			System.in.read();
			consumer.close();
			
		} catch (JMSRuntimeException | IOException re) {
			re.printStackTrace();
		}
	}

	public static void main(String[] args) throws NamingException {
		HTMLSummaryCreator summary = new HTMLSummaryCreator();
		summary.launch_and_wait();
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
			addXslt(xml);
		} catch (SAXException | IOException e) {
			System.out.println("XML file is NOT valid");
			System.out.println("Reason: " + e.getLocalizedMessage());
		}
	}
	
	private void addXslt(String msg){
        
        try {

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource("TablePrinter.xsl"));
            String name = Long.toString(Calendar.getInstance().getTimeInMillis());
            // create new directory, if it doesn't exist
            File theDir = new File("htmls");
            if (!theDir.exists()) {
                try{
                    theDir.mkdir();
                }
                catch(SecurityException se){
                    se.printStackTrace();
                }
            }
            
            transformer.transform(new javax.xml.transform.stream.StreamSource(new StringReader(msg)), 
                    new javax.xml.transform.stream.StreamResult( new FileOutputStream("htmls/" + name + ".html")));
        }
        catch (Exception e) {
            e.printStackTrace( );
        }
        
    }

}