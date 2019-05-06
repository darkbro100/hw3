package me.paul.hw3.simulation;

import java.util.SplittableRandom;
import me.paul.hw3.Main;
import me.paul.hw3.simulation.grid.Board;
import me.paul.hw3.simulation.grid.Board.Direction;
import me.paul.hw3.simulation.grid.Cell;

/**
 * Class represents an Agent that exists and live on the {@link Board} grid
 * environment
 * 
 * @author Paul Guarnieri
 */
public abstract class Agent<T extends Agent<T>> {
	/**
	 * A counter used to give each {@link Agent} a unique ID
	 */
	private static int agentCounter = 0;
	/**
	 * The id of this {@link Agent} instance
	 */
	private final int id;
	/**
	 * The {@link Cell} that this {@link Agent} belongs to. Will only be null if
	 * {@link #isDead()} returns true
	 */
	private Cell cell;
	/**
	 * How long this {@link Agent} has been living in the {@link Simulation} for
	 */
	private int age;
	/**
	 * An instance of {@link SplittableRandom} to generate any random numbers at
	 * some point
	 */
	private final SplittableRandom random = new SplittableRandom();

	/**
	 * Constructs a new {@link Agent} and adds it to a given {@link Cell}
	 * 
	 * @param cell {@link Cell} to spawn this {@link Agent} on
	 */
	protected Agent(Cell cell) {
		this.cell = cell;
		this.id = agentCounter;
		this.age = 0;
		cell.setOccupying(this);
		agentCounter++;
	}

	/**
	 * Move this cell to a new Cell
	 * 
	 * @param direction Direction we want to move towards
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
		String from = String.format("(%s, %s)", cell.getRow(), cell.getColumn());
		String to = String.format("(%s,%s)", nCell.getRow(), nCell.getColumn());
		Main.getLogger().info(getClass().getSimpleName() + "(" + getId() + ") is moving " + direction.getShortHand()
				+ " from " + from + " to " + to);
		this.cell.setOccupying(null);
		nCell.setOccupying(this);
		this.cell = nCell;
		return true;
	}

	/**
	 * Try to move this {@link Agent} in any random direction. If there are no
	 * available directions to move in, this {@link Agent} will not move
	 * 
	 * @return Whether or not this {@link Agent} successfully moved or not
	 */
	public boolean arbitaryMove() {
		Direction[] dirs = Direction.values();
		int starting = getRandom().nextInt(dirs.length);
		int i = starting == 0 ? dirs.length - 1 : starting - 1;
		Direction randomD = dirs[starting];
		while (!move(randomD)) {
			if (i == starting) {
				return false;
			}
			randomD = dirs[i];
			i--;
			if (i < 0)
				i = dirs.length - 1;
		}
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
	 * 
	 * @return True if this {@link Agent} is dead
	 */
	public boolean isDead() {
		return age == -1;
	}

	/**
	 * Called to check if this {@link Agent} can breed yet
	 * 
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

	/**
	 * @return The id of this {@link Agent} instance
	 */
	protected int getId() {
		return this.id;
	}

	/**
	 * @return The {@link Cell} that this {@link Agent} belongs to. Will only be
	 *         null if {@link #isDead()} returns true
	 */
	protected Cell getCell() {
		return this.cell;
	}

	/**
	 * An instance of {@link SplittableRandom} to generate any random numbers at
	 * some point
	 */
	protected SplittableRandom getRandom() {
		return this.random;
	}

	/**
	 * Set this {@link Agent}'s cell
	 * 
	 * @param cell Cell we want to set this {@link Agent} to
	 */
	protected void setCell(final Cell cell) {
		this.cell = cell;
	}

	/**
	 * Get the age of this {@link Agent}
	 * 
	 * @return How long this {@link Agent} has been living in the {@link Simulation}
	 *         for
	 */
	public int getAge() {
		return this.age;
	}

}
