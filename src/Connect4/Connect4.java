package Connect4;

import java.util.Random;

public class Connect4 {
	int[][] grid = new int[7][6];
	int currentPlayer = 1;

	public Connect4() {
//		int hCount = 0;
//		int rCount = 0;
//		int draw = 0;
//		
//		for(int i = 0; i < 100; i++) {
//			String result = playConnect4();
//			printBoard();
//			if(result.equals("HeuristicAI"))hCount++;
//			else if(result.equals("-1"))draw++;
//			else rCount++;
//			grid = new int[7][6];
//		}
//		System.out.println("Heuristic: " + hCount);
//		System.out.println("Random: " + rCount);
//		System.out.println("Draw: " + draw);
		grid[0][0] = 1;
		grid[1][0] = 2;
		grid[2][0] = 0;
		grid[3][0] = 2;
		grid[4][0] = 2;
		grid[5][0] = 2;
		grid[6][0] = 0;
		
		grid[0][1] = 1;
		grid[1][1] = 1;
		grid[2][1] = 0;
		grid[3][1] = 2;
		grid[4][1] = 0;
		grid[5][1] = 0;
		grid[6][1] = 0;
		
		grid[0][2] = 1;
		grid[1][2] = 0;
		grid[2][2] = 0;
		grid[3][2] = 0;
		grid[4][2] = 0;
		grid[5][2] = 0;
		grid[6][2] = 0;
		
//		grid[0][3] = 1;
//		grid[1][3] = 2;
//		grid[2][3] = 2;
//		grid[3][3] = 1;
//		grid[4][3] = 2;
//		grid[5][3] = 0;
//		grid[6][3] = 0;
//		
//		grid[0][4] = 0;
//		grid[1][4] = 0;
//		grid[2][4] = 0;
//		grid[3][4] = 2;
//		grid[4][4] = 2;
//		grid[5][4] = 0;
//		grid[6][4] = 0;
//		printBoard();
//		System.out.println(isGameOver());
//		System.out.println(new HeuristicAI(2).canWinOrLose(grid));
		
//		playConnect4();
//		printBoard();
		
		LookingAheadAI player1 = new LookingAheadAI(1);
		makeMove(player1.chooseMove(grid), 1);
		printBoard();
	}
	
	private String playConnect4() {
		Connect4AI player1 = null;
		Connect4AI player2 = null;
		String player1Name = "";
		String player2Name = "";
		
		int startingPlayer = new Random().nextInt(2) + 1;
		if(startingPlayer == 1) {
			player1 = new LookingAheadAI(1);
			player2 = new HeuristicAI(2);
			player1Name = "LookingAheadAI";
			player2Name = "HeuristicAI";
			System.out.println("Player 1: LookingAheadAI\nPlayer 2: HeuristicAI");
		}
		else {
			player1 = new HeuristicAI(1);
			player2 = new LookingAheadAI(2);
			player2Name = "HeuristicAI";
			player1Name = "LookingAheadAI";
			System.out.println("Player 1: HeuristicAI\nPlayer 2: LookingAheadAI");
		}
		
		int player = 0;
		
		for(int i = 0; i < 50; i++) {
			if(!makeMove(player1.chooseMove(grid), 1)) {
				int count = 0;
				while(count < 4 && !makeMove(player1.chooseMove(grid), 1))count++;
				if(count == 5) {
					System.out.println(player1Name + "failed to make a valid move, " + player2Name + " wins");
					return player2Name;
				}
			}
			
			player = isGameOver();
			if(player != 0) {
				if(player == 3) {
					System.out.println("No one won :'(");
					return "-1";
				}
				else {
					if(player == 1) {
						System.out.println(player1Name + " wins!");
						return player1Name;
					}
					if(player == 2) {
						System.out.println(player2Name + " wins!");
						return player2Name;
					}
				}
			}
			
			printBoard();
			
			if(!makeMove(player2.chooseMove(grid), 2)){
				int count = 0;
				while(count < 4 && !makeMove(player2.chooseMove(grid), 2))count++;
				if(count == 5) {
					System.out.println(player2Name + " failed to make a valid move, " + player1Name + " wins");
					return player1Name;
				}
			}
			
			player = isGameOver();
			if(player != 0) {
				if(player == 3) {
					System.out.println("No one won :'(");
					return "-1";
				}
				else {
					if(player == 1) {
						System.out.println(player1Name + " wins!");
						return player1Name;
					}
					if(player == 2) {
						System.out.println(player2Name + " wins!");
						return player2Name;
					}
				}
			}
			
			printBoard();
		}
		return "";
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
				if(grid[x][y] != 0) {
					if (grid[x][y] == player)
						chainLength++;
					else {
						player = grid[x][y];
						chainLength = 1;
					}
				}
				else 
					chainLength = 0;
				
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
				
				if(player == 0) {
					chainLength = 0;
					continue;
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
						int chainLength = 2;
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
						int chainLength = 2;
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
