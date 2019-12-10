package grid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;

@SuppressWarnings("serial")
public class GridView extends GridWorldView {

	AgentPlanet env = null;
	LinkedList<Integer> randomRobots = new LinkedList<Integer>();
	LinkedList<Integer> unvisitedRobots = new LinkedList<Integer>();
	LinkedList<Integer> r_learningRobots = new LinkedList<Integer>();
	LinkedList<Integer> robotsWithTask = new LinkedList<Integer>();
	
	public GridView(GridWorldModel model) {
		super(model, "Tasks World", 600);
		setVisible(true);
		repaint();
	}	
	
	public void setEnv(AgentPlanet env) {
		this.env = env;
	}
	
	public void addRobot(int robotId, String strategyType) {
		if (strategyType.equals("random")) {
			randomRobots.add(robotId);
		} else if (strategyType.equals("unvisited")) {
			unvisitedRobots.add(robotId);
		} else {
			r_learningRobots.add(robotId);
		}
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
		} else if (randomRobots.contains(id)) {
			super.drawAgent(g, x, y, Color.blue, id);
		} else if (unvisitedRobots.contains(id)) {
			super.drawAgent(g, x, y, Color.cyan, id);
		} else if (r_learningRobots.contains(id)) {
			super.drawAgent(g, x, y, Color.orange, id);
		} else {
			super.drawAgent(g, x, y, Color.green, id);
		}
	}
	
	@Override
	public void initComponents(int width) {
		super.initComponents(width);
		
		JLabel label;
		
		JPanel msg = new JPanel();
		msg.setLayout(new BoxLayout(msg, BoxLayout.Y_AXIS));
		msg.setBorder(BorderFactory.createEtchedBorder());
		
		JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		label = new JLabel("Tasks");
		label.setForeground(Color.black);
		taskPanel.add(label);
		taskPanel.setBackground(Color.green);
		msg.add(taskPanel);
		
		JPanel tPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		label = new JLabel("Robots with tasks");
		label.setForeground(Color.white);
		tPanel.add(label);
		tPanel.setBackground(Color.darkGray);
		msg.add(tPanel);
		
		JPanel rPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		label = new JLabel("Random Robots");
		label.setForeground(Color.white);
		rPanel.add(label);
		rPanel.setBackground(Color.blue);
		msg.add(rPanel);
		
		JPanel uPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		label = new JLabel("Unvited Robots");
		label.setForeground(Color.black);
		uPanel.add(label);
		uPanel.setBackground(Color.cyan);
		msg.add(uPanel);
		
		JPanel qPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		label = new JLabel("Q Learning Robots");
		label.setForeground(Color.black);
		qPanel.add(label);
		qPanel.setBackground(Color.orange);
		msg.add(qPanel);
		
		JPanel s = new JPanel(new BorderLayout());
		s.add(BorderLayout.CENTER, msg);
		getContentPane().add(BorderLayout.SOUTH, s);
		
		
	}

}
