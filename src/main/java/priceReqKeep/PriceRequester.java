package priceReqKeep;

import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PriceRequester{

	JMSContext jcontext;
	private ConnectionFactory cf;
	private Destination d;
	static Scanner inputkey = new Scanner(System.in);

	public PriceRequester() throws NamingException {
		this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
		this.d = InitialContext.doLookup("jms/queue/QueueProject");
	}


	public void createTempDest(String request) {
		jcontext = cf.createContext("pricereq", "secret");
		JMSProducer jmsproducer = jcontext.createProducer();
		TextMessage msg = jcontext.createTextMessage();
		Destination replyto = jcontext.createTemporaryQueue();
		try {
			msg.setJMSReplyTo(replyto);
			msg.setText(request);
			jmsproducer.send(d, msg);

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JMSConsumer jmsconsumer = jcontext.createConsumer(replyto);
		TextMessage reply = (TextMessage) jmsconsumer.receive();
		try {
			System.out.println("Resultado da procura:\n" + reply.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws NamingException {
		PriceRequester pricereq = new PriceRequester();
		System.out.println("Introduza a marca e/ou modelo do Smartphone:");
		pricereq.createTempDest(inputkey.nextLine());
	}

}
