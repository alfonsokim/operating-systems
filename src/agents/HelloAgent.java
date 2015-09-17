/**
 * 
 */
package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.ACLMessage;

/**
 * @author Alfonso Kim
 * 
 */
public class HelloAgent extends Agent {

	private static final long serialVersionUID = 4771420969544225263L;

	/**
	 * 
	 */
	public HelloAgent() {
	}

	/**
	 * 
	 */
	protected void setup() {
		Object[] args = getArguments();
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				System.out.println("Hi " + (String) args[i] + " !");
			}
		}
		System.out.println("My name local is " + getLocalName());
		System.out.println("My GUID is " + getAID().getName());

		addBehaviour(new MessageListener(this, "TopicA"));
		addBehaviour(new MessageListener(this, "TopicB"));
	}

	private class MessageListener extends CyclicBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1529234285722495127L;
		private Agent agt;
		private MessageTemplate mt;

		public MessageListener(Agent a, String topicID) {
			agt = a;
			mt = MessageTemplate.and(
					MessageTemplate.MatchConversationId(topicID),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		}

		public void action() {
			jade.lang.acl.ACLMessage msg = agt.receive(mt);
			if (msg == null) {
				block();
				return;
			}
			try {
				String content = msg.getContent();
				System.out.println("I received a message from "
						+ msg.getSender() + " saying: " + msg.getContent());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private class MessageSender extends OneShotBehaviour {
		private Agent agt;
		private String conversationID;
		private String receiver;

		MessageSender(Agent a, String cID, String aReceiver) {
			agt = a;
			conversationID = cID;
			receiver = aReceiver;
		}

		public void action() {
			try {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.addReceiver(new AID(receiver, AID.ISGUID));
				msg.setConversationId(conversationID);
				msg.setContent("Contenido");
				agt.send(msg);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private class GenericBehaviour extends SimpleBehaviour {
		private Agent agt;
		private int state = 1;
		private boolean finished = false;

		public GenericBehaviour(Agent a) {
			agt = a;
		}

		public void action() {
			switch (state) {
			case 1:
				block(2000);
				break;
			case 2:
				System.out.println("--- Message 1 --- ");
				block(12000);
				break;
			case 3:
				System.out.println(" -- Message 2 --");
				finished = true;
				doDelete();
				break;
			}
			state++;
		}

		public boolean done() {
			return finished;
		}
	}

}
