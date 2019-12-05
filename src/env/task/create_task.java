/**
 * 
 */
package task;

import jason.runtime.*;
import utils.CreateControls;
import jason.asSemantics.*;
import jason.asSyntax.*;

/**
 * @author trdp
 *
 */
@SuppressWarnings("serial")
public class create_task extends DefaultInternalAction {
	
	public static final int MAX_SIZE = 20;

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
		
		CreateControls createC = CreateControls.createControl();

		// RuntimeServices provides services to create agents in the current platform
		// (Centralized, JADE, JaCaMo, ...)
		RuntimeServices rs = ts.getUserAgArch().getRuntimeServices();

		int id;
		do {
			id = (int) Math.round(Math.random() * 10000);
		} while (createC.contains(id));
		
		createC.addId(id);
		
		// use Settings to add initial beliefs and goals for the new agent
		// (as used in the .mas2j project file)
		Settings sett = new Settings();
		sett.addOption(Settings.INIT_BELS, "myId(" + createC.newCount() + "), maxSize(" + MAX_SIZE  + ")");

		String name = "task" + id;
		name = rs.createAgent(name, "task.asl", null, null, null, sett, ts.getAg());
		rs.startAgent(name);

		return true;
	}
}
