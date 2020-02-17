package wolves;

import org.javatuples.Triplet;

import java.util.List;

public interface Wolf {

	/*
	public abstract int[] moveAll(int id, int[][]grid);
	// returns an array with 2 elements in {-1,0,1} where the 
	// first integer indicates the ROW movement, and the second the COL movement
	// Hence, diagonal movement is possible
	*/


	public abstract int moveLim(int i, List<Triplet<Integer,Integer,Integer>> sight);
	// returns 0 for No Movement, 1 for North, 2 for East, 3 for South, 4 for West
	// Hence, no diagonal movement is possible
}
