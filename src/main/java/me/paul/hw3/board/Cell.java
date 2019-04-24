package me.paul.hw3.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.paul.hw3.impl.Agent;

@RequiredArgsConstructor @Getter
public class Cell {

	private final Board board;
	
	private final int row;
	private final int column;
	
	private Agent<? extends Agent<?>> occupying;

	/**
	 * Set which {@link Agent} is currently occupying this Cell
	 * @param agent Agent to set this Cell to, can be null
	 */
	public void setOccupying(Agent<? extends Agent<?>> agent) {
		this.occupying = agent;
	}
	
	public boolean isOccupied() {
		return occupying != null;
	}
	
	public double distance(Cell other) {
		return Math.sqrt(Math.pow(row - other.row, 2) + Math.pow(column - other.column, 2));
	}
	
}
