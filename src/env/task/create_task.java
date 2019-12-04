/**
 * 
 */
package task;

import java.util.LinkedList;
import java.util.List;

import jason.*;
import jason.runtime.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

/**
 * @author trdp
 *
 */
public class create_task extends DefaultInternalAction {

	List<Integer> listIds = new LinkedList<Integer>();

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {

		// use Settings to add initial beliefs and goals for the new agent
		// (as used in the .mas2j project file)
		Settings s = new Settings();

		// RuntimeServices provides services to create agents in the current platform
		// (Centralised, JADE, JaCaMo, ...)
		RuntimeServices rs = ts.getUserAgArch().getRuntimeServices();

		int id;
		do {
			id = (int) Math.round(Math.random() * 10000);
		} while (listIds.contains(id));
		
		listIds.add(id);

		String name = "task" + id;
		name = rs.createAgent(name, "task.asl", null, null, null, s, ts.getAg());
		rs.startAgent(name);

		return true;
	}
}
