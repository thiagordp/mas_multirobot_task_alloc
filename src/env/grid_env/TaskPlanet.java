package grid_env;

import java.util.logging.Logger;

import javax.swing.JOptionPane;

import cartago.Artifact;
import cartago.OPERATION;

public class TaskPlanet extends Artifact {
	
	private static Logger logger = Logger.getLogger(TaskPlanet.class.getName());

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
