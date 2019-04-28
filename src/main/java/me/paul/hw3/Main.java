package me.paul.hw3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import me.paul.hw3.simulation.SimulationBuilder;

public class Main {

	@Getter
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		logger.debug("Building simulation...");
		logger.info("TEST");
		SimulationBuilder builder = SimulationBuilder.getInstance();
		builder.createSimulation(9,9, 10, 20, 10, 20);
	}
	
}
