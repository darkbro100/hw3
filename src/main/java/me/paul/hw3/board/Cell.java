package me.paul.hw3.board;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

import lombok.Getter;
import me.paul.hw3.simulation.Agent;
import me.paul.hw3.simulation.Coyote;

@Getter
public class Cell extends JComponent {

	/**
	 * The width of each cell that will be drawn on the screen. Will also be used
	 * for height since they are equal in a Square
	 */
	public static final int SQUARE_WIDTH = 50;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3962034456495915406L;

	private final Board board;

	private final int row;
	private final int column;

	private Agent<? extends Agent<?>> occupying;

	private final JLabel label;
	
	public Cell(Board board, int row, int column) {
		this.board = board;
		this.row = row;
		this.column = column;

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		setVisible(true);
		setOpaque(false);
		
		this.label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVisible(true);
		label.setText("");
		
		add(label);
	}

	/**
	 * Set which {@link Agent} is currently occupying this Cell
	 * 
	 * @param agent Agent to set this Cell to, can be null
	 */
	public void setOccupying(Agent<? extends Agent<?>> agent) {
		this.occupying = agent;
		
		if(occupying == null) {
			label.setText("");
		} else {
			label.setText(occupying instanceof Coyote ? "Coyote" : "RoadRunner");
		}
	}

	public boolean isOccupied() {
		return occupying != null;
	}

	public double distance(Cell other) {
		return Math.sqrt(Math.pow(row - other.row, 2) + Math.pow(column - other.column, 2));
	}

}
