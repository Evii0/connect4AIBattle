package Connect4;

import java.util.Random;

public class PetersAI implements Connect4AI {

	public PetersAI() {
		
	}
	
	@Override
	public int chooseMove(int[][] board) {
		Random rand = new Random();
		int column = rand.nextInt(7);
		
		while(board[column][5] != 0)
			column = rand.nextInt(7);
		
		return column;
	}
	
}