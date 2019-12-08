/**
 * 
 */
package robot;

import java.util.logging.Level;

import grid.GridState;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Atom;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;
import utils.LearningControls;

/**
 * Search for next state using RL
 * 
 * @authors Daniel, George, Marcelo e Thiago
 */
public class next_rl extends DefaultInternalAction {

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {

		try {
			Atom agName = (Atom) terms[0]; // Agent Name
			NumberTerm agX = (NumberTerm) terms[1]; // Current X position
			NumberTerm agY = (NumberTerm) terms[2]; // Current Y position

			String name = agName.toString();
			int x = (int) agX.solve();
			int y = (int) agY.solve();

			GridState curState = new GridState(x, y);

			String nextAction = LearningControls.nextPos(name, curState);
			GridState nextState = getPosFromAction(curState, nextAction);

			if (nextState.x < 0)
				nextState.x = 0;
			if (nextState.x > LearningControls.gridSize)
				nextState.x = LearningControls.gridSize;
			if (nextState.y < 0)
				nextState.y = 0;
			if (nextState.y > LearningControls.gridSize)
				nextState.y = LearningControls.gridSize;

			// Unify new position
			un.unifies(terms[3], new NumberTermImpl(nextState.x));
			un.unifies(terms[4], new NumberTermImpl(nextState.y));
		} catch (Throwable e) {
			ts.getLogger().log(Level.SEVERE, "rlearning error: " + e, e);
		}

		return true;
	}

	private GridState getPosFromAction(GridState curState, String action) {

		if (action.equals("N")) {
			curState.y = curState.y - 1;
		} else if (action.equals("S")) {
			curState.y = curState.y + 1;
		} else if (action.equals("L")) {
			curState.x = curState.x + 1;
		} else if (action.equals("O")) {
			curState.x = curState.x - 1;
		}

		return curState;
	}
}
