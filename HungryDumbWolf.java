/*
public class HungryDumbWolf implements Wolf {

	@Override
	public int[] moveAll(int id, int[][] grid) {
		int preyRow = findRow(4,grid);
		int preyCol = findCol(4,grid);
		int myRow = findRow(id,grid);
		int myCol = findCol(id,grid);
		int rm, cm;
		if (preyRow < myRow) rm = -1;
		else if (preyRow > myRow) rm = 1;
		else rm = 0;
		if (preyCol < myCol) cm = -1;
		else if (preyCol > myCol) cm = 1;
		else cm = 0;
		return new int[]{rm,cm};
	}

	@Override
	public int moveLim(int id, int[][] grid) {
		int preyRow = findRow(4,grid);
		int preyCol = findCol(4,grid);
		int myRow = findRow(id,grid);
		int myCol = findCol(id,grid);
		if (Math.abs(preyRow-myRow) > Math.abs(preyCol-myCol)) {
			if (preyRow < myRow) return 1;
			else return 3;
		} else {
			if (preyCol > myCol) return 2;
			else return 4;
		}
	}
	
	private int findRow(int val, int[][] grid) {
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j< grid[i].length; j++) 
				if (grid[i][j] == val) return i; 
		}
		return 0;
	}
	private int findCol(int val, int[][] grid) {
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j< grid[i].length; j++) 
				if (grid[i][j] == val) return j; 
		}
		return 0;
	}
}
*/