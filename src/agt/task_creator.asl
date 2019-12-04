// Agent task_creator in project mas_multirobot_task_alloc

/* Initial beliefs and rules */

/* Initial goals */

!create_task.

/* Plans */

+!create_task  <- 
	.wait(math.round( math.random * 5000 + 1000));
	task.create_task;
	!create_task.

+!create <- .wait(1).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
