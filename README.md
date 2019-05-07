# Pre Requisites To Run
In order to compile and run, you need:  
    - Maven (version 3.3.9)  
    - Java (version 8) (1.8.0_101)  

# How to compile and run
Enter the parent directory of the project (same directory that will contain the src folder, pom.xml, etc.)  
To compile the application, simply run `mvn install` on the command line  
The compiled application will be installed in the `final` directory under the name `Simulation.jar`
To run, simply run `java -jar Simulation.jar` from the command line. The first time you run it a `settings.properties` configuration file will be generated allowing you to change the configuration of the Simulation yourself
