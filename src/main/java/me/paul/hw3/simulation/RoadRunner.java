package me.paul.hw3.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		
		List<Cell> coyotes = findCoyotes();
		
		if(coyotes.isEmpty()) {
			Direction randomD = Direction.values()[getRandom().nextInt(Direction.values().length)];
			move(randomD);
		} else {
			Direction d = runFrom(getCell(), coyotes);
			move(d);
			
//			coyotes = findCoyotes();
//			if(coyotes.size() >= 1) {
//				d = runFrom(getCell(), coyotes);
//				move(d);
//				System.out.println("Moved again!");
//			}
		}

		super.update();
		getCell().updateLabel();
	}
	
	private Direction runFrom(Cell position, List<Cell> cells) {
		double greatest = 0;
		Direction toReturn = Direction.NORTH;
		
		List<Direction> exclusions = new ArrayList<>();
		
		//Find least optimal directions to move in
		for(int i = 0; i < cells.size(); i++) {
			Cell c = cells.get(i);
			
			int diffX = c.getRow() - position.getRow();
			int diffY = c.getColumn() - position.getColumn();
			
			Direction towards = Direction.valueOf(diffX, diffY);
			if(towards.getColumnOffset() == 0 || towards.getRowOffset() == 0) {
				for(int r = -1; r <= 1; r++) {
					boolean useRows = towards.getRowOffset() == 0;
					exclusions.add(Direction.valueOf(useRows ? r : towards.getRowOffset(), useRows ? towards.getColumnOffset() : r));
				}
			} else {
				exclusions.add(Direction.valueOf(0, towards.getColumnOffset()));
				exclusions.add(Direction.valueOf(towards.getRowOffset(), 0));
			}
			
			exclusions.add(towards);
		}
		
		List<Direction> allowed = Stream.of(Direction.values()).filter(e -> !exclusions.contains(e)).collect(Collectors.toList());
		for(Direction d : allowed) {
			Cell r = position.getRelative(d);
			
			if(r == null || r.isOccupied())
				continue;
			
			double totalD = 0;
			for(Cell c : cells) totalD += c.distance(r);
			
			double avg = totalD / ((double)cells.size());
			
			if(avg > greatest) {
				toReturn = d;
				greatest = avg;
			}
		}
		
		return toReturn;
	}
	
	public List<Cell> findCoyotes() {
		return findCoyotes(getCell());
	}
	
	private List<Cell> findCoyotes(Cell position) {
		List<Cell> toReturn = new ArrayList<>();
		
		for(Direction dir : Direction.values()) {
			Cell oC = position.getRelative(dir);
			
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
