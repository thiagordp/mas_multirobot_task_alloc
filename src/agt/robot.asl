// Agent robot in project mas_multirobot_task_alloc

/* Initial beliefs and rules */
pos(math.round(math.random * 100), math.round(math.random * 100)).
neighborhood(100).
status("idle").

/* Initial goals */

/* Plans */
	
+!focus_message_task(AtName)  
	: 	status("idle")
	<-  lookupArtifact(AtName, ToolId); // Busca pelo nome do artefato e atribui um Id.
		focus(ToolId).
		//.print("!focus_message ... AId: ", AtName, " TI: ", ToolId).

+!focus_message_task(AtName) 
	: status(S)
	<- .wait(0).


+status_task(S)[artifact_id(AId), artifact_name(AId, AtName)]
	: S == "running" & 
	  status(Sr) &
	  Sr == "idle" & 
	  position_x(X)[artifact_id(AId)] &
	  position_y(Y)[artifact_id(AId)] &
	  pos(Rx, Ry) &
	  neighborhood(H) & 
	  (math.abs(X - Rx) + math.abs(Y - Ry)) <= H 
  	<- 
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
	: position_x(X)[artifact_id(AId)] &
	  position_y(Y)[artifact_id(AId)] &
	  N \== no_winner &
	  status(S) &
	  .my_name(Me) &
	  S == "waiting"
	 <-	  
	  if (Me == N){
	  	-+status("moving");
	  	+task(AtName, X, Y);
	  	.print(AtName, " sent accept, then I'm moving to ", AtName);
	  	!move(AtName);
	  }
	  else {
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
		-+status("idle");
		stopFocus(AId);
		-myfocused(AId).
		
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
