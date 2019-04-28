package me.paul.hw3.simulation;

import java.awt.BorderLayout;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

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
	
	private int minPrey,maxPrey;
	private int minPreds,maxPreds;
	
	private boolean started = false;
	
	protected Simulation(int rows, int columns, int minPredators, int maxPredators, int minPrey, int maxPrey) {
		this.board = new Board(this, rows, columns);
		this.tick = 0;

		this.minPreds = minPredators;
		this.maxPreds = maxPredators;
		
		this.minPrey = minPrey;
		this.maxPrey = maxPrey;
		
		setVisible(true);
		updateTitle();
		setSize(640, 480);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(board);
		
		setupButton();
	}
	
	private void startSimulation() {
		if(started)
			throw new RuntimeException("Simulation already started!");
		started = true;
		
		SplittableRandom random = new SplittableRandom();
		int prey = random.nextInt(minPrey, maxPrey + 1);
		int predators = random.nextInt(minPreds, maxPreds + 1);

		for (int i = 0; i < prey; i++) {
			Cell found = null;

			while (found == null) {
				int x = random.nextInt(getBoard().getRows());
				int y = random.nextInt(getBoard().getColumns());

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
				int x = random.nextInt(getBoard().getRows());
				int y = random.nextInt(getBoard().getColumns());

				found = board.findCell(x, y);
				if (found.isOccupied())
					found = null;
			}

			new Coyote(found);

			System.out.println(String.format("Spawned Coyote at location: (%s, %s)", found.getRow(), found.getColumn()));
		}
		
		updateTitle();
	}

	private void setupButton() {
		this.stepButton = new JButton("Click to start!");
		stepButton.setVisible(true);
		
		stepButton.addActionListener((e) -> {
			if(!started) {
				startSimulation();
				stepButton.setText("Next Step");
				return;
			}

			System.out.println("\n\n#########  Step " + tick + "  ##########\n\n");
			
			List<Agent<?>> agents = board.getAllAgents();
			
			//Since this is technically a turn based simulation since it runs on 1 thread, we need to shuffle the order of the turns each step to keep it as accurate as possible
			Collections.shuffle(agents);
			
			//Update each agent
			agents.forEach(Agent::update);
			
			//Remove any invalid Agents (any that were killed)
			agents = agents.stream().filter(a -> !a.isDead()).collect(Collectors.toList());
			
			//Check breeding process
			agents.stream().filter(Agent::shouldBreed).forEach(Agent::breed);

			tick++; //Prep for next tick
			
			//Update title of JFrame
			updateTitle();
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

	private void updateTitle() {
		if(!started)
			setTitle("Simulation | READY");
		else
			setTitle("Simulation | Time Step: " + tick);
	}
	
}
