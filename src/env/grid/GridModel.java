package grid;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

public class GridModel extends GridWorldModel{
	
	public static final int ROBOT = 1;
	public static final int TASK = 2;

	// singleton pattern
    protected static GridModel model = null;
    Location task;

	synchronized public static GridModel create(int w, int h, int nbAgs) {
		if (model == null) {
			model = new GridModel(w, h, nbAgs);
		}
		return model;
	}
	
	private GridModel(int w, int h, int nbAgs) {
		super(w, h, nbAgs);
	}
	
	
	static GridModel createGrid() throws Exception {
		GridModel model = GridModel.create(11, 11, 10);
		return model;		
	}

}
