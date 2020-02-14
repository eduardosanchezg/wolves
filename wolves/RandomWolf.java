package wolves;

import java.util.Random;

public class RandomWolf implements Wolf {

	@Override
	public int[] moveAll(int id, int[][] grid) {
		Random r = new Random();
		int[] mymove = new int[2];
		mymove[0] = r.nextInt(3)-1;
		mymove[1] = r.nextInt(3)-1;
		return mymove;
	}
	
	public int moveLim(int id, int[][] grid) {
		Random r = new Random();
		return r.nextInt(4) + 1;
	}

}
