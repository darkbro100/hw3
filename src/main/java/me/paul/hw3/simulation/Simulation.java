package me.paul.hw3.simulation;

import java.util.SplittableRandom;

import javax.swing.JFrame;

import lombok.Getter;
import me.paul.hw3.board.Board;
import me.paul.hw3.board.Cell;

public class Simulation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1931152867981077508L;

	private int tick;
	@Getter
	private Board board;

	protected Simulation(int rows, int columns, int minPredators, int maxPredators, int minPrey, int maxPrey) {
		this.board = new Board(rows, columns);
		this.tick = 0;

		SplittableRandom random = new SplittableRandom();
		int prey = random.nextInt(minPrey, maxPrey + 1);
		int predators = random.nextInt(minPredators, maxPredators + 1);

		for (int i = 0; i < prey; i++) {
			Cell found = null;

			while (found == null) {
				int x = random.nextInt(rows);
				int y = random.nextInt(columns);

				found = board.findCell(x, y);
				if (found.isOccupied())
					found = null;
			}

			RoadRunner roadRunner = new RoadRunner(found);
			found.setOccupying(roadRunner);

			System.out.println(
					String.format("Spawned RoadRunner at location: (%s, %s)", found.getRow(), found.getColumn()));
		}

		for (int i = 0; i < predators; i++) {
			Cell found = null;

			while (found == null) {
				int x = random.nextInt(rows);
				int y = random.nextInt(columns);

				found = board.findCell(x, y);
				if (found.isOccupied())
					found = null;
			}

			Coyote coyote = new Coyote(found);
			found.setOccupying(coyote);

			System.out.println(String.format("Spawned Coyote at location: (%s, %s)", found.getRow(), found.getColumn()));
		}

		setVisible(true);
		setSize(640, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(board);
	}

	public void start() {

	}
	
//	private void drawCells() {
//		for(int i = 0; i < board.getRows(); i++) {
//			for(int j = 0; j < board.getColumns(); j++) {
//				Cell c = board.findCell(i, j);
//				add(c);
//			}
//		}
//	}

}
