package Connect4;

import java.util.Random;

public class Connect4 {
	int[][] grid = new int[7][6];
	int currentPlayer = 1;

	public Connect4() {
		playConnect4();
		printBoard();
	}
	
	private void playConnect4() {
		Connect4AI player1 = null;
		Connect4AI player2 = null;
		
		int startingPlayer = new Random().nextInt(2) + 1;
		if(startingPlayer == 1) {
			player1 = new PetersAI();
			player2 = new PetersAI();
			System.out.println("Player 1: Peter\nPlayer 2: Jonah");
		}
		else {
			player1 = new PetersAI();
			player2 = new PetersAI();
			System.out.println("Player 1: Jonah\nPlayer 2: Peter");
		}
		
		int player = 0;
		
		for(int i = 0; i < 50; i++) {
			if(!makeMove(player1.chooseMove(grid), 1)) {
				int count = 0;
				while(count < 4 && !makeMove(player1.chooseMove(grid), 1))count++;
				if(count == 5) {
					System.out.println("Player 1 failed to make a valid move, Player 2 wins");
					return;
				}
			}
			player = isGameOver();
			if(player != 0) {
				if(player == 3)System.out.println("No one won :'(");
				else System.out.println("Player " + player + " wins!");
				return;
			}
			
			printBoard();
			
			if(!makeMove(player2.chooseMove(grid), 2)){
				int count = 0;
				while(count < 4 && !makeMove(player2.chooseMove(grid), 2))count++;
				if(count == 5) {
					System.out.println("Player 2 failed to make a valid move, Player 1 wins");
					return;
				}
			}
			player = isGameOver();
			if(player != 0) {
				if(player == 3)System.out.println("No one won :'(");
				else System.out.println("Player " + player + " wins!");
				return;
			}
			
			printBoard();
		}
	}

	private boolean makeMove(int col, int player) {
		for (int i = 0; i < 6; i++) {
			if (i == 5 && grid[col][i] != 0) {
				System.out.println("Invalid move, there is no space in that column");
				return false;
			}
			if (grid[col][i] == 0) {
				grid[col][i] = player;
				return true;
			}
		}
		System.out.println("Something bust, flame the developer.");
		return false;
	}

	private int isGameOver() {
		// check verticals
		for (int x = 0; x < 7; x++) {
			int chainLength = 0;
			int player = grid[x][0];

			for (int y = 0; y < 6; y++) {
				if (grid[x][y] == player) {
					chainLength++;
				} 
				else {
					chainLength = 1;
					player = grid[x][y];
				}
				
				if (chainLength == 4 && player != 0)
					return player;
			}
		}

		// check horizontals
		for (int y = 0; y < 6; y++) {
			int chainLength = 0;
			int player = grid[0][y];

			for (int x = 0; x < 7; x++) {
				if (grid[x][y] == player) {
					chainLength++;
				} 
				else {
					chainLength = 1;
					player = grid[x][y];
				}
				
				if (chainLength == 4 && player != 0)
					return player;
			}
		}
		
		//check diagonals
		for(int x = 0; x < 7; x++) {
			for(int y = 0; y < 3; y++) {
				if(grid[x][y] == 0) continue;
				int player = grid[x][y];
				
				if(x < 4) {
					if(player == grid[x+1][y+1]) {
						int tempX = x + 1;
						int tempY = y + 1;
						int chainLength = 1;
						while(player == grid[tempX][tempY]) {
							if(chainLength == 4) return player;
							chainLength++;
							tempX++;
							tempY++;
						}
					}
				}
				
				if(x > 2) {
					if(player == grid[x-1][y+1]) {
						int tempX = x - 1;
						int tempY = y + 1;
						int chainLength = 1;
						while(player == grid[tempX][tempY]) {
							if(chainLength == 4) return player;
							chainLength++;
							tempX--;
							tempY++;
						}
					}
				}
			}
		}
		
		//check for draw
		boolean isFull = true;
		for(int x = 0; x < 7; x++) {
			for(int y = 0; y < 6; y++) {
				if(grid[x][y] == 0)isFull = false;
			}
		}
		if(isFull)return 3;

		return 0;
	}
	
	private void printBoard() {
		for(int y = 5; y >= 0; y--) {
			System.out.println("| " + grid[0][y] + " "+ grid[1][y] + " "+ grid[2][y] + " "+ grid[3][y] + " "+ grid[4][y] + " "+ grid[5][y] + " "+ grid[6][y] + " |");
		}
		System.out.println("");
	}

	public static void main(String[] args) {
		new Connect4();
	}
}
