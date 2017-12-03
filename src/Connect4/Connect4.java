package Connect4;

public class Connect4 {
	int[][] grid = new int[7][6];
	int currentPlayer = 1;
	
	public Connect4() {
		grid[0][0] = 1;
//		grid[0][1] = 1;
//		grid[0][2] = 1;
//		grid[0][4] = 1;
		
		System.out.println(isGameOver());
	}
	
	private boolean makeMove(int col, int player) {
		for(int i = 0; i < 6; i++) {
			if(i == 5 && grid[i][col] != 0) {
				System.out.println("Invalid move, there is no space in that column");
				return false;
			}
			if(grid[i][col] == 0) {
				grid[i][col] = player;
				isGameOver();
				return true;
			}
		}
		System.out.println("Something bust, flame the developer.");
		return false;
	}
	
	private boolean isGameOver() {
		//check verticals
		for(int x = 0; x < 7; x++) {
			int chainLength = 0;
			int player = grid[x][0];
			
			for(int y = 0; y < 6; y++) {
				System.out.println(x + ", " + y + ": " + grid[x][y]);
				if(grid[x][y] == player) {
					chainLength++;
				}
				else {
					System.out.println("hi");
					chainLength = 1;
					player = grid[x][y];
				}
				if(chainLength == 4 && player != 0)return true;
			}
		}
		
		//check horizontals
//		for(int y = 0; y < 6; y++) {
//			int chainLength = 0;
//			int player = grid[0][y];
//			
//			for(int x = 0; x < 7; x++) {
//				if(grid[x][y] == player)chainLength++;
//				else {
//					chainLength = 1;
//					player = grid[x][y];
//				}
//				if(chainLength == 4)return true;
//			}
//		}
		
		return false;
	}
	
	public static void main(String[] args) {
		new Connect4();
	}
}
