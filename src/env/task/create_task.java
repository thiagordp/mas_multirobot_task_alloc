/**
 * 
 */
package task;

import jason.runtime.*;
import utils.CreateControls;
import jason.asSemantics.*;
import jason.asSyntax.*;

/**
 * @authors Daniel, George, Marcelo e Thiago 
 *
 */
@SuppressWarnings("serial")
public class create_task extends DefaultInternalAction {
	
	public static final int MAX_SIZE = 20;

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
		
		CreateControls createC = CreateControls.createControl();

		RuntimeServices rs = ts.getUserAgArch().getRuntimeServices();

		int id;
		do {
			id = (int) Math.round(Math.random() * 10000);
		} while (createC.contains(id));
		
		createC.addId(id);
		
		Settings sett = new Settings();
		sett.addOption(Settings.INIT_BELS, "myId(" + createC.newCount() + "), maxSize(" + MAX_SIZE  + ")");

		String name = "task" + id;
		name = rs.createAgent(name, "task.asl", null, null, null, sett, ts.getAg());
		rs.startAgent(name);

		return true;
	}
}
