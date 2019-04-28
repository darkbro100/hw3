package me.paul.hw3.simulation;

import me.paul.hw3.simulation.grid.Board.Direction;
import me.paul.hw3.simulation.grid.Cell;

public class Coyote extends Agent<Coyote> {

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
			return;
		}
		
		Cell rCell = findRoadRunner();
		
		if(rCell == null) {
			Direction randomD = Direction.values()[getRandom().nextInt(Direction.values().length)];
			move(randomD);
		} else {
			int nX = rCell.getRow() - getCell().getRow();
			int nY = rCell.getColumn() - getCell().getColumn();
			
			Direction towards = Direction.valueOf(nX, nY);
			
			Agent<?> roadRunner = rCell.getOccupying();
			roadRunner.die();
			
			System.out.println("Coyote(" + getUuid() + ") just ate a RoadRunner(" + roadRunner.getUuid() + ")!");
			lastAte = simulationAge;
			
			move(towards);
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
				return new Coyote(o);
			}
		}
		return null;
	}
	
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