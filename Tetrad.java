import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents a Tetris piece.
public class Tetrad
{
	private Block[] blocks;	// The blocks for the piece.

	// Constructs a Tetrad.
	public Tetrad(BoundedGrid<Block> grid)
	{
		blocks = new Block[4];
		Color colour;
		Location[] locArray = new Location[4];
		colour = Color.RED;
		Random auto = new Random();
		int n = auto.nextInt(7);
		// creates block objects for the blocks array
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = new Block();
		}
		if (n == 0) {
			// sets the location of the tetrad to be at the top of the grid (I-shaped)
			locArray[0] = new Location(0, 5);
			locArray[1] = new Location(0, 3);
			locArray[2] = new Location(0, 4);
			locArray[3] = new Location(0, 6);
			// Sets each block's color to that of the class (Red)
			for (int i = 0; i < blocks.length; i++) {
				blocks[i].setColor(colour);
			}
		} else if (n == 1) {
			colour = Color.MAGENTA;
			// creates a J shaped tetrad
			locArray[0] = new Location(0, 4);
			locArray[1] = new Location(0, 3);
			locArray[2] = new Location(0, 5);
			locArray[3] = new Location(1, 5);
			// Sets each block's color according to the specifications
			for (int i = 0; i < blocks.length; i++) {
				blocks[i].setColor(colour);
			}
		} else if (n == 2) {
			colour = Color.BLUE;
			// creates a S shaped tetrad
			locArray[0] = new Location(0, 4);
			locArray[1] = new Location(1, 4);
			locArray[2] = new Location(1, 3);
			locArray[3] = new Location(0, 5);
			// Sets each block's color according to the specifications
			for (int i = 0; i < blocks.length; i++) {
				blocks[i].setColor(colour);
			}
	
		} else if (n == 3) {
			colour = Color.GREEN;
			// creates a Z shaped tetrad
			locArray[0] = new Location(0, 4);
			locArray[1] = new Location(0, 3);
			locArray[2] = new Location(1, 4);
			locArray[3] = new Location(1, 5);
			// Sets each block's color according to the specifications
			for (int i = 0; i < blocks.length; i++) {
				blocks[i].setColor(colour);
			}
		} else if (n == 4) {
			colour = Color.CYAN;
			// creates an O shaped tetrad
			locArray[0] = new Location(0, 4);
			locArray[1] = new Location(0, 5);
			locArray[2] = new Location(1, 4);
			locArray[3] = new Location(1, 5);
			// Sets each block's color according to the specifications
			for (int i = 0; i < blocks.length; i++) {
				blocks[i].setColor(colour);
			}
		} else if (n == 5) {
			colour = Color.GRAY;
			// creates a T shaped tetrad
			locArray[0] = new Location(0, 4);
			locArray[1] = new Location(0, 3);
			locArray[2] = new Location(0, 5);
			locArray[3] = new Location(1, 4);
			// Sets each block's color according to the specifications
			for (int i = 0; i < blocks.length; i++) {
				blocks[i].setColor(colour);
			}
		} else if (n == 6) {
			colour = Color.YELLOW;
			// creates a L shaped tetrad
			locArray[0] = new Location(0, 4);
			locArray[1] = new Location(0, 3);
			locArray[2] = new Location(0, 5);
			locArray[3] = new Location(1, 3);
			// Sets each block's color according to the specifications
			for (int i = 0; i < blocks.length; i++) {
				blocks[i].setColor(colour);
			}
		}
		addToLocations(grid, locArray);
	}


	// Postcondition: Attempts to move this tetrad deltaRow rows down and
	//						deltaCol columns to the right, if those positions are
	//						valid and empty.
	//						Returns true if successful and false otherwise.
	public boolean translate(int deltaRow, int deltaCol)
	{
		Location[] oldLocations = new Location[4]; // stores the old locations
		Location[] newLocations = new Location[4]; // stores the new locations
		BoundedGrid<Block> grid;
		grid = blocks[0].getGrid();
		oldLocations = removeBlocks();
		for (int i = 0; i < oldLocations.length; i++) {
			newLocations[i] = new Location(oldLocations[i].getRow() + deltaRow, oldLocations[i].getCol() + deltaCol);
		}
		
		if (areEmpty(grid, newLocations)) {
			addToLocations(grid, newLocations); 
			return true;
		} else {
			addToLocations(grid, oldLocations);
		}
		return false;
	}

	// Postcondition: Attempts to rotate this tetrad clockwise by 90 degrees
	//                about its center, if the necessary positions are empty.
	//                Returns true if successful and false otherwise.
	public boolean rotate()
	{
		Location[] oldLocations = new Location[4];
		Location[] newLocations = new Location[4];
		BoundedGrid<Block> grid;
		grid = blocks[0].getGrid();
		Location rotationPoint = new Location(blocks[0].getLocation().getRow(), blocks[0].getLocation().getCol());
		oldLocations = removeBlocks();
		for (int i = 0; i < oldLocations.length; i++){
			newLocations[i] = new Location(rotationPoint.getRow() - rotationPoint.getCol() + oldLocations[i].getCol(), rotationPoint.getRow() + rotationPoint.getCol() - oldLocations[i].getRow());
		}
		
		if (areEmpty(grid, newLocations)) {
			addToLocations(grid, newLocations);
			return true;
		} else {
			addToLocations(grid, oldLocations);
		}
		return false;
	}


	// Precondition:  The elements of blocks are not in any grid;
	//                locs.length = 4.
	// Postcondition: The elements of blocks have been put in the grid
	//                and their locations match the elements of locs.
	private void addToLocations(BoundedGrid<Block> grid, Location[] locs)
	{
		for (int i = 0; i < 4; i++) {
			blocks[i].putSelfInGrid(grid, locs[i]);
		}
	}

	// Precondition:  The elements of blocks are in the grid.
	// Postcondition: The elements of blocks have been removed from the grid
	//                and their old locations returned.
	private Location[] removeBlocks()
	{
		Location[] oldLocations = new Location[blocks.length];
		for (int i = 0; i < blocks.length; i++) {
			oldLocations[i] = blocks[i].getLocation();
			blocks[i].removeSelfFromGrid();
		}
		return oldLocations;
	}

	// Postcondition: Returns true if each of the elements of locs is valid
	//                and empty in grid; false otherwise.
	private boolean areEmpty(BoundedGrid<Block> grid, Location[] locs)
	{
		boolean statement = true;
		List<Location> location = grid.getOccupiedLocations();
		for (int i = 0; i < locs.length; i++) {
			for (Location loc : location) {
			if (loc.equals(locs[i])) {
				statement = false;
		}
	}
			if (!grid.isValid(locs[i]) || statement == false) {
				return false;
			}
	}
		return statement;
  }
}
