package me.paul.hw3.simulation.grid;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.paul.hw3.simulation.Agent;
import me.paul.hw3.simulation.Simulation;

/**
 * This is the class that will represent the grid environment for a {@link Simulation} object
 * @author Paul
 *
 */
public class Board extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7157079009555542796L;

	private Cell[][] cells;
	
	@Getter
	private int rows;
	@Getter
	private int columns;
	
	@Getter
	private Simulation simulation;
	
	public Board(Simulation simulation, int rows, int columns) {
		this.simulation = simulation;
		this.rows = rows;
		this.columns = columns;
		
		this.cells = new Cell[rows][columns];
		
		setLayout(new GridLayout(rows, columns));
		setOpaque(false);
		
		fillGrid();
	}
	
	/**
	 * Method that will be used to fill our grid space environment. Will only be called once during the constructor
	 */
	private void fillGrid() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				Cell c = new Cell(this, i, j);
				cells[i][j] = c;
				
				add(c);
			}
		}
	}
	
	public boolean inBounds(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	/**
	 * Locate a cell in the grid space environment
	 * @param row The row where the cell is located
	 * @param column The column where the cell is located
	 * @return The cell, if found
	 */
	public Cell findCell(int row, int column) {
		if(!inBounds(row, column))
			return null;
		
		return cells[row][column];
	}
	
	/**
	 * Find the {@link Agent} in this {@link Board} grid environment
	 * @param agentClazz The class that represents the specific Agent we are trying to find
	 * @return List of the found Agents
	 */
	@SuppressWarnings("unchecked")
	public <T extends Agent<T>> List<T> getAgents(Class<T> agentClazz) {
		List<T> toReturn = new ArrayList<>();
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				Cell c = cells[i][j];
				
				if(c.isOccupied() && agentClazz.isInstance(c.getOccupying()))
					toReturn.add((T) c.getOccupying());
			}
		}
		
		return toReturn;
	}
	
	/**
	 * @return List of all the {@link Agent} currently residing in the {@link Board}
	 */
	public List<Agent<?>> getAllAgents() {
		List<Agent<?>> toReturn = new ArrayList<>();
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				Cell c = cells[i][j];
				
				if(c.isOccupied())
					toReturn.add(c.getOccupying());
			}
		}
		
		return toReturn;
	}
	
	@RequiredArgsConstructor @Getter
	public enum Direction {

		NORTH(1,0),
		SOUTH(-1,0),
		EAST(0,1),
		NORTHEAST(1, 1),
		SOUTHEAST(-1, 1),
		WEST(0,-1),
		SOUTHWEST(-1, -1),
		NORTHWEST(1, -1);

		private final int rowOffset;
		private final int columnOffset;
		
		public static Direction valueOf(int row, int col) {
			for(Direction d : values()) {
				if(d.rowOffset == row && d.columnOffset == col)
					return d;
			}
			
			return null;
		}
		
	}
	
}
