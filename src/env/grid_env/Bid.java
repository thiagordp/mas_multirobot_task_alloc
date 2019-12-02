/**
 * 
 */
package grid_env;

import cartago.AgentId;

/**
 * @author trdp
 *
 */
public class Bid {

	AgentId agentId;
	int x;
	int y;

	public Bid() {

	}

	public Bid(AgentId agentId, int x, int y) {
		this.agentId = agentId;
		this.x = x;
		this.y = y;
	}

}
