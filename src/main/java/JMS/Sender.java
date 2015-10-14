package JMS;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Sender {

	private ConnectionFactory cf;
	private Destination d;

	public Sender() throws NamingException {
		try {
			this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
			this.d = InitialContext.doLookup("jms/topic/TopicProject");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void send(String text) {
		try (JMSContext jcontext = cf.createContext("hugoapp", "hsmar");) {
			JMSProducer mp = jcontext.createProducer();
			mp.send(d, text);
		} catch (JMSRuntimeException re) {
			re.printStackTrace();
		}
	}

	public static void main(String[] args) throws NamingException {
		Sender s = new Sender();
		s.send("Hello Receiver!");
	}

}
