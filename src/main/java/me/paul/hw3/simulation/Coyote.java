package me.paul.hw3.simulation;

import me.paul.hw3.simulation.grid.Board.Direction;
import me.paul.hw3.Main;
import me.paul.hw3.simulation.grid.Cell;

/**
 * An implementation of {@link Agent} in the form of a predator living in the {@link Simulation}
 * @author Paul Guarnieri
 *
 */
public class Coyote extends Agent<Coyote> {

	/**
	 * The last time this {@link Coyote} ate a {@link RoadRunner}
	 */
	private int lastAte;
	
	protected Coyote(Cell cell) {
		super(cell);
		
		this.lastAte = cell.getBoard().getSimulation().getTick();
	}

	@Override
	public void update() {
		if(isDead())
			return;
		
		int simulationAge = getCell().getBoard().getSimulation().getTick();
		int ateSince = simulationAge - lastAte;
		
		if(ateSince >= 4) {
			die();
			Main.getLogger().warn("Coyote(" + getId() + ") died to starvation!");
			return;
		}
		
		Cell rCell = findRoadRunner();
		
		if(rCell == null) {
			arbitaryMove();
		} else {
			int nX = rCell.getRow() - getCell().getRow();
			int nY = rCell.getColumn() - getCell().getColumn();
			
			Direction towards = Direction.valueOf(nX, nY);
			
			Agent<?> roadRunner = rCell.getOccupying();
			roadRunner.die();
			
			lastAte = simulationAge;
			
			move(towards);
			
			Main.getLogger().warn("Coyote(" + getId() + ") just ate RoadRunner(" + roadRunner.getId() + ") at cell " + String.format("(%s,%s)", rCell.getRow(), rCell.getColumn()));
		}
		
		super.update();
		getCell().updateLabel();
	}

	@Override
	public Coyote breed() {
		Cell c = getCell();
		for(Direction d : Direction.values()) {
			Cell o = c.getRelative(d);
			
			if(o != null && !o.isOccupied()) {
				Main.getLogger().info("Coyote just bred a new Coyote at cell " + String.format("(%s,%s)", o.getRow(), o.getColumn()));
				return new Coyote(o);
			}
		}
		return null;
	}
	
	/**
	 * Find any nearby {@link RoadRunner} (1 tile) and the {@link Cell} it belongs to
	 * @return The {@link Cell} that is being occupied by a {@link RoadRunner}, if there are any
	 */
	private Cell findRoadRunner() {
		for(Direction d : Direction.values()) {
			Cell c = getCell().getRelative(d);
			if(c != null && c.isOccupied() && c.getOccupying() instanceof RoadRunner) {
				return c;
			}
		}
		
		return null;
	}

	@Override
	public boolean shouldBreed() {
		if(getAge() % 8 != 0) {
			return false;
		}
		
		for(Direction dir : Direction.values()) {
			Cell relative = getCell().getRelative(dir);
			
			if(relative != null && !relative.isOccupied())
				return true;
		}
		
		return false;
	}

}
