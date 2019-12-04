// Agent robot in project mas_multirobot_task_alloc

/* Initial beliefs and rules */
//maxSize(10).
//pos(math.round(math.random * 10), math.round(math.random * 100)).
//neighborhood(10).
//status("idle").

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
	<- .at("now + 2 seconds", {+!decideMove}).
	
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
		//.print("!focus_message ... AId: ", AtName, " TI: ", ToolId).

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
	  .print("Stop focus ", AId, " task: ", AtName);
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
	  	-+status("moving");
	  	//+task(AtName, X, Y);
	  	.print(AtName, " sent accept, then I'm moving to ", AtName);
	  	!move(AtName);
	  }
	  else {
	  	 -task(AtName, X, Y);
		 -+status("idle");
		 .print(AtName, " sent cancel, I'm going back to state of idle");
	  }.
	  
+winner(N)[artifact_id(AId)] <- 
	.wait(0).
	
+!move(AtName)
	:	task(TName, X, Y)
	<-  -+status("processing");
		-+pos(X, Y);
		.print("I arrived to ", AtName);
		.send(AtName, tell, hello(AtName));
		.print("Update").

+destiny(X, Y)[source(A)]
	:	task(TName,_ ,_) &
		A == TName
	<-	.print("I received destiny from ", A);
		!moveUntilDestiny(X, Y).
		
+!moveUntilDestiny(X, Y)
	:	task(TName, _, _) &
		myfocused(AId)
	<-	.print("I taking ", TName, " until ", X, Y);
		.send(TName, tell, arrive(X, Y));
		-+pos(X, Y);
		stopFocus(AId);
		-myfocused(AId);
		-+status("idle").
		
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
