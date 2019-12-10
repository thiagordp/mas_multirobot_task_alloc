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

			int x = (int) Math.floor(agX.solve());
			int y = (int) Math.floor(agY.solve());
			int gridSize = (int) Math.floor(gS.solve());
			int c = 0;

			int rx = x;
			int ry = y;

			do {

				int o = r.nextInt(4);

				if (o == 0) {
					rx = rx + 1;
				} else if (o == 1) {
					rx = rx - 1;
				} else if (o == 2) {
					ry = ry + 1;
				} else if (o == 3) {
					ry = ry - 1;
				}

				if (c == 10) {
					rx = x;
					ry = y;
				}
				c++;
			} while (rx < 0 || rx > gridSize || ry < 0 || ry > gridSize);

			
			un.unifies(terms[2], new NumberTermImpl(rx));
			un.unifies(terms[3], new NumberTermImpl(ry));

		} catch (Throwable e) {
			ts.getLogger().log(Level.SEVERE, "random_move error: " + e, e);
		}

		return true;
	}
}