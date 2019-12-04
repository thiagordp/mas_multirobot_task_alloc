// Agent task in project mas_multirobot_task_alloc

/* Initial beliefs and rules */
maxSize(10).
//destiny(math.round(math.random * 100), math.round(math.random * 100)).
/* Initial goals */
!set_initial_positions.

/* Plans */

+!set_initial_positions
	: 	maxSize(M)
	<- 	+origin(math.round(math.random*M), math.round(math.random*M));
		+destiny(math.round(math.random * M), math.round(math.random * M));
		!start.

+!start
	:	maxSize(M) &
		origin(X, Y)
	<- 	
		.my_name(Id);
		makeArtifact(Id, "task.TaskArtifact", [], ArtId);
		.print("Artefato criado por: ", Id, " com id ", ArtId);
		Id::focus(ArtId);
		Id::start(Id, X, Y);
		.print("Initial broadcasting para os robos...");
		.df_search(robo, L);
		.send(L, achieve, focus_message_task(Id));
		.at("now + 5 seconds", {+!decide(Id)}).

+!decide(Id) : 
	Id::bid_count(C) &
	C > 0
	<- 
	.print("Deliberando ", Id, " C: ", C);
	Id::stop.

+!decide(Id)
	: Id::bid_count(C) & C == 0 // TODO: É unificação ou comparação mesmo?
	<- 
	.print("Broadcasting ", Id);
	.df_search(robo, L);
	.send(L, achieve, focus_message_task(Id));
	.at("now + 5 seconds", {+!decide(Id)}).

+hello(AId)[source(A)]
	: AId::winner(N) &
	  N == A &
	  destiny(X, Y)
	<- .print("I received the message from ", A);
	   .send(A, tell, destiny(X, Y)).

+arrive(X, Y)[source(A)]
	: destiny(X, Y)	
	<- .print("I arrived in my destiny by ", A);
	   .my_name(N);
	   .print(N);
	   .df_search(create, L);	  
	   .send(L, tell, task_finish(N));  
	   .kill_agent(N).
	    

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
