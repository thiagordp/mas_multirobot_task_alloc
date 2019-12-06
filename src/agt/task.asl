// Agent task in project mas_multirobot_task_alloc

/* Initial beliefs and rules */

/* Initial goals */
!set_initial_positions.

/* Plans */

+!set_initial_positions
	: 	maxSize(M)
	<- 	+origin(math.round(math.random*M), math.round(math.random*M));
		+destiny(math.round(math.random * M), math.round(math.random * M));
		.my_name(Id);
		.concat(Id, "view", V);
		.print(V);
		makeArtifact(V, "grid.AgentPlanet", [M], ArtId);
		focus(ArtId);
		!start.

+!start
	:	maxSize(M) &
		origin(X, Y) &
		myId(Mi)
	<- 	setPosition(Mi, X, Y);
		.my_name(Id);
		makeArtifact(Id, "task.TaskArtifact", [], ArtId);
		.print("Artefato criado por: ", Id, " com id ", ArtId);
		Id::focus(ArtId);
		Id::start(Id, X, Y);
		.print("Initial broadcasting para os robos...");
		.df_search(robo, L);
		.send(L, achieve, focus_message_task(Id));
		.at("now + 5 seconds", {+!decide(Id)}).

+!decide(Id)
	: 	Id::bid_count(C) &
		C > 0
	<- 	.print("Deliberando ", Id, " C: ", C);
		Id::stop.

+!decide(Id)
	: 	Id::bid_count(C) &
		C == 0 & 
		origin(X, Y) &
		myId(Mi)
	<- 	setPosition(Mi, X, Y);
		.print("Broadcasting ", Id);
		.df_search(robo, L);
		.send(L, achieve, focus_message_task(Id));
		.at("now + 5 seconds", {+!decide(Id)}).

+hello(AId)[source(A)]
	:	AId::winner(N) &
	  	N == A &
	  	origin(X, Y) &
	  	destiny(Dx, Dy) &
	  	myId(MId)
	<- 	removeAgent(MId, X, Y);
		.print("I received the message from ", A);
	   	.send(A, tell, destiny(Dx, Dy)).

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
