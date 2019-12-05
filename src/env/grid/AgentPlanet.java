package grid;

import java.util.logging.Logger;
import cartago.Artifact;
import cartago.OPERATION;

public class AgentPlanet extends Artifact {
	
	private static Logger logger = Logger.getLogger(AgentPlanet.class.getName());

	static GridModel model = null;
	static GridView view;
	
	int agentId = -1;
	int agentType = -1;

	@OPERATION
	public void init() {
		initGrid();
	}
	
	@OPERATION
	public void setPosition(int agId, int x, int y) {
		model.setAgPos(agId, x, y);
	}
	
	@OPERATION
	public void removeAgent(int agId, int x, int y) {
		model.remove(agId, x, y);
	}
	
	@OPERATION
	public void addRobot(int agId) {
		view.addRobot(agId);
	}
	
	@OPERATION
	public void setArrivedAtTask(int agId) {
		view.addRobotWithTask(agId);
	}
	
	@OPERATION
	public void setAttivedAtDestination(int agId) {
		view.removeRobotWithTask(agId);		
	}
	
	public synchronized void initGrid() {
		try {
			if (model == null) {
				model = GridModel.createGrid();
				view = new GridView(model);
				view.setEnv(this);
				view.update();
			}
		} catch (Exception e) {
			logger.warning("Erro creating world " + e);
			e.printStackTrace();
		}
				
	}

}
