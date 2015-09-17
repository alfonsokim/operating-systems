/**
 * 
 */
package agents;

import jade.wrapper.AgentContainer;
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
		jade.core.Runtime mainRuntime = jade.core.Runtime.instance();
		jade.core.ProfileImpl mainProfile = new jade.core.ProfileImpl("localhost", 2000, "AgentPlatform", true); 
		mainProfile.setParameter("jade _core_messaging_MessageManager_maxqueuesize", "90000000");
		AgentContainer myMainContainer = mainRuntime.createMainContainer(mainProfile);
		
		//Object [] agentParams = new Object[] {
		//		"Hugo", "Luis", "Gilberto",
		//		"Farid", "Alfonso", "Sergio"
		//		}; 
		
		//jade.core.ProfileImpl secondaryProfile;
		//secondaryProfile = new jade.core.ProfileImpl("localhost", 2001, "AgentPla|orm", false);
		//secondaryProfile.setParameter("jade _core_messaging_MessageManager_maxqueuesize", "90000000"); jade.wrapper.AgentContainer mySecondaryContainer;
		//mySecondaryContainer = mainRuntime.createAgentContainer(secondaryProfile);
		
		try {
			//myMainContainer.createNewAgent("MyMainHelloWorldAgent","agents.HelloAgent", agentParams);
			//myMainContainer.getAgent("MyMainHelloWorldAgent").start();
			
			myMainContainer.createNewAgent("sniffer","jade.tools.sniffer.Sniffer", null); 
			myMainContainer.getAgent("sniffer").start();
			
			myMainContainer.createNewAgent("RMA","jade.tools.rma.rma", null);
			myMainContainer.getAgent("RMA").start();
			
			myMainContainer.createNewAgent("buyer", "agents.BuyerAgent", null);
			myMainContainer.createNewAgent("seller", "agents.SellerAgent", null);
			
			myMainContainer.getAgent("buyer").start();
			myMainContainer.getAgent("seller").start();
			
			//mySecondaryContainer.createNewAgent("MySecondaryHelloWorldAgent","agents.HelloAgent", agentParams);
			//mySecondaryContainer.getAgent("MySecondaryHelloWorldAgent").start();
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
