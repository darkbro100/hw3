package me.paul.hw3.simulation;

import java.util.SplittableRandom;

import lombok.Getter;
import me.paul.hw3.board.Board;
import me.paul.hw3.board.Cell;

public class Simulation {

	private int tick;
	@Getter
	private Board board;
	
	protected Simulation(int rows, int columns, int minPredators, int maxPredators, int minPrey, int maxPrey) {
		this.board = new Board(rows, columns);
		this.tick = 0;
		
		SplittableRandom random = new SplittableRandom();
		int prey = random.nextInt(minPrey, maxPrey+1);
		int predators = random.nextInt(minPredators, maxPredators+1);
		
		for(int i = 0; i < prey; i++) {
			Cell found = null;
			
			while(found == null) {
				int x = random.nextInt(rows);
				int y = random.nextInt(columns);
				
				found = board.findCell(x, y);
				if(found.isOccupied())
					found = null;
			}
			
			RoadRunner roadRunner = new RoadRunner(found);
			found.setOccupying(roadRunner);
			
			System.out.println(String.format("Spawned RoadRunner at location: (%s, %s)", found.getRow(), found.getColumn()));
		}
		
		for(int i = 0; i < predators; i++) {
			Cell found = null;
			
			while(found == null) {
				int x = random.nextInt(rows);
				int y = random.nextInt(columns);
				
				found = board.findCell(x, y);
				if(found.isOccupied())
					found = null;
			}
			
			Coyote coyote = new Coyote(found);
			found.setOccupying(coyote);
			
			System.out.println(String.format("Spawned Coyote at location: (%s, %s)", found.getRow(), found.getColumn()));
		}
	}
	
	public void start() {
		
	}
	
}
