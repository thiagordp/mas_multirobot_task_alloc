/**
 * Move randomly in the grid
 * 
 * @authors Daniel, George, Marcelo e Thiago 
 */ 

+!defineMove
	: pos(X, Y) &
	  maxSize(M) &
	  search_strategy(random) &
	  robot.next_random(X, Y, Next_X, Next_Y, M)
	<- 	!moveNeighbor(Next_X, Next_Y).
		
+!moveNeighbor(X, Y)
	: 	myId(MId)
	<- 	.print("I am moving to (", X, ", ",  Y, ")");
		-+status("moving");
		-+pos(X, Y);
		setPosition(MId, X, Y);
		-+status("idle").
				