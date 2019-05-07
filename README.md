# Pre Requisites To Run
In order to compile and run, you need:  
    - Maven (version 3.3.9)  
    - Java (version 8) (1.8.0_101)  

# How to compile and run
Enter the parent directory of the project (same directory that will contain the src folder, pom.xml, etc.)  

To compile the application, simply run `mvn install` on the command line  

The compiled application will be installed in the `final` directory under the name `Simulation.jar`

To run, simply run `java -jar Simulation.jar` from the command line. The first time you run it a `settings.properties` configuration file will be generated allowing you to change the configuration of the Simulation yourself

# To View Documentation
To view the documentation, simply open the file `doc/index.html`

# UML Key
White Open Arrow = Inheritance  
Black Closed Arrow = Composition  

The "S" next to fields or methods = Static  
The "F" next to fields or methods = Final  
The "C" = Constructors  
The "A" = Abstract/Virtual  

Green Circle = Public  
Hollow Red Square = Private Variables  
Filled Red Square = Private Methods  
Yellow Diamond = Protected  

## Viewing Log Files
You can at any point view the log file of a previous simulation you ran by viewing the `logs/` directory and simply viewing a log file of your choice 
