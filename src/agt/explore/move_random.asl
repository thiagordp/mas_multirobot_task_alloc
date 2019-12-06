/**
 * Move randomly in the grid
 * 
 * @authors Daniel, George, Marcelo e Thiago 
 */ 

+!defineMove
	: pos(X, Y) &
	  maxSize(M) &
	  search_strategy(random)
	<- 	D = math.round(math.random*3);
		if(D == 0 & X + 1 <= M  ){
			!moveNeighbor(X + 1, Y);	
		}
		elif( D == 1 & X - 1 >= 0 ){
			!moveNeighbor(X - 1, Y);
		}
		elif( D == 2 & Y - 1 >= 0 ){
			!moveNeighbor(X, Y - 1);
		}
		elif( D == 3 & Y + 1 <= M ){
			!moveNeighbor(X, Y + 1);
		}
		else{
			!defineMove;
		}.
		
+!moveNeighbor(X, Y)
	: 	myId(MId)
	<- 	.print("I am moving to (", X, ", ",  Y, ")");
		-+status("moving");
		-+pos(X, Y);
		setPosition(MId, X, Y);
		-+status("idle").