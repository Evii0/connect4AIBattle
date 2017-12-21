package Connect4;

public class Board{
	
	private int[][] board;
	private int originalMove;
	private int nextPlayer;
	
	public Board(int[][] board) {
		this.board = board;
	}
	
	/*
	 * takes the move made to create this board and the player that made it
	 */
	public Board(int[][] board, int player, int originalMove) {
		this.board = board;
		this.originalMove = originalMove;
		if(player == 1)
			nextPlayer = 2;
		else 
			nextPlayer = 1;
	}
	
	public boolean makeMove(int col, int player) {
		for (int i = 0; i < 6; i++) {
			if (i == 5 && board[col][i] != 0) {
				System.out.println("Invalid move, there is no space in that column");
				return false;
			}
			if (board[col][i] == 0) {
				board[col][i] = player;
				return true;
			}
		}
		System.out.println("Something bust, flame the developer.");
		return false;
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public int getNextPlayer() {
		return nextPlayer;
	}
	
	public int getOriginalMove() {
		return originalMove;
	}
}