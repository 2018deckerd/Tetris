public class Tetris implements ArrowListener
{
	private BoundedGrid<Block> grid;	// The grid containing the Tetris pieces.
	private BlockDisplay display;		// Displays the grid.
	private Tetrad activeTetrad;		// The active Tetrad (Tetris Piece).

	// Constructs a Tetris Game
	public Tetris()
	{
		grid = new BoundedGrid(20, 10);
		display = new BlockDisplay(grid);
		display.setArrowListener(this);
		display.setTitle("Tetris");
		activeTetrad = new Tetrad(grid);
		display.showBlocks();
	}

	// Play the Tetris Game
	public void play()
	{
		while (true) {
			sleep(1);
			boolean canTranslate = activeTetrad.translate(1, 0);
			if (canTranslate == false) {
				clearCompletedRows();
				activeTetrad = new Tetrad(grid);
			} 
			display.showBlocks();
		}
		
	}


	// Precondition:  0 <= row < number of rows
	// Postcondition: Returns true if every cell in the given row
	//                is occupied; false otherwise.
	private boolean isCompletedRow(int row)
	{
		for (int i = 0; i < grid.getNumCols(); i++) {
			if (grid.get(new Location(row, i)) == null) {
				 return false;
			} 
		}
			return true;

	}

	// Precondition:  0 <= row < number of rows;
	//                The given row is full of blocks.
	// Postcondition: Every block in the given row has been removed, and
	//                every block above row has been moved down one row.
	private void clearRow(int row)
	{
		// removes every block in the given row 
		for (int i = 0; i < grid.getNumCols(); i++) {
			grid.get(new Location(row, i)).removeSelfFromGrid();
		}
		
		// shifts all the blocks above the row down one row
		for (int i = row - 1; i >= 0; i--) {
			for (int j = grid.getNumCols() - 1; j >= 0; j--) {
				Block item = grid.get(new Location(i, j));
				if (item != null) {
					Block newBlock = grid.get(new Location(i, j));
					newBlock.putSelfInGrid(grid, new Location(i +1, j));
					grid.remove(new Location(i, j));
			}
		}
	}
	}

	// Postcondition: All completed rows have been cleared.
	private void clearCompletedRows()
	{
		for (int i = 0; i < grid.getNumRows(); i++) {
			if (isCompletedRow(i)) {
				clearRow(i);
			}
		}
	}

	// Sleeps (suspends the active thread) for duration seconds.
	private void sleep(double duration)
	{
		final int MILLISECONDS_PER_SECOND = 1000;

		int milliseconds = (int)(duration * MILLISECONDS_PER_SECOND);

		try
		{
			Thread.sleep(milliseconds);
		}
		catch (InterruptedException e)
		{
			System.err.println("Can't sleep!");
		}
	}


	// Creates and plays the Tetris game.
	public static void main(String[] args)
	{
		Tetris newTetris = new Tetris();
		newTetris.play();
	}

	public void upPressed() {
		activeTetrad.rotate();
		// activeTetrad.translate(-1, 0);
		display.showBlocks();
		
	}

	public void downPressed() {
		activeTetrad.translate(1, 0);
		display.showBlocks();
		
	}

	public void leftPressed() {
		activeTetrad.translate(0, -1);
		display.showBlocks();
		
	}

	public void rightPressed() {
		activeTetrad.translate(0, 1);
		display.showBlocks();
	}
}
