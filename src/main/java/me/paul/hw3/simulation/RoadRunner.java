package me.paul.hw3.simulation;

import java.util.ArrayList;
import java.util.List;

import me.paul.hw3.simulation.grid.Board.Direction;
import me.paul.hw3.simulation.grid.Cell;

public class RoadRunner extends Agent<RoadRunner> {

	protected RoadRunner(Cell cell) {
		super(cell);
	}

	@Override
	public RoadRunner breed() {
		Cell c = getCell();
		for(Direction d : Direction.values()) {
			Cell o = c.getRelative(d);
			
			if(o != null && !o.isOccupied()) {
				return new RoadRunner(o);
			}
		}
		return null;
	}

	@Override
	public void update() {
		if(isDead())
			return;
		
		super.update();
		
		List<Cell> coyotes = findCoyotes();
		
		if(coyotes.isEmpty()) {
			Direction randomD = Direction.values()[getRandom().nextInt(Direction.values().length)];
			move(randomD);
		} else {
			
		}
		
		getCell().updateLabel();
	}
	
	public List<Cell> findCoyotes() {
		List<Cell> toReturn = new ArrayList<>();
		
		for(Direction dir : Direction.values()) {
			Cell oC = getCell().getRelative(dir);
			
			if(oC != null && oC.isOccupied() && oC.getOccupying() instanceof Coyote)
				toReturn.add(oC);
		}
		
		return toReturn;
	}

	@Override
	public boolean shouldBreed() {
		if(getAge() % 3 != 0) {
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
