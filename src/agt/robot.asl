// Agent robot in project mas_multirobot_task_alloc

/* Initial beliefs and rules */
pos(math.round(math.random * 100), math.round(math.random * 100)).
neighborhood(50).
status("idle").

/* Initial goals */

/* Plans */
	
+!focus_message_task(A)  
	: 	status("idle")
	<-  lookupArtifact(A, ToolId);
		focus(ToolId).
		
+status_task(S)[artifact_id(AId)]
	: S == "idle" & 
	  position_x(X)[artifact_id(AId)] &
	  position_y(Y)[artifact_id(AId)] &
	  pos(Rx, Ry) &
	  neighborhood(H) & 
	  math.abs(X - Rx) + math.abs(Y - Ry) <= H	 
  	  
	<- 
	  .my_name(R) ;
	  .print("Robo ", R, " fez bid"); 
	  bid(Rx, Ry)[artifact_id(AId)].
	
+status_task(S)[artifact_id(AId)]
	<- 
	  .print("Stop focus ", AId);
	  stopFocus(AId).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
