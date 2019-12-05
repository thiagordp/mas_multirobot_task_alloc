// Agent task_creator in project mas_multirobot_task_alloc

/* Initial beliefs and rules */

count_robots(0).
count_tasks(0).
random_wait(R) :- R = math.round(math.random * 2000) + 500.

/* Initial goals */
!start.

/* Plans */
+!start <-
	.print("Initiating creator");
	.df_register(create);
	!create_robots;
	!create_tasks.
		
/*
 * When a task is finished, it sends a message to decrement the task counter.
 */ 
+task_finish(T)
	:	count_tasks(C)
	<- 	-+count_tasks(C - 1).

/*
 * When a robot is shut down, it sends a message to decrement the robot counter.
 */ 
+robot_finish(T)
	:	count_robots(C)
	<-	-+count_robots(C - 1).

/*
 * Periodic task creation
 */
+!create_tasks 
	:	random_wait(R) &
		count_tasks(C) &
		tasks(T) & 
		C < T 
	 <- .print("Creating tasks. ", (R/1000));
	 	.wait(R);
		task.create_task;
		-+count_tasks(C+1);
		!create_robots.

/*
 * Other cases, just wait.
 */
+!create_tasks 
	:	random_wait(R)  
	<- 	.wait(R);
		!create_robots.

/*
 * Periodic robot creation
 */	
+!create_robots
	:	random_wait(R) &
		count_robots(C) &
		robots(T) & 
		C < T 
 	<-  .print("Creating robots. ", (R/1000));
	 	.wait(R);
	 	.concat("robot", R, St);
		.create_agent(St, "robot.asl");
		-+count_robots(C+1);
		!create_tasks.

/*
 * Other cases, just wait.
 */
+!create_robots
	:	random_wait(R)  
	<- 	.wait(R);
		!create_tasks.

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
