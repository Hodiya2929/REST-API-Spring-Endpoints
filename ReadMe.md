# Read Me First

In the folder "Description" -> Project.pdf there is the task description.

This REST aPI exposes two endpoints to the user: "connections" and "statistics"
 
To run this project you must have Java installed on your machine.

In the main folfer there is a jar file named: "connection-statistics-0.0.1-SNAPSHOT.jar". open cmd in the current folder

and type: 

	java -jar <jar filename.jar> <arguments>
	
arguments are the json files that describes the environment: input-0.json / input-1.json / input-2.json / input-3.json

You can pass one argument at a time.

So for example a valid running command will be: "java -jar connection-statistics-0.0.1-SNAPSHOT.jar input-3.json"

When you see a "SPRING" on the cmd woth some messages ("Started ConnectionStatisticsApplication in x.xyz seconds") - success.
	
The service runs as long as the cmd window is open: 

now REST service ip up and you can access it by:

http://localhost:8080/api/v1/statistics

or in case you loaded input-3.json:

http://localhost:8080/api/v1/connections?host_id=host-ab51cba10


	
