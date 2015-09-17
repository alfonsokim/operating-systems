/**
 * 
 */
package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author Alfonso Kim
 *
 */
public class SellerAgent extends Agent {

	private static final long serialVersionUID = -7946785613212445075L;

	/**
	 * 
	 */
	public SellerAgent() { }
	
	protected void setup() {
		addBehaviour(new MessageListenerAndReplier(this));
		publishService(this, "seller");
	}
	
	protected void takeDown(){
		unpublishService(this);
	}
	
	public static void unpublishService(Agent a) {
		try {
			DFService.deregister(a); 
		} catch (FIPAException e) {
			e.printStackTrace(); 
		}
	}
	
	public static void publishService(Agent a, String serviceType) {
		DFAgentDescription dfd = new DFAgentDescription(); 
		dfd.setName((jade.core.AID)a.getAID());
		ServiceDescription sd = new ServiceDescription(); 
		sd.setType(serviceType);
		sd.setName(a.getAID().getName()); dfd.addServices(sd);
		try {
			DFService.register(a, dfd);
		}
		catch (FIPAException fe) {
		fe.printStackTrace(); 
		}
	}
	
	private class MessageListenerAndReplier extends CyclicBehaviour {
		
		private Agent agt;
		private MessageTemplate mt;
		
		public MessageListenerAndReplier(Agent agt){
			this.agt = agt;
			mt = MessageTemplate.and(
					MessageTemplate.MatchConversationId("tamales"),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		}

		@Override
		public void action() {
			ACLMessage msg = agt.receive(mt);
			if (msg == null) {
				block();
				return;
			}
			try {
				System.out.println("I received a message from "
						+ msg.getSender() + " saying: " + msg.getContent());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try{
				ACLMessage response = new ACLMessage(ACLMessage.INFORM);
				response.addReceiver(msg.getSender()); 
				response.setConversationId("tamales");
				response.setContent("Respuesta");
				agt.send(response);
			} catch(Exception ex) {
					ex.printStackTrace(); 
			}
		}
		
	}

}
