/**
 * 
 */
package robot;

import jason.runtime.*;
import utils.CreateControls;

import java.util.Random;

import jason.asSemantics.*;
import jason.asSyntax.*;

public class create_robot extends DefaultInternalAction {

	public static final int MAX_SIZE = 20;

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {

		CreateControls createC = CreateControls.createControl();

		// RuntimeServices provides services to create agents in the current platform
		// (Centralized, JADE, JaCaMo, ...)
		RuntimeServices rs = ts.getUserAgArch().getRuntimeServices();

		Long id;
		do {
			id = (Long) Math.round(Math.random() * 1000);
		} while (createC.contains(id));

		createC.addId(id);

		// use Settings to add initial beliefs and goals for the new agent
		// (as used in the .mas2j project fieeeeeele)

		String beliefs = "myId(" + createC.newCount() + "), maxSize(" + MAX_SIZE + "), search_strategy(#)";

		Random random = new Random();
		random.setSeed(System.nanoTime());
		
		
		/* Experiments with mixed agents */
		
		double r = random.nextDouble();
		if (r < (1D / 3))
			beliefs = beliefs.replace("#", "random");
		else if (r >= (1D / 3) && r < (2D / 3)) {
			beliefs = beliefs.replace("#", "unvisited");
		} else {
			beliefs = beliefs.replace("#", "r_learning");
		}
		
		
		
		/* Experiments with random agents */
		//beliefs = beliefs.replace("#", "random");
		/* Experiments with unvisited pos agents */
		// beliefs = beliefs.replace("#", "unvisited");
		/* Experiments with unvisited pos agents */
		// beliefs = beliefs.replace("#", "r_learning");
		
		Settings sett = new Settings();
		sett.addOption(Settings.INIT_BELS, beliefs);

		String name = "robot" + id;
		name = rs.createAgent(name, "robot.asl", null, null, null, sett, ts.getAg());
		rs.startAgent(name);

		return true;
	}
}
