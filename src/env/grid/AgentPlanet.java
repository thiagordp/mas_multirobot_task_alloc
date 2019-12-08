package grid;

import java.util.logging.Logger;
import cartago.Artifact;
import cartago.OPERATION;

public class AgentPlanet extends Artifact {
	
	public static final int NB_AG = 200000;
	
	private static Logger logger = Logger.getLogger(AgentPlanet.class.getName());

	static GridModel model = null;
	static GridView view;

	@OPERATION
	public void init(int maxSize) {
		initGrid(maxSize);
	}
	
	@OPERATION
	public void setPosition(int agId, int x, int y) {
		model.setAgPos(agId, x, y);
	}
	
	@OPERATION
	public void removeAgent(int agId, int x, int y) { // TODO: Remover agente após conclusão
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
	
	public synchronized void initGrid(int maxSize) {
		try {
			if (model == null) {
				maxSize++;
				model = GridModel.createGrid(maxSize, NB_AG);
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
