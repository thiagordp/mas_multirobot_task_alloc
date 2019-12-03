package grid_env;

import java.util.logging.Logger;

import cartago.Artifact;
import cartago.OPERATION;

public class TaskPlanet extends Artifact {
	
	private static Logger logger = Logger.getLogger(TaskPlanet.class.getName());

	static GridModel model = null;
	static GridView view;
	
	int agentId = -1;

	@OPERATION
	public void init(int agId) {
		this.agentId = agId;
		initGrid();
	}
	
	public synchronized void initGrid() {
		try {
			if (model == null) {
				model = GridModel.createGrid();
			}
		} catch (Exception e) {
			logger.warning("Erro creating world " + e);
			e.printStackTrace();
		}
				
	}

}
