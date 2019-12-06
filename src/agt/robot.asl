// Agent robot in project mas_multirobot_task_alloc


/* Includes */
{ include("explore/move_random.asl") }		// Random Movement Strategy
{ include("explore/move_unvisited.asl") }	// Move to Unvisited Strategy

/* Initial beliefs and rules */
neighborhood(5).
//search_strategy(random).
search_strategy(unvisited).
// search_strategy(r_learning).


/* Initial goals */
!set_initial_positions.

/* Plans */
+!set_initial_positions
	:	myId(MId) &
		maxSize(M)
	<- 	+pos(math.round(math.random * M), math.round(math.random * M));
		.my_name(Id);
		.concat(Id, "view", V);
		.print(V);
		makeArtifact(V, "grid.AgentPlanet", [M], ArtId);
		focus(ArtId);
		addRobot(MId);
		!start.
	
+!start
	: 	pos(X, Y) &
		myId(MId)
	 <- setPosition(MId, X, Y);
	    .df_register(robo);
	 	+status("idle"). 

+status("idle")
	<-	-+exploring(no); 	
		.at("now + 2 seconds", {+!decideMove}).

+!decideMove
	: status(S) &
	  S == "idle"
	<- !defineMove.
	
+!decideMove
	<- .wait(0).


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
	  bid(Rx, Ry)[artifact_id(AId)];
	  -+visited([]).
	
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
		myfocused(AId) &
		myId(MId)
	<-	setArrivedAtTask(MId);
		.print("I arrived to ", TName);
		.send(TName, tell, hello(TName));
		.print("Update").

+!move(X, Y)
	: myId(MId)
	<- 	-+status("moving");
		-+pos(X, Y);
		setPosition(MId, X, Y);
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
			.abolish(destiny(_, _));
		}.

+!moveToDestiny(X, Y)
	: myId(MId)
	<- 	-+status("moving");
		-+pos(X, Y);
		setPosition(MId, X, Y);
		-+status("to_destiny").
		
		
@arrive[atomic]+!arrivedAtDestination(X, Y)
	:	task(TName, _, _) &
		myfocused(AId) &
		myId(MId)
	<-	setAttivedAtDestination(MId);
		.print("I arrived at my destination ");
		.send(TName, tell, arrive(X, Y));
		stopFocus(AId);
		-myfocused(AId);
		-+status("idle").
	
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
