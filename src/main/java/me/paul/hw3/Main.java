package me.paul.hw3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import me.paul.hw3.simulation.SimulationBuilder;

/**
 * Main Driver Program that runs a {@link Simulation}
 * @author Paul Guarnieri
 *
 */
public class Main {

	/**
	 * SLF4J Logger implementation to make logging prettier
	 */
	@Getter
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		SimulationBuilder builder = SimulationBuilder.getInstance();
		builder.createSimulation(10,10, 10, 20, 10, 20);
	}
	
}
