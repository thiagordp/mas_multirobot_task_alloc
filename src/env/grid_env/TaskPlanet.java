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
	boolean isRobot = false;

	@OPERATION
	public void init(int agId, int isRobot) {
		this.agentId = agId;
		if (isRobot == 1) {
			this.isRobot = true;
		}
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
		} catch (Exception e) {
			logger.warning("Erro creating world " + e);
			e.printStackTrace();
		}
				
	}

}
