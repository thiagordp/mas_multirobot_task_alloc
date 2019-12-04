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
	public void init(int agId, int agentType) {
		this.agentId = agId;
		this.agentType = agentType; 
		initGrid();
	}
	
	@OPERATION
	public void setPosition(int x, int y) {
		model.setAgPos(this.agentId, x, y);
	}
	
	@OPERATION
	public void removeAgent(int x, int y) {
		model.remove(this.agentId, x, y);
	}
	
	@OPERATION
	public void setArrivedAtTask() {
		view.addRobotWithTask(this.agentId);
	}
	
	@OPERATION
	public void setAttivedAtDestination() {
		view.removeRobotWithTask(this.agentId);		
	}
	
	public synchronized void initGrid() {
		try {
			if (model == null) {
				model = GridModel.createGrid();
				view = new GridView(model);
				view.setEnv(this);
				view.update();
			}
			if (this.agentType == 1) {
				view.addRobot(this.agentId);
			}
		} catch (Exception e) {
			logger.warning("Erro creating world " + e);
			e.printStackTrace();
		}
				
	}

}
