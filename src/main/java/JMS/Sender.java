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
		int maxtries=0;
		while (maxtries < 10) {
			try {
				this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
				this.d = InitialContext.doLookup("jms/topic/TopicProject");
			} catch (Exception e) {
				System.out.println("Failed to connect to WildFly!\nRetrying in 5 seconds...");
				maxtries++;
				try {
				    Thread.sleep(1000);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				e.printStackTrace();
			} 
		}
	}

	public void send(String text) {
		try (JMSContext jcontext = cf.createContext("hugoapp", "hsmar"); ) {
			JMSProducer mp = jcontext.createProducer();
			mp.send(d, text);
		} catch (JMSRuntimeException re) {
			re.printStackTrace();
		}
	}

	public static void main(String[] args) throws NamingException {
	}

}
