package me.paul.hw3.simulation;

import java.awt.BorderLayout;
import java.util.SplittableRandom;

import javax.swing.JButton;
import javax.swing.JFrame;

import lombok.Getter;
import me.paul.hw3.simulation.grid.Board;
import me.paul.hw3.simulation.grid.Cell;

public class Simulation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1931152867981077508L;

	@Getter
	private int tick;
	@Getter
	private Board board;

	private JButton stepButton;
	
	protected Simulation(int rows, int columns, int minPredators, int maxPredators, int minPrey, int maxPrey) {
		this.board = new Board(this, rows, columns);
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

			new RoadRunner(found);

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

			new Coyote(found);

			System.out.println(String.format("Spawned Coyote at location: (%s, %s)", found.getRow(), found.getColumn()));
		}

		setVisible(true);
		setTitle("Simulation | Time Step: " + tick);
		setSize(640, 480);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(board);
		
		setupButton();
	}

	private void setupButton() {
		this.stepButton = new JButton("Next Step");
		stepButton.setVisible(true);
		
		stepButton.addActionListener((e) -> {
			tick++;
			board.getAllAgents().forEach(Agent::update);
			board.getAllAgents().stream().filter(a -> a.shouldBreed()).forEach(a -> a.breed());
			
			setTitle("Simulation | Time Step: " + tick);
		});
		
		add(stepButton, BorderLayout.SOUTH);
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
