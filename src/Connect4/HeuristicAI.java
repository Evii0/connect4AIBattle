package Connect4;

import java.util.Random;

public class HeuristicAI implements Connect4AI {

	private int playerNumber;
	private int otherNumber;
	
	public HeuristicAI(int playerNumber) {
		this.playerNumber = playerNumber;
		if(playerNumber == 1)
			otherNumber = 2;
		else
			otherNumber=1;
	}

	@Override
	public int chooseMove(int[][] board) {
		int move = canWinOrLose(board);
		if(move != -1)
			return move;
		else {
			Random rand = new Random();
			int column = rand.nextInt(7);
	
			while (board[column][5] != 0)
				column = rand.nextInt(7);
	
			return column;
		}
	}
	
	private void printBoard(int[][] grid) {
		for(int y = 5; y >= 0; y--) {
			System.out.println("| " + grid[0][y] + " "+ grid[1][y] + " "+ grid[2][y] + " "+ grid[3][y] + " "+ grid[4][y] + " "+ grid[5][y] + " "+ grid[6][y] + " |");
		}
		System.out.println("");
	}
	
	public int canWinOrLose(int[][] board) {
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
		System.out.println("Something bust, flame the developer.");
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
	

	private int canWinLose(int[][] grid) {
		int winMove = -1;
		int notLoseMove = -1;
		// check verticals
		for (int x = 0; x < 7; x++) {
			int chainLength = 0;
			int player = grid[x][0];

			for (int y = 0; y < 5; y++) {
				if(player == 0)
					continue;
				if (grid[x][y] == player) {
					chainLength++;
				} else {
					chainLength = 1;
					player = grid[x][y];
				}

				if (chainLength == 3 && grid[x][y+1] == 0)
					if(player == playerNumber)winMove = x;
					else notLoseMove = x;
			}
		}

		// check horizontals
		for (int y = 0; y < 6; y++) {
			int chainLength = 0;
			int player = grid[0][y];

			for (int x = 0; x < 6; x++) {
				if(player == 0) {
					player = 1;
					chainLength = 1;
					continue;
				}
				if (grid[x][y] == player) {
					chainLength++;
				} else {
					chainLength = 1;
					player = grid[x][y];
				}
				
				
				if(chainLength == 2) {
					if(y == 0) {
						if(x < 5) {
							if(grid[x+1][y] == 0 && grid[x+2][y] == player)
								if(player == playerNumber)winMove = x+1;
								else notLoseMove = x+1;
						}
						if(x > 2) {
							if(grid[x-2][y] == 0 && grid[x-3][y] == player)
								if(player == playerNumber)winMove = x-2;
								else notLoseMove = x-2;
						}
					}
					else {
						if(x < 5) {
							if(grid[x+1][y] == 0 && grid[x+2][y] == player && grid[x+1][y-1] != 0)
								if(player == playerNumber)winMove = x+1;
								else notLoseMove = x+1;
						}
						if(x > 2) {
							if(grid[x-2][y] == 0 && grid[x-3][y] == player && grid[x-2][y-1] != 0)
								if(player == playerNumber)winMove = x-2;
								else notLoseMove = x-2;
						}
					}
				}
				
				if(chainLength == 3){
					if(x > 2) {
						if(grid[x-3][y] == 0 && y == 0)
							if(player == playerNumber)winMove = x-3;
							else notLoseMove = x-3;
						else if(grid[x-3][y] == 0 && grid[x-3][y-1] != 0)
							if(player == playerNumber)winMove = x-3;
							else notLoseMove = x-3;
					}
					if(x < 5) {
						if(grid[x+1][y] == 0 && y == 0)
							if(player == playerNumber)winMove = x+1;
							else notLoseMove = x+1;
					
						else if (grid[x+1][y] == 0 && grid[x][y-1] != 0)
							if(player == playerNumber)winMove = x+1;
							else notLoseMove = x+1;
					}
				}
			}
		}

		// check diagonals
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 3; y++) {
				if (grid[x][y] == 0)
					continue;
				int player = grid[x][y];

				if (x < 4) {
					if (player == grid[x + 1][y + 1]) {
						int tempX = x + 1;
						int tempY = y + 1;
						int chainLength = 2;
						while (player == grid[tempX][tempY]) {
							if(tempX == 6 || tempY == 5) {
								break;
							}
							if(chainLength == 2) {
								 if(tempX < 5 && tempY < 4 && 
									grid[tempX+2][tempY+2] == player && 
									grid[tempX+1][tempY+1] == 0 &&
									grid[tempX+1][tempY] != 0) {
										if(player == playerNumber)winMove = tempX+1;
										else notLoseMove = tempX+1;
								 }
								 if(tempX > 2 && tempY > 2 && 
										 grid[tempX-3][tempY-3] == player && 
										 grid[tempX-2][tempY-2] == 0) {
									 if(tempY > 3) {
										 if (grid[tempX-2][tempY-3] != 0)
											 if(player == playerNumber)winMove = tempX-2;
											 else notLoseMove = tempX-2;
									 }
									 if(player == playerNumber)winMove = tempX+1;
									 else notLoseMove = tempX+1;
								 }
								
							}
									
							if (chainLength == 3 && grid[tempX+1][tempY+1] == 0 && grid[tempX+1][tempY] != 0)
								if(player == playerNumber)winMove = tempX+1;
								else notLoseMove = tempX+1;
							chainLength++;
							tempX++;
							tempY++;
						}
					}
				}

				if (x > 2) {
					if (player == grid[x - 1][y + 1]) {
						int tempX = x - 1;
						int tempY = y + 1;
						int chainLength = 2;
						while (player == grid[tempX][tempY]) {
							if(tempX == 0 || tempY == 5)break;
							
							if(chainLength == 2) {
								 if(tempX < 1 && tempY < 4 && 
									grid[tempX-2][tempY+2] == player && 
									grid[tempX-1][tempY+1] == 0 &&
									grid[tempX-1][tempY] != 0) {
										if(player == playerNumber)winMove = tempX-1;
										else notLoseMove = tempX-1;
								 }
								 if(tempX > 5 && tempY > 1 && 
										 grid[tempX+3][tempY-3] == player && 
										 grid[tempX+2][tempY-2] == 0) {
									 if(tempY > 3) {
										 if(grid[tempX+2][tempY-3] != 0) {
											 if(player == playerNumber)winMove = tempX+2;
											 else notLoseMove = tempX+2;
										 }
									 }
									 if(player == playerNumber)winMove = tempX+1;
									 else notLoseMove = tempX+1;
								 }
								
							}
							
							if (chainLength == 3) {
								
									if(grid[tempX-1][tempY+1] == 0 && grid[tempX-1][tempY] != 0) {
										if(player == playerNumber)winMove = tempX-1;
										else notLoseMove = tempX-1;
									}
									if(tempX > 2 && tempY > 2) {
										if(grid[tempX-3][tempY-3] == 0 && grid[tempX-1][tempY] != 0) {
											if(player == playerNumber)winMove = tempX-1;
											else notLoseMove = tempX-1;
										}
									}
								
							}
								
							chainLength++;
							tempX--;
							tempY++;
						}
					}
				}
			}
		}
		
		if(winMove != -1)return winMove;
		else return notLoseMove;
	}
}