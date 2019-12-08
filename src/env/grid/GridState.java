/**
 * 
 */
package grid;

import utils.State;

/**
 * To test q-learning
 *
 * @author jomi
 */

public class GridState implements State {

	public int x; // col
	public int y; // lin

	/** initial state */
	public GridState() {
		x = 1;
		y = 1;
	}

	public GridState(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		GridState os = (GridState) o;
		return (this.x == os.x && this.y == os.y);
	}

	@Override
	public int hashCode() {
		return x * 31 + y;
	}

	public String toString() {
		return "s(" + x + "," + y + ")";
	}

}
