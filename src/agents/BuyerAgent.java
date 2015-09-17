/**
 * 
 */
package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author Alfonso Kim
 *
 */
public class BuyerAgent extends Agent {

	private static final long serialVersionUID = 76034717775332120L;

	/**
	 * 
	 */
	public BuyerAgent() {}
	
	protected void setup() {
		addBehaviour(new Asking(this, 10000));
		addBehaviour(new MessageListenerAndReplier(this));
	}
	
	private class Asking extends TickerBehaviour {
		
		private static final long serialVersionUID = 5161535948688493922L;
		private Agent agt;

		public Asking(Agent a, long period) {
			super(a, period);
			agt = a;
		}

		@Override
		protected void onTick() {
			String agentAddress = searchAgent(agt, "seller");
			try{
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.addReceiver(new AID(agentAddress, AID.ISGUID)); 
				msg.setConversationId("tamales");
				msg.setContent("Pregunta");
				agt.send(msg);
			} catch(Exception ex) {
					ex.printStackTrace(); 
			}
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
		}
		
	}
	
	static public String searchAgent(Agent agt, String serviceType) {
		try {
			DFAgentDescription dfd = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType(serviceType);
			dfd.addServices(sd);
			DFAgentDescription[] result = DFService.search(agt, dfd);
			if (result.length > 0) {
				return result[0].getName().getName();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
