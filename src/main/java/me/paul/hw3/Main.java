package me.paul.hw3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import me.paul.hw3.simulation.SimulationBuilder;

/*
 * Todos:
 * 	- Work on RoadRunner algorithm for running away from Coyotes
 *  - Fix issue with application freezing after running for x amount of steps
 */
public class Main {

	@Getter
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		SimulationBuilder builder = SimulationBuilder.getInstance();
		builder.createSimulation(10,10, 10, 20, 10, 20);
	}
	
}
