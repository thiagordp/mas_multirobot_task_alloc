/**
 * 
 */
package robot;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Term;
import utils.LearningControls;

/**
 * @author trdp
 *
 */
public class init_ql extends DefaultInternalAction {

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		NumberTerm gridSize = (NumberTerm) terms[0];
		LearningControls.gridSize = (int) gridSize.solve();

		return true;
	}

}
