package grid_env;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;

public class GridView extends GridWorldView {

	TaskPlanet env = null;
	
	public GridView(GridWorldModel model) {
		super(model, "Tasks World", 600);
		setVisible(true);
		repaint();
	}	
	
	public void setEnv(TaskPlanet env) {
		this.env = env;
	}
}
