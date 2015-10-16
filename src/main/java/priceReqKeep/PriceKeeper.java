package priceReqKeep;

import java.io.IOException;
import java.io.StringReader;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import JMS.ReceiverTopic;
import generated.Smartphone;
import generated.Smartphones;

public class PriceKeeper implements MessageListener {

	private ConnectionFactory cf;
	private Destination dest;
	private JMSContext jcontext_queue;
	private JMSConsumer consumer_queue;
	private Smartphones smartphones;

	public PriceKeeper() throws NamingException {
		this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
		this.dest = InitialContext.doLookup("jms/queue/QueueProject");
	}

	@Override
	public void onMessage(Message msg) {
		try {
			System.out.println("asdf");
			JMSConsumer mc = jcontext_queue.createConsumer(dest);
			System.out.println("sera?");
			TextMessage textmsg = (TextMessage) mc.receive();
			System.out.println("oi");
			TextMessage reply = jcontext_queue.createTextMessage(getSmartphone(textmsg.getText()));
			System.out.println("parou");
			JMSProducer mp = jcontext_queue.createProducer();
			mp.send(msg.getJMSReplyTo(),"sup");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void launch_and_wait() {
		try {
			jcontext_queue = cf.createContext("pricereq", "secret");
			consumer_queue = jcontext_queue.createConsumer(dest);
			consumer_queue.setMessageListener(this);
		} catch (JMSRuntimeException re) {
			re.printStackTrace();
		}
	}
	
	public String getSmartphone(String msg){
		boolean found = false;
		
		if(smartphones == null){
			return "Lista de Smartphones vazia";
		}
		
		StringBuilder stringb = new StringBuilder();
		
		for(Smartphone smartphone: smartphones.getSmartphone()){
			if(smartphone.getModel().toLowerCase().indexOf(msg.toLowerCase().trim())!=-1){
				stringb.append("Modelo: "+smartphone.getModel()+"\n");
				stringb.append("Preço: "+smartphone.getPrice()+"\n");
				if(!found){
					found = true;
				}
			}
		}
		
		if(!found){
			return "Não encontrou Smartphones com essa descrição!";
		}
		
		return stringb.toString();
		
	}

	public void unmarshall(String xml) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Smartphones.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			smartphones = (Smartphones) jaxbUnmarshaller.unmarshal(new StringReader(xml));
			System.out.println("Data loaded");

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws NamingException {
		PriceKeeper pricekeeper = new PriceKeeper();
		pricekeeper.launch_and_wait();
		ReceiverTopic receiverTopic = new ReceiverTopic();
		receiverTopic.launch_and_wait();
		try {
			System.out.println("Press enter to finish...");
			System.in.read();
			receiverTopic.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
