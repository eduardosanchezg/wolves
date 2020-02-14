package wolves;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;


public class Wolves {

	private int[][] grid;
	private int rows, cols;
	private int[] wolfRow = new int[3];
	private int[] wolfCol = new int[3];
	private int preyRow, preyCol;
	private Wolf[] wolves = new Wolf[3];
	private Random r = new Random();
	private WolvesUI visuals;
	private long tickcounter = 0;

	public Wolves(int rows, int cols) {
		this.rows=rows;
		this.cols=cols;
		grid = new int[rows][cols];

		for (int i=0; i<3; i++) {
			do {
				wolfRow[i] = r.nextInt(rows);
				wolfCol[i] = r.nextInt(cols);
			} while (!empty(wolfRow[i],wolfCol[i]));
			grid[wolfRow[i]][wolfCol[i]] = i+1;
		}
		do {
			preyRow = r.nextInt(rows);
			preyCol = r.nextInt(cols);
		} while (!empty(preyRow,preyCol) 
				|| captured(preyRow,preyCol));
		grid[preyRow][preyCol] = 4;
		initWolves();
	}
	
	private boolean empty(int row, int col) {
		return (grid[row][col] == 0);
	}

	private void initWolves() {
		// You should put your own wolves in the array here!!
		Wolf[] wolvesPool = new Wolf[5];
		wolvesPool[0] = new RandomWolf();
		wolvesPool[1] = new RandomWolf();
		wolvesPool[2] = new RandomWolf();
		wolvesPool[3] = new RandomWolf();
		wolvesPool[4] = new RandomWolf();
		// Below code will select three random wolves from the pool. 
		// Make the pool as large as you want, but not < 3
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < 3)
		{
		    Integer next = r.nextInt(wolvesPool.length);
		    generated.add(next);
		}
		int i = 0;
		for (Integer index : generated) {
		    wolves[i++] = wolvesPool[index];
		}
	}
	
	public void tick() {
		int[][] moves = new int[3][2];
		int rowMove, colMove;
		int cntr = 0;
		do {
			rowMove = r.nextInt(3)-1;
			colMove = r.nextInt(3)-1;
			cntr++;
		} while (!empty(rowWrap(preyRow,rowMove),colWrap(preyCol,colMove))
				|| (cntr<100 && captured(rowWrap(preyRow,rowMove),colWrap(preyCol,colMove))));
		grid[preyRow][preyCol] = 0;
		preyRow = rowWrap(preyRow,rowMove);
		preyCol = colWrap(preyCol,colMove);
		grid[preyRow][preyCol] = 4;
		int[][] safetyGrid;
		
		// You can alter the allowances of the Wolves by commenting or un-commenting the code pieces below.
		
		// Wolves can move diagonally
		for (int i = 0; i<3; i++) {
			safetyGrid = new int[grid.length][grid[0].length];
			for (int r=0; r<grid.length; r++)
				for (int s=0; s<grid[0].length; s++)
					safetyGrid[r][s] = grid[r][s];
			moves[i] = wolves[i].moveAll(i+1,safetyGrid);
		}
				
		// Wolves can not move diagonally
		/*for (int i = 0; i<3; i++) {
			safetyGrid = new int[grid.length][grid[0].length];
			for (int r=0; r<grid.length; r++)
				for (int s=0; s<grid[0].length; s++)
					safetyGrid[r][s] = grid[r][s];
			int dir = wolves[i].moveLim(i+1,grid);
			switch (dir) {
			 	case 0: moves[i][0] = 0; moves[i][1] = 0; break; 
			 	case 1: moves[i][0] = -1; moves[i][1] = 0; break; 
			 	case 2: moves[i][0] = 0; moves[i][1] = 1; break; 
			 	case 3: moves[i][0] = 1; moves[i][1] = 0; break;
				case 4: moves[i][0] = 0; moves[i][1] = -1; break; 
			}
		}*/
		
		// Till here!!!
		
		for (int i = 0; i<3; i++) {
			if (empty(rowWrap(wolfRow[i],moves[i][0]),colWrap(wolfCol[i],moves[i][1]))) {
				grid[wolfRow[i]][wolfCol[i]] = 0;
				wolfRow[i] = rowWrap(wolfRow[i],moves[i][0]);
				wolfCol[i] = colWrap(wolfCol[i],moves[i][1]);
				grid[wolfRow[i]][wolfCol[i]] = i+1;
			}
		}
		
		visuals.update();
		tickcounter++;
		if (captured(preyRow,preyCol)) {
			JOptionPane.showMessageDialog(null, "Wolves won in " + tickcounter + " steps!!");
			System.out.println("Winners");
			System.exit(0);
		}
	}
	

	public boolean captured(int r, int c) {
		int count = 0;
		if (grid[rowWrap(r,-1)][colWrap(c,-1)]!=0) count++;
		if (grid[rowWrap(r,-1)][colWrap(c,0)]!=0) count++;
		if (grid[rowWrap(r,-1)][colWrap(c,1)]!=0) count++;
		if (grid[rowWrap(r,0)][colWrap(c,-1)]!=0) count++;
		if (grid[rowWrap(r,0)][colWrap(c,1)]!=0) count++;
		if (grid[rowWrap(r,1)][colWrap(c,-1)]!=0) count++;
		if (grid[rowWrap(r,1)][colWrap(c,0)]!=0) count++;
		if (grid[rowWrap(r,1)][colWrap(c,1)]!=0) count++;	
		return (count>=2);
	}
	
	public int rowWrap(int x, int inc) {
		int tmp = x+inc;
		if (tmp<0) tmp+=rows;
		if (tmp>=rows) tmp-=rows;
		return tmp;
	}
	public int colWrap(int x, int inc) {
		int tmp = x+inc;
		if (tmp<0) tmp+=cols;
		if (tmp>=cols) tmp-=cols;
		return tmp;
	}

	public int getNumbCols() {
		return cols;
	}

	public int getNumbRows() {
		return rows;
	}

	public boolean isWolf(int i, int j) {
		return (0<grid[i][j]) && (grid[i][j]<4);
	}

	public boolean isPrey(int i, int j) {
		return (grid[i][j] == 4);
	}

	public void attach(WolvesUI wolvesUI) {
		visuals = wolvesUI;
	}

}
