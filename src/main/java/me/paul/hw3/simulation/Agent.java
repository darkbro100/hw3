package me.paul.hw3.simulation;

import java.util.SplittableRandom;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.paul.hw3.simulation.grid.Board;
import me.paul.hw3.simulation.grid.Board.Direction;
import me.paul.hw3.simulation.grid.Cell;

/**
 * Class represents an Agent that exists and live on the {@link Board} grid environment
 * 
 * @author Paul
 *
 */
@Getter(value = AccessLevel.PROTECTED) @Setter(value = AccessLevel.PROTECTED)
public abstract class Agent<T extends Agent<T>> {

	private final UUID uuid;

	private Cell cell;

	@Getter @Setter
	private int age;

	private final SplittableRandom random = new SplittableRandom();

	protected Agent(Cell cell) {
		this.cell = cell;
		this.uuid = UUID.randomUUID();
		this.age = 0;
		
		cell.setOccupying(this);
	}

	/**
	 * Move this cell to a new Cell
	 * 
	 * @param direction
	 *            Direction we want to move towards
	 *            
	 * @return Whether or not this {@link Agent} actually moved           
	 */
	public boolean move(Direction direction) {
		Board b = cell.getBoard();

		int rowOff = direction.getRowOffset();
		int colOff = direction.getColumnOffset();

		Cell nCell = b.findCell(cell.getRow() + rowOff, cell.getColumn() + colOff);

		if (nCell == null) {
			return false;
		}

		if (nCell.isOccupied()) {
			return false;
		}

		this.cell.setOccupying(null);
		nCell.setOccupying(this);

		this.cell = nCell;
		return true;
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
	 * Check is this {@link Agent} is dead 
	 * @return True if this {@link Agent} is dead
	 */
	public boolean isDead() {
		return cell == null;
	}

	/**
	 * Called to check if this {@link Agent} can breed yet
	 * @return True if this {@link Agent} can breed
	 */
	public abstract boolean shouldBreed();
	
	/**
	 * Called when this {@link Agent} breeds a new Agent
	 * 
	 * @return The new {@link Agent} that was bred.
	 */
	public abstract T breed();

	/**
	 * Called every tick (time step) of the simulation
	 */
	public void update() {
		age++;
	}

}
