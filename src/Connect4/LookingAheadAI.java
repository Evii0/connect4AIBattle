package Connect4;

import java.util.LinkedList;
import java.util.Random;

public class LookingAheadAI implements Connect4AI {

	private int playerNumber;
	private int otherNumber;
	
	public LookingAheadAI(int playerNumber) {
		this.playerNumber = playerNumber;
		if(playerNumber == 1)
			otherNumber = 2;
		else
			otherNumber=1;
	}
	
	@Override
	public int chooseMove(int[][] board) {
		double[] scores = evaluateAllMoves(board);
		
		int canWinOrLose = canWinOrLose(board);
		if(canWinOrLose != -1) {
			System.out.println("A player can win if they go here: " + canWinOrLose);
		}
		
		double largest = scores[0];
		int largestIndex = 0;
		double smallest = scores[0];
		int smallestIndex = 0;
		boolean allZero = true;
		for(int i = 0; i < 7; i++) {
			if(scores[i] != 0) allZero = false;
			if(scores[i] > largest) { largest = scores[i]; largestIndex = i; }
			if(scores[i] < smallest) { smallest = scores[i]; smallestIndex = i; }
		}
		System.out.println(scores[0] + ", " + scores[1] + ", " + scores[2] + ", " + scores[3] + ", " + scores[4] + ", " + scores[5] + ", " + scores[6]);
		
		if(allZero) {
			Random rand = new Random();
			int column = rand.nextInt(7);
	
			while (board[column][5] != 0)
				column = rand.nextInt(7);
	
			return column;
		}
		
		if(smallest*-1 > largest)return largestIndex;
		else return smallestIndex;
	}
	
	private int shouldMakeAMove(int[] scores) {
		for(int i = 0; i < 7; i++) {
			if(scores[i] > 0)return i;
		}
		return -1;
	}
	
	private double[] evaluateAllMoves(int[][] board) {
		double[] scores = new double[7];
		int currentPlayer = playerNumber;
		LinkedList<Board> queue = new LinkedList<Board>();
		
		//add first possible moves to the queue
		for(int i = 0; i < 7; i++) {
			if(board[i][5] == 0) {
				int[][] temp = createCopy(board);
				queue.add(new Board(makeMove(temp, i, currentPlayer), currentPlayer, i));
			}
		}
		
		int count = 0;
		while(!queue.isEmpty() && count < 200) {
			Board tempBoard = queue.poll();
			
			count++;
		}
		
		return scores;
	}
	
	private void printBoard(int[][] grid) {
		for(int y = 5; y >= 0; y--) {
			System.out.println("| " + grid[0][y] + " "+ grid[1][y] + " "+ grid[2][y] + " "+ grid[3][y] + " "+ grid[4][y] + " "+ grid[5][y] + " "+ grid[6][y] + " |");
		}
	}
	
	private double log(int i) {
		double temp = Math.ceil(Math.log(i) / Math.log(7));
		return temp;
	}
	
	private int canWinOrLose(int[][] board) {
		int canWin = -1;
		int canLose = -1;
		for(int i = 0; i < 7; i++) {
			if(board[i][5] == 0) {
				int[][] temp = createCopy(board);
				int[][] temp2 = createCopy(board);
				canWin = isGameOver(makeMove(temp, i, playerNumber));
				int tempCanLose = isGameOver(makeMove(temp2, i, otherNumber));
				if(canWin != -1) {
					return i;
				}
				if(tempCanLose != -1){
					canLose = i;
				}
			}
		}
		return canLose;
	}
	
	private int[][] createCopy(int[][]board){
		int[][] tempBoard = new int[7][6];
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 6; j++) {
				tempBoard[i][j] = board [i][j];
			}
		}
		return tempBoard;
	}
	
	private int[][] makeMove(int[][] grid, int col, int player) {
		for (int i = 0; i < 6; i++) {
			if (grid[col][i] == 0) {
				grid[col][i] = player;
				return grid;
			}
		}
		System.out.println("-----------------------------------\n\n\n\n\n\n\nSomething bust, flame the developer.\n\n\n\n\n\n\n\n------------------------------------");
		return null;
	}
	
	private int isGameOver(int[][] grid) {
		// check verticals
		for (int x = 0; x < 7; x++) {
			int chainLength = 0;
			int player = grid[x][0];

			for (int y = 0; y < 6; y++) {
				if (grid[x][y] != 0) {
					if (grid[x][y] == player)
						chainLength++;
					else {
						player = grid[x][y];
						chainLength = 1;
					}
				} else
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
		return -1;
	}
}