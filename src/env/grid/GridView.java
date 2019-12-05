package grid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;

@SuppressWarnings("serial")
public class GridView extends GridWorldView {

	AgentPlanet env = null;
	LinkedList<Integer> robots = new LinkedList<Integer>();
	LinkedList<Integer> robotsWithTask = new LinkedList<Integer>();
	
	public GridView(GridWorldModel model) {
		super(model, "Tasks World", 600);
		setVisible(true);
		repaint();
	}	
	
	public void setEnv(AgentPlanet env) {
		this.env = env;
	}
	
	public void addRobot(int robotId) {
		robots.add(robotId);
	}
	
	public void addRobotWithTask(int robotId) {
		robotsWithTask.add(robotId);
	}
	
	public void removeRobotWithTask(int robotId) {
		robotsWithTask.remove((Object)robotId);
	}
	
	@Override
	public void drawAgent(Graphics g, int x, int y, Color c, int id) {
		if (robotsWithTask.contains(id)) {
			super.drawAgent(g, x, y, Color.darkGray, id);
		} else if (robots.contains(id)) {
			super.drawAgent(g, x, y, c, id);
		} else {
			super.drawAgent(g, x, y, Color.green, id);
		}
	}

}
