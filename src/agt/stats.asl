// Agent stats in project mas_multirobot_task_alloc

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start
	<- 	.print("Setting up statistics...");
		.at("now +30 m", {+!stats_broadcast}).
	
+!stats_broadcast
	<-	.df_search(robo, L);
		.send(L, tell, generate_stats);
		//.print("Stats broadcast");
		.wait(10);
		!stats_broadcast.
	

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
