package me.paul.hw3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.paul.hw3.simulation.SimulationBuilder;

/**
 * Main Driver Program that runs a {@link me.paul.hw3.Simulation}
 * @author Paul Guarnieri
 */
public class Main {
	/**
	 * SLF4J Logger implementation to make logging prettier
	 */
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		SettingsManager settings = SettingsManager.getInstance();
		settings.load();
		
		int rows = settings.getRows();
		int columns = settings.getColumns();
		int minPrey = settings.getMinPrey();
		int maxPrey = settings.getMaxPrey();
		int minPreds = settings.getMinPredators();
		int maxPreds = settings.getMaxPredators();
		
		SimulationBuilder builder = SimulationBuilder.getInstance();
		builder.createSimulation(rows, columns, minPreds, maxPreds, minPrey, maxPrey);
	}

	/**
	 * Get the SLF4J Logger implementation to make logging prettier
	 * @return The SLF4J Logger
	 */
	public static Logger getLogger() {
		return Main.logger;
	}
}
