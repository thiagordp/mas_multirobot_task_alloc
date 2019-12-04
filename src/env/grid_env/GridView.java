package grid_env;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;

public class GridView extends GridWorldView {

	TaskPlanet env = null;
	LinkedList<Integer> robots = new LinkedList<Integer>();
	
	public GridView(GridWorldModel model) {
		super(model, "Tasks World", 600);
		setVisible(true);
		repaint();
	}	
	
	public void setEnv(TaskPlanet env) {
		this.env = env;
	}
	
	public void addRobot(int robotId) {
		robots.add(robotId);
	}
	
	@Override
	public void drawAgent(Graphics g, int x, int y, Color c, int id) {
		if (robots.contains(id)) {
			super.drawAgent(g, x, y, c, id);
		} else {
			super.drawAgent(g, x, y, Color.green, id);
		}
	}

}
