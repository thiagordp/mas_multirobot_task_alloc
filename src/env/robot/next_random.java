package robot;

import java.util.Random;
import java.util.logging.Level;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

/**
 * Search for next_unvisited node
 * 
 * @authors Daniel, George, Marcelo e Thiago
 */
public class next_random extends DefaultInternalAction {

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {

		try {
			NumberTerm agX = (NumberTerm) terms[0]; // Current X position
			NumberTerm agY = (NumberTerm) terms[1]; // Current Y position
			NumberTerm gS = (NumberTerm) terms[4];

			Random r = new Random();
			r.setSeed(System.nanoTime());

			int rx = (int) Math.round(agX.solve());
			int ry = (int) Math.round(agY.solve());
			int gridSize = (int) Math.round(gS.solve());

			do {
				double x = r.nextInt(4);

				if (x == 0) {
					rx = rx + 1;
				} else if (x == 1) {
					rx = rx - 1;
				} else if (x == 2) {
					ry = ry + 1;
				} else if (x == 3) {
					ry = ry - 1;
				}

			} while (rx < 0 || rx > gridSize || ry < 0 || ry > gridSize);

			un.unifies(terms[2], new NumberTermImpl(rx));
			un.unifies(terms[3], new NumberTermImpl(ry));

		} catch (Throwable e) {
			ts.getLogger().log(Level.SEVERE, "random_move error: " + e, e);
		}

		return true;
	}
}