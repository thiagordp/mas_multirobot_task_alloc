/**
 * 
 */
package robot;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Structure;
import jason.asSyntax.Term;
import utils.Location;
import utils.MoveControls;

/**
 * Search for next_unvisited node
 * 
 * @authors Daniel, George, Marcelo e Thiago
 */
public class next_unvisited extends DefaultInternalAction {

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {

		try {
			NumberTerm agX = (NumberTerm) args[0]; // Current X position
			NumberTerm agY = (NumberTerm) args[1]; // Current Y position
			List<Term> listT = ((ListTerm) args[2]).getAsList(); // Visited Places

			int gridSize = (int) ((NumberTerm) args[3]).solve();
			int curX = (int) agX.solve();
			int curY = (int) agY.solve();

			List<Location> listVisited = new ArrayList<Location>();

			// Pegar os X,Y de cada item na lista.
			for (Term term : listT) {
				List<Term> pos = ((Structure) term).getTerms();

				NumberTerm xT = (NumberTerm) pos.get(0);
				NumberTerm yT = (NumberTerm) pos.get(1);

				int x = (int) xT.solve();
				int y = (int) yT.solve();

				listVisited.add(new Location(x, y));
			}

			Location curLoc = new Location(curX, curY);
			Location nextUnvisited = getNearestUnvisited(listVisited, curLoc, gridSize);

			// Unify new position
			un.unifies(args[4], new NumberTermImpl(nextUnvisited.x));
			un.unifies(args[5], new NumberTermImpl(nextUnvisited.y));

		} catch (Throwable e) {
			ts.getLogger().log(Level.SEVERE, "next_visited error: " + e, e);
		}

		return true;
	}

	/**
	 *
	 */
	private Location getNearestUnvisited(List<Location> visited, Location currentPos, int gridSize) {

		int d = 1;
		while (true) {
			int randX = 0, randY = 0;

			if (Math.random() < 0.5)
				randX = (currentPos.x + (int) (Math.round(Math.random() * (d + 1) - d)));
			else
				randY = (currentPos.y + (int) (Math.round(Math.random() * (d + 1) - d)));

			int locX = randX + currentPos.x;
			int locY = randY + currentPos.y;

			if (locX >= 0 && locX <= gridSize && locY >= 0 && locY <= gridSize) {
				Location l = new Location(locX, locY);

				if (!MoveControls.contains(visited, l))
					return l;

			} else if (Math.random() < (0.01 / d)) {
				d++;
			}
		}
	}
}
