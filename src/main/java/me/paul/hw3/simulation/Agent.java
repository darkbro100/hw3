package me.paul.hw3.simulation;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import me.paul.hw3.board.Board;
import me.paul.hw3.board.Board.Direction;
import me.paul.hw3.board.Cell;

/**
 * Class that will represents the Agents that will be interacting with each
 * other in the Simulation
 * 
 * @author Paul
 *
 */
public abstract class Agent<T extends Agent<T>> {
	
	@Getter
	private final UUID uuid;
	
	@Getter
	private Cell cell;
	
	@Getter @Setter
	private int age;

	protected Agent(Cell cell) {
		this.cell = cell;
		this.uuid = UUID.randomUUID();
		this.age = 0;
	}
	
	/**
	 * Move this cell to a new Cell
	 * 
	 * @param direction Direction we want to move towards
	 */
	public void move(Direction direction) {
		Board b = cell.getBoard();
		
		int rowOff = direction.getRowOffset();
		int colOff = direction.getColumnOffset();
		
		Cell nCell = b.findCell(cell.getRow() + rowOff, cell.getColumn() + colOff);
		
		if(nCell == null) {
			//uhhhh
		}
		
		this.cell.setOccupying(null);
		nCell.setOccupying(this);
		
		this.cell = nCell;
	}

	/**
	 * Called when this Agent is killed during the {@link Simulation}
	 */
	public void die() {
		this.cell.setOccupying(null);
		this.cell = null;
		this.age = -1;
	}

	/**
	 * Called when this {@link Agent} breeds a new Agent
	 * 
	 * @return The new {@link Agent} that was bred.
	 */
	public abstract T breed();

	/**
	 * Called every tick (time step) of the simulation
	 */
	public abstract void update();
	
}
