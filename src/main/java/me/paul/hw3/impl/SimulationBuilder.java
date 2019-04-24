package me.paul.hw3.impl;

import lombok.Getter;

public class SimulationBuilder {

	@Getter
	private static final SimulationBuilder instance = new SimulationBuilder();
	
	private SimulationBuilder() { }
	
	public Simulation createSimulation(int rows, int columns) {
		return createSimulation(rows, columns, 1, 10, 1, 10);
	}
	
	public Simulation createSimulation(int rows, int columns, int maxPredators, int maxPrey) {
		return createSimulation(rows, columns, 1, maxPredators, 1, maxPrey);
	}

	public Simulation createSimulation(int rows, int columns, int minPredators, int maxPredators, int minPrey, int maxPrey) {
		Simulation s = new Simulation(rows, columns, minPredators, maxPredators, minPrey, maxPrey);
		return s;
	}
	
}
