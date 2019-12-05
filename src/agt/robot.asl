// Agent robot in project mas_multirobot_task_alloc

/* Initial beliefs and rules */
maxSize(10).
neighborhood(5).

/* Initial goals */
!set_initial_positions.

/* Plans */
+!set_initial_positions
	:	maxSize(M)
	<- 	+pos(math.round(math.random * M), math.round(math.random * M));
		!start.
	
+!start
	: pos(X, Y)
	 <- setPosition(X, Y);
	    .df_register(robo);
	 	+status("idle"). 

+status("idle")
	<- .at("now + 1 seconds", {+!decideMove}).
	
+!decideMove
	: status(S) &
	  S == "idle"
	<- !defineMove.
	
+!decideMove
	<- .wait(0).
	
+!defineMove
	: pos(X, Y) &
	  maxSize(M)
	<- D = math.round(math.random*3);
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
	<- 	.print("I am moving to (", X, ", ",  Y, ")");
		-+status("moving");
		-+pos(X, Y);
		setPosition(X, Y);
		-+status("idle").

+!focus_message_task(AtName)  
	: 	status("idle")
	<-  lookupArtifact(AtName, ToolId); // Busca pelo nome do artefato e atribui um Id.
		focus(ToolId).

+!focus_message_task(AtName) 
	<- .wait(0).


@u[atomic] +status_task(S)[artifact_id(AId), artifact_name(AId, AtName)]
	: S == "running" & 
	  status(Sr) &
	  Sr == "idle" & 
	  position_x(X)[artifact_id(AId)] &
	  position_y(Y)[artifact_id(AId)] &
	  pos(Rx, Ry) &
	  neighborhood(H) & 
	  (math.abs(X - Rx) + math.abs(Y - Ry)) <= H 
  	<- 
  	   +task(AtName, X, Y);
  	  -+status("waiting");
	  .my_name(R);
	  .print("Robo ", R, " fez bid em ", AId, "name: ", AtName); 
	  +myfocused(AId);
	  bid(Rx, Ry)[artifact_id(AId)].
	
/*
 * Caso contrário, "ignora" a tarefa
 */
+status_task(S)[artifact_id(AId), artifact_name(AId, AtName)]
	<- 
	  //.print("Stop focus ", AId, " task: ", AtName);
	  lookupArtifact(AtName, ToolId);
	  stopFocus(ToolId).


+winner(N)[artifact_id(AId), artifact_name(AId, AtName)]
	: task(AtName, X, Y) &
	  N \== no_winner &
	  status(S) &
	  .my_name(Me) &
	  S == "waiting"
	 <-	  
	  if (Me == N){
	  	.print(AtName, " sent accept, then I'm moving to ", AtName);
	  	-+status("processing");
	  }
	  else {
	  	 -task(AtName, X, Y);
		 -+status("idle");
		 .print(AtName, " sent cancel, I'm going back to state of idle");
	  }.
	  
+winner(N)[artifact_id(AId)] <- 
	.wait(0).
	
+status("processing")
	<- 	.at("now + 1 seconds", {+!moveToTask}).
	
+!moveToTask
	:	task(TName, X, Y) &
		pos(Rx, Ry)
	<-  -+status("moving");
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
		}
		else {
			!arrivedAtTask(Rx, Ry);
		}.
	
+!arrivedAtTask(X, Y)
	:	task(TName, _, _) &
		myfocused(AId)
	<-	setArrivedAtTask;
		.print("I arrived to ", TName);
		.send(TName, tell, hello(TName));
		.print("Update").

+!move(X, Y)
	<- 	-+status("moving");
		-+pos(X, Y);
		setPosition(X, Y);
		-+status("processing").


+destiny(X, Y)[source(A)]
	:	task(TName,_ ,_) &
		A == TName &
		pos(Rx, Ry)
	<-	.print("I received destiny from ", A, " (", X, ", ",  Y, ")");
		-+displacement(X, Y);
		-+status("to_destiny").
		
+status("to_destiny")
	<- 	.at("now + 1 seconds", {+!defineMoveDestiny}).
	
+!defineMoveDestiny
	: 	pos(Rx, Ry) &
		displacement(X, Y)
	<-	if (Rx > X) {
			!moveToDestiny(Rx - 1, Ry);
		}
		elif (Rx < X) {
			!moveToDestiny(Rx + 1, Ry);
		} 
		elif (Ry > Y) {
			!moveToDestiny(Rx, Ry - 1);
		}
		elif (Ry < Y) {
			!moveToDestiny(Rx, Ry + 1);
		}
		else {
			!arrivedAtDestination(Rx, Ry);
		}.

+!moveToDestiny(X, Y)
	<- 	-+status("moving");
		-+pos(X, Y);
		setPosition(X, Y);
		-+status("to_destiny").
		
		
+!arrivedAtDestination(X, Y)
	:	task(TName, _, _) &
		myfocused(AId)
	<-	setAttivedAtDestination;
		.print("I arrived at my destination ");
		.send(TName, tell, arrive(X, Y));
		stopFocus(AId);
		-myfocused(AId);
		!energy_level;
		-+status("idle").
		
+!energy_level 
	:	R = math.random &
		R < 0.1 
	<-	.df_search(create, L);
		.my_name(N);
		.send(L, tell, robot_finish(N));
		.print("Low Energy... Shutting down");
		.kill_agent(N).	

+!energy_level
	<-	.wait(0).
	
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
