package grid_env;

import jason.environment.grid.GridWorldModel;

public class GridModel extends GridWorldModel{

	// singleton pattern
    protected static GridModel model = null;

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
		GridModel model = GridModel.create(21, 21, 4);
		return model;		
	}

}
