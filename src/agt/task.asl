// Agent task in project mas_multirobot_task_alloc

/* Initial beliefs and rules */
destiny(math.round(math.random * 100), math.round(math.random * 100)).
/* Initial goals */
!start.

/* Plans */

+!start
	<- 	.my_name(Id);
		makeArtifact(Id, "grid_env.TaskArtifact", [], ArtId);
		.print("Artefato criado por: ", Id);
		Id::focus(ArtId);
		Id::start(Id, math.round(math.random*100), math.round(math.random*100));
		.print("Broadcasting...");
		.broadcast(achieve, focus_message_task(Id)) ;
		.at("now + 5 seconds", {+!decide(Id)}).

+!decide(Id) : 
	Id::bid_count(C) &
	C > 0
	<- 
	.print("Deliberando ", Id);
	Id::stop.

+!decide(Id)
	<- 
	.print("Broadcasting", Id);
	.broadcast(achieve, focus_message_task(Id));
	.at("now + 5 seconds", {+!decide(Id)}).


{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
