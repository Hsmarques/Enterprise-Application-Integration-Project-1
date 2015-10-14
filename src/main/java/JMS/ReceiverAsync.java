package JMS;

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

public class ReceiverAsync implements MessageListener {
	private ConnectionFactory cf;
	private Destination d;

	public ReceiverAsync() throws NamingException {
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
		try (JMSContext jcontext = cf.createContext("hugoapp", "hsmar");) {
			jcontext.setClientID("hugoapp");
			JMSConsumer consumer = jcontext.createDurableConsumer((Topic) d,"hugoapp");
			consumer.setMessageListener(this);
			System.out.println("Press enter to finish...");
			System.in.read();
		} catch (JMSRuntimeException | IOException re) {
			re.printStackTrace();
		}
	}

	public static void main(String[] args) throws NamingException {
		ReceiverAsync r = new ReceiverAsync();
		r.launch_and_wait();
	}

}
