package grid;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

public class GridModel extends GridWorldModel{
	
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
	
	static GridModel createGrid(int size, int nbAgs) throws Exception {
		GridModel model = GridModel.create(size, size, nbAgs);
		return model;		
	}

}
