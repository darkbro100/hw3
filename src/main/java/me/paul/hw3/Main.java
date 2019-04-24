package me.paul.hw3;

import java.util.List;

import me.paul.hw3.impl.Coyote;
import me.paul.hw3.impl.RoadRunner;
import me.paul.hw3.impl.Simulation;
import me.paul.hw3.impl.SimulationBuilder;

public class Main {

	public static void main(String[] args) {
		System.out.println("Normal simulation type shit...");
		SimulationBuilder builder = SimulationBuilder.getInstance();
		
		Simulation s = builder.createSimulation(5, 5, 5, 10, 5, 10);
		
		List<Coyote> coyotes = s.getBoard().getAgents(Coyote.class);
		List<RoadRunner> runners = s.getBoard().getAgents(RoadRunner.class);
		
		System.out.println("Coyotes: " + coyotes.size());
		System.out.println("RoadRunners: " + runners.size());
	}
	
}
