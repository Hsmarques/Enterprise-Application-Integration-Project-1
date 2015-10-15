package priceReqKeep;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Sender {

	private ConnectionFactory cf;
	private Destination d;

	public Sender() throws NamingException {
		try {
			this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
			this.d = InitialContext.doLookup("jms/queue/QueueProject");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
