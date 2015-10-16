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
	private int maxtries = 0;

	public Sender() throws NamingException {
		//Tries to connect 10 times to WildFly with an interval of 5 seconds
		while (maxtries < 10) {
			try {
				this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
				this.d = InitialContext.doLookup("jms/topic/TopicProject");
				break;
			} catch (Exception e) {
				System.out.println("Failed to connect to WildFly!\nRetrying in 5 seconds...");
				maxtries++;
				try {
				    Thread.sleep(5000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			} 
		}
	}

	public int getMaxtries() {
		return maxtries;
	}

	public void setMaxtries(int maxtries) {
		this.maxtries = maxtries;
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
