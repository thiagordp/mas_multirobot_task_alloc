/**
 * 
 */
package utils;

import java.util.HashMap;

import grid.GridState;

/**
 * @author trdp
 *
 */
public class LearningControls {

	/*
	 * 
	 */
	public static Integer gridSize = 0;
	private static String[] actions = { "N", "S", "L", "O" };
	private static HashMap<String, QLearning<GridState>> agentQTables = new HashMap<String, QLearning<GridState>>();

	/*
	 * 
	 */
	public static QLearning<GridState> getQLearning(String agName) {

		if (!agentQTables.containsKey(agName)) {
			QLearning<GridState> q = new QLearning<GridState>(actions);
			q.setEpsilon(1D);
			q.setEpsilonDecrement(0.0001);
			q.setGamma(0.5);

			agentQTables.put(agName, q);
		}

		return agentQTables.get(agName);

	}

	/*
	 * 
	 */
	public static void addQLearning(String agName, QLearning<GridState> qLearning) {
		agentQTables.put(agName, qLearning);
	}

	/*********************************************************************************************************/

	/*
	 * 
	 */
	public static void updateQTable(String agName, GridState i, GridState j, Integer reward) {

		QLearning<GridState> ql = getQLearning(agName);
		String a = getAction(i, j);
		ql.updateQ(i, a, j, reward);
	}

	/*
	 * 
	 */
	public static String nextPos(String agName, GridState curPos) {
		QLearning<GridState> ql = getQLearning(agName);
		String action = ql.getExplorationActionByGreedy(curPos);
		return action;
	}

	/*
	 * 
	 */
	public static String getAction(GridState i, GridState j) {

		if (j.x == i.x + 1)
			return "L";
		if (j.x == i.x - 1)
			return "O";
		if (j.y == i.y - 1)
			return "N";
		if (j.y == i.y + 1)
			return "S";

		/*
		 * Caso ele tenha ficado no mesmo lugar.
		 */
		if (j.x == 0 && j.y == 0) {
			if (Math.random() < 0.5)
				return "N";
			return "O";
		} else if (j.x == gridSize && j.y == 0) {
			if (Math.random() < 0.5)
				return "N";
			return "L";
		} else if (j.x == 0 && j.y == gridSize) {
			if (Math.random() < 0.5)
				return "S";
			return "O";
		} else if (j.x == gridSize && j.y == gridSize) {
			if (Math.random() < 0.5)
				return "S";
			return "L";
		}

		/*
		 * Caso alguma outra situação não projetada, retorna uma ação aleatória.
		 */
		int index = ((int) (Math.random() + Math.random()) * 50) % actions.length;
		return actions[index];
	}
}
