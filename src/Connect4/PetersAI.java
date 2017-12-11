package Connect4;

import java.util.ArrayList;
import java.util.Random;

public class PetersAI implements Connect4AI {

//	private int numLayers;
//	private int[] sizes = {2, 3, 1};
//	private double[] biases = new double[2];
//	private double[] weights = new double[3];
	
	public PetersAI() {
		
	}
	
//	private double specialRandom(double mean, double variance) {
//		Random r = new Random();
//		double randomValue = mean + r.nextGaussian()*variance;
//		return randomValue;
//	}

	@Override
	public int chooseMove(int[][] board) {
		Random rand = new Random();
		ArrayList<Integer> availableCols = new ArrayList<Integer>();
		
		for(int i = 0; i < 7; i++) {
			if(board[i][5] == 0)availableCols.add(i);
		}
		
		int column = rand.nextInt(availableCols.size());

		return availableCols.get(column);
	}



}