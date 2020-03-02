

import java.util.List;

public interface Wolf {
	public abstract int[] moveAll(int id, List<int[]> wolvesSight, List<int[]> preysSight);
	// returns an array with 2 elements in {-1,0,1} where the 
	// first integer indicates the ROW movement, and the second the COL movement
	// Hence, diagonal movement is possible
}
