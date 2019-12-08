package robot;

import java.util.logging.Level;

import grid.GridState;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Atom;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import utils.LearningControls;

public class update_q_table extends DefaultInternalAction {

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {

		try {
			Atom agName = (Atom) terms[0]; // Agent Name
			NumberTerm agPrevX = (NumberTerm) terms[1]; // Prev X position
			NumberTerm agPrevY = (NumberTerm) terms[2]; // Prev Y position
			NumberTerm agCurX = (NumberTerm) terms[3]; // Cur X position
			NumberTerm agCurY = (NumberTerm) terms[4]; // Cur Y position
			StringTerm context = (StringTerm) terms[5]; // Action

			GridState curState = new GridState((int) agCurX.solve(), (int) agCurY.solve());
			GridState prevState = new GridState((int) agPrevX.solve(), (int) agPrevY.solve());
			Integer reward = getReward(context.toString());

			LearningControls.updateQTable(agName.toString(), prevState, curState, reward);

		} catch (Throwable e) {
			ts.getLogger().log(Level.SEVERE, "update_q_table error: " + e.getMessage(), e);
		}

		return true;
	}

	private int getReward(String context) {
		if (context.equals("W"))
			return 4;
		else if (context.equals("B"))
			return 0;
		else
			return -2;
	}

}
