package me.paul.hw3.simulation;

/**
 * Simple Singleton design which is used to create instances of a
 * {@link Simulation}
 * 
 * @author Paul Guarnieri
 */
public class SimulationBuilder {
	private static final SimulationBuilder instance = new SimulationBuilder();

	private SimulationBuilder() {
	}

	/**
	 * Create a {@link Simulation} given a bounded grid
	 * 
	 * @param rows    Number of rows in the grid
	 * @param columns Number of columns in the grid
	 * @return The generated {@link Simulation} with the given configuration
	 *         settings. If no max/min predators/prey are given, the default values
	 *         of 1 minimum and 10 maximum are used.
	 */
	public Simulation createSimulation(int rows, int columns) {
		return createSimulation(rows, columns, 1, 10, 1, 10);
	}

	/**
	 * Create a {@link Simulation} given a bounded grid and a maximum amount of
	 * predators and prey
	 * 
	 * @param rows         Number of rows in the grid
	 * @param columns      Number of columns in the gird
	 * @param maxPredators Maximum amount of predators that can spawn in the grid
	 * @param maxPrey      Maximum amount of prey that can spawn in the grid
	 * @return The generated {@link Simulation} with the given configuration
	 *         settings
	 */
	public Simulation createSimulation(int rows, int columns, int maxPredators, int maxPrey) {
		return createSimulation(rows, columns, 1, maxPredators, 1, maxPrey);
	}

	/**
	 * Create a {@link Simulation} given a bounded grid and a maximum and minimum
	 * amount of predators and prey.
	 * 
	 * @param rows         Number of rows in the grid
	 * @param columns      Number of columns in the grid
	 * @param minPredators Minimum amount of predators that can spawn in the grid
	 * @param maxPredators Maximum amount of predators that can spawn in the grid
	 * @param minPrey      Minimum amount of prey that can spawn in the grid
	 * @param maxPrey      Maximum amount of prey that can spawn in the grid
	 * @return The generated {@link Simulation} given the configuration settings
	 */
	public Simulation createSimulation(int rows, int columns, int minPredators, int maxPredators, int minPrey, int maxPrey) {
		Simulation s = new Simulation(rows, columns, minPredators, maxPredators, minPrey, maxPrey);
		return s;
	}

	/**
	 * Get the instance of {@link SimulationBuilder}
	 * @return An instance of {@link SimulationBuilder}
	 */
	public static SimulationBuilder getInstance() {
		return SimulationBuilder.instance;
	}
}
