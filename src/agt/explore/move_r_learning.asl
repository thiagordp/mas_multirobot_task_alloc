/**
 * Move using Reinforcement Learning
 * 
 * @authors Daniel, George, Marcelo e Thiago 
 */ 

!init_q_learning.

+!init_q_learning
	:	maxSize(M) 
	<- 	robot.init_ql(M).

/*
 * 
 */
+!defineMove
	:	search_strategy(r_learning) &
		pos(X, Y) &
		status("idle") &
		maxSize(M) 
	<-  .my_name(N);
		robot.next_rl(N, X, Y, Next_X, Next_Y, M);
		.wait(100);
		!move_r_learning(Next_X, Next_Y).		
+!defineMove
	:	status("idle") &
		search_strategy(r_learning)
	<- 	.wait(100);
		!defineMove.		
+!defineMove
	: 	search_strategy(r_learning)
	<- 	.wait(0).

/*
 * 
 */		
+!move_r_learning(X, Y)
	:	pos(Rx, Ry) &
		status("idle")
	<-  
		-+last_pos(Rx, Ry)
		if (Rx > X) {
			!move(Rx - 1, Ry);			
			!move_r_learning(Rx - 1, Ry);	
		}
		elif (Rx < X) {
			!move(Rx + 1, Ry);
			!move_r_learning(Rx + 1, Ry);
		} 
		elif (Ry > Y) {
			!move(Rx, Ry - 1);
			!move_r_learning(Rx, Ry - 1);
		}
		elif (Ry < Y) {
			!move(Rx, Ry + 1);
			!move_r_learning(Rx, Ry + 1);
		} else {
			!defineMove; 				// Starts the search again
		}.
		
+!move_r_learning(X, Y)
	<- .wait(0).

/*
 * 
 */
@m[atomic] +!move(X, Y)
	: 	myId(MId) &
		pos(Lx, Ly) &
		status("idle") &
		search_strategy(r_learning)
	<-
		.my_name(N);
		robot.update_q_table(N, Lx, Ly, X, Y, "M");
		setPosition(MId, X, Y);	
		-+last_pos(Lx, Ly);
		-+pos(X, Y).

/*
 * 
 */
+!bid_rl
	:	search_strategy(r_learning) &
		pos(X, Y) &
		last_pos(Lx, Ly)
	<- 	.my_name(N);
		robot.update_q_table(N, Lx, Ly, X, Y, "B").
+!bid_rl
	<- .wait(0).	
	
/*
 * 
 */
+!winner_rl
	:	search_strategy(r_learning) &
		pos(X, Y) &
		last_pos(Lx, Ly)
	<- 	.my_name(N);
		robot.update_q_table(N, Lx, Ly, X, Y, "W").
+!winner_rl
	<- .wait(0).
	
	
	
	
	
	
	
	
	
	
	
	