package priceReqKeep;

import java.io.IOException;
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
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PriceRequester implements MessageListener {

	JMSContext jcontext;
	private ConnectionFactory cf;
	private Destination d;

	public PriceRequester() throws NamingException {
		this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
		this.d = InitialContext.doLookup("jms/queue/QueueProject");
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
		try {
			JMSContext jcontext = cf.createContext("pricereq", "secret");
			jcontext.setClientID("pricereq");
			JMSConsumer consumer = jcontext.createDurableConsumer((Topic) d, "pricereq");
			consumer.setMessageListener(this);
			System.out.println("Press enter to finish...");
			System.in.read();
		} catch (JMSRuntimeException | IOException re) {
			re.printStackTrace();
		}
	}

	public void createTempDest(String brand, String model) {
		JMSProducer jmsproducer = jcontext.createProducer();
		TextMessage msg = jcontext.createTextMessage();
		Destination replyto = jcontext.createTemporaryQueue();
		try {
			msg.setJMSReplyTo(replyto);
			msg.setText("OH OH OH");

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jmsproducer.send(replyto, msg);

		JMSConsumer jmsconsumer = jcontext.createConsumer(replyto);
		TextMessage reply = (TextMessage) jmsconsumer.receive();
		try {
			System.out.println("Sender got back: " + reply.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws NamingException {
		PriceRequester pricereq = new PriceRequester();
		pricereq.launch_and_wait();
	}

}
