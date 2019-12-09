package utils;

import java.util.LinkedList;
import java.util.List;

public class CreateControls {
	
	private int count = -1;
	private List<Long> listIds = new LinkedList<Long>();	
	
	// singleton patter
	protected static CreateControls createControls = null;
	
	synchronized public static CreateControls create() {
		if (createControls == null) {
			createControls = new CreateControls();
		}
		return createControls;
	}
	
	private CreateControls() {
		super();
	}
	
	public static CreateControls createControl() throws Exception {
		CreateControls createControls = CreateControls.create();
		return createControls;
	}

	public int newCount() {
		this.count++;
		return this.count;
	}
	
	public void addId(Long agId) {
		this.listIds.add(agId);
	}
	
	public boolean contains(Long agId) {
		return this.listIds.contains(agId);
	}

}
