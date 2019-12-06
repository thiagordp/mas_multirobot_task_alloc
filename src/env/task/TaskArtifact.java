/**
 * 
 */
package task;

import java.util.LinkedList;

import cartago.AgentId;
import cartago.Artifact;
import cartago.OPERATION;
import jason.asSyntax.Atom;

/**
 * @author trdp
 *
 */
public class TaskArtifact extends Artifact {

	String currentWinner = "no_winner";
	String idAgentProp;

	LinkedList<Bid> activeList = new LinkedList<Bid>();

	public void init() {
		defineObsProperty("status_task", "idle"); // idle, running, finish
		defineObsProperty("winner", new Atom(currentWinner));
	}

	@OPERATION
	public void start(String idTask, int x, int y) {
		// Apenas a tarefa que criou pode fazer a operação
		if (!getCreatorId().equals(getCurrentOpAgentId()))
			failed("You are not allowed to do it");
		if (!getObsProperty("status_task").stringValue().equals("idle"))
			failed("Task allocated");

		defineObsProperty("id_task", idTask);
		defineObsProperty("position_x", x);
		defineObsProperty("position_y", y);
		defineObsProperty("bid_count", 0);
		getObsProperty("status_task").updateValue("running");
	}

	@OPERATION
	public void stop() {
		// Apenas a tarefa que criou pode fazer a operação
		if (!getCreatorId().equals(getCurrentOpAgentId()))
			failed("You are not allowed to do it");
		if (!getObsProperty("status_task").stringValue().equals("running"))
			failed("Task not running");

		getObsProperty("status_task").updateValue("finish");

		int minDist = Integer.MAX_VALUE;
		Bid minBid = null;

		for (Bid bid : activeList) {
			int xR = bid.x;
			int yR = bid.y;

			int xT = getObsProperty("position_x").intValue();
			int yT = getObsProperty("position_y").intValue();

			int dManhattam = Math.abs(xR - xT) + Math.abs(yR - yT);

			if (dManhattam < minDist) {
				minDist = dManhattam;
				minBid = bid;
			}
		}

		//JOptionPane.showMessageDialog(null, minBid.agentId.toString());
		currentWinner = minBid.agentId.toString();
		getObsProperty("winner").updateValue(new Atom(currentWinner));
		activeList.clear();
	}

	@OPERATION
	public void bid(int x, int y) {
		AgentId agentId = getCurrentOpAgentId();

		boolean contains = false;
		for (Bid bid : activeList) {

			if (bid.agentId.equals(agentId)) {
				contains = true;
				break;
			}
		}

		if (!contains) {
			Bid bid = new Bid(agentId, x, y);
			activeList.add(bid);
		}

		getObsProperty("bid_count").updateValue(activeList.size());
	}
}
