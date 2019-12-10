/**
 * Move only to unvisited nodes in the grid
 * 
 * @authors Daniel, George, Marcelo e Thiago 
 */ 

visited([]).
exploring(no).

/*
 * 
 */
+!defineMove
	: 	pos(X, Y) &
	  	maxSize(M) &
	  	status("idle") &
	  	search_strategy(unvisited) &
	  	visited(L) &
	  	exploring(no) &
	  	robot.next_unvisited(X, Y, L, M, Next_X, Next_Y)	  	
	<-  -+exploring(yes);
		//.print("Next    -> ", Next_X, ", ", Next_Y);
		//.print("Visited -> ", L);
		.wait(100);
		!move_unvisited(Next_X, Next_Y).

/*
 * 
 */
+!defineMove
	:	status("idle") &
		search_strategy(unvisited)
	<- 	.wait(100);
		!defineMove.

/*
 * 
 */
+!defineMove
	:	search_strategy(unvisited) & 
		pos(X, Y) 
	<- 	-+visited([]);
		.wait(100).
		
/*
 * 
 */
+!move_unvisited(X, Y)
	:	pos(Rx, Ry) &
		status("idle") &
		visited(L)
	<-  
		-+exploring(yes);
		if (Rx > X) {
			!move(Rx - 1, Ry);
		}
		elif (Rx < X) {
			!move(Rx + 1, Ry);
		} 
		elif (Ry > Y) {
			!move(Rx, Ry - 1);
		}
		elif (Ry < Y) {
			!move(Rx, Ry + 1);
		} else {
			-+exploring(no);
			!add_visited(pos(X,Y)); 	// mark current position as visited.
			!defineMove; 				// Starts the search again
		}.

/*
 * 
 */
@v[atomic] +!add_visited(P)
	:	visited(L) &
		not .member(P, L)
	<-	.sort([P | L], A);
		-+visited(A).

/*
 * 
 */
+!add_visited(P)
	<- .wait(0).

/*
 * 
 */		
+!move_unvisited(X, Y)
	<- .wait(0).
/*
 * 
 */
+!move(X, Y)
	: 	myId(MId) &
		status("idle") &
		search_strategy(unvisited)
	<- 	-+pos(X, Y);
		setPosition(MId, X, Y);		
		!add_visited(pos(X,Y)); 
		!move_unvisited(X, Y).
		
