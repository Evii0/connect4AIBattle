package Connect4;

public interface Connect4AI {
	
	/**
	 * chooses a move to make, returns the column to place a token in
	 * (ie it works like people playing connect 4 where you drop a token into a column)
	 * @param board
	 * @return
	 */
	public int chooseMove(int[][] board);
	
}
