/**
 * 
 */
package agents;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription; 
import jade.domain.FIPAAgentManagement.ServiceDescription; 
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

/**
 * @author Alfonso Kim
 *
 */
public class Agents {

	/**
	 * 
	 */
	public Agents() { }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		jade.core.Runtime mainRuntime; 
		mainRuntime = jade.core.Runtime.instance();
		jade.core.ProfileImpl mainProfile;
		mainProfile = new jade.core.ProfileImpl("localhost", 2000, "AgentPlatform", true); 
		mainProfile.setParameter("jade _core_messaging_MessageManager_maxqueuesize", "90000000");
		jade.wrapper.AgentContainer myMainContainer = mainRuntime.createMainContainer(mainProfile);
		Object [] agentParams = new Object[] {
				"Hugo", "Luis", "Gilberto",
				"Farid", "Alfonso", "Sergio"
				}; 
		try {
			myMainContainer.createNewAgent("MyMainHelloWorldAgent","agents.HelloAgent", agentParams);
			myMainContainer.getAgent("MyMainHelloWorldAgent").start();
			
			myMainContainer.createNewAgent("sniffer","jade.tools.sniffer.Sniffer", null); 
			myMainContainer.getAgent("sniffer").start();
			
			myMainContainer.createNewAgent("RMA","jade.tools.rma.rma", null);
			myMainContainer.getAgent("RMA").start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
