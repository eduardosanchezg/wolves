

import java.util.*;

import javax.swing.JOptionPane;

public class Wolves {

	private int numWolves;
	private int numPreys;
	private int visibility;
	private int minCaptured;
	private int min_surround;
	public int[][] grid;
	private int rows, cols;
	private int[] wolfRow = new int[numWolves];
	private int[] wolfCol = new int[numWolves];
	private int[] preyRow = new int[numPreys];
	private int[] preyCol = new int[numPreys];
	private Wolf[] wolves = new Wolf[numWolves];
	private int[] lastMove = new int[numWolves];
	private List<Integer> capturedList = new ArrayList<>();
	private Random r = new Random();
	private WolvesUI visuals;
	private long tickcounter = 0;

	public Wolves(int rows, int cols, int numWolves, int numPreys, int visibility, int minCaptured, int min_surround) {
		this.rows=rows;
		this.cols=cols;
		this.numWolves = numWolves;
		this.numPreys = numPreys;
		this.visibility = visibility;
		this.minCaptured = minCaptured;
		this.min_surround = min_surround;
		grid = new int[rows][cols];

		wolfRow = new int[numWolves];
		wolfCol = new int[numWolves];
		preyRow = new int[numPreys];
		preyCol = new int[numPreys];
		wolves = new Wolf[numWolves];
		lastMove = new int[numWolves];

		for (int i=0; i< numWolves; i++) {
			do {
				wolfRow[i] = r.nextInt(rows);
				wolfCol[i] = r.nextInt(cols);
			} while (!empty(wolfRow[i],wolfCol[i]));
			grid[wolfRow[i]][wolfCol[i]] = i*2+1;
		}
		for (int i = 0; i < numPreys; i++) {

			int preyR;
			int preyC;
			do {
				preyR = r.nextInt(rows);
				preyC = r.nextInt(cols);
			} while (!empty(preyR,preyC)
					|| captured(preyR,preyC));
			preyRow[i] = preyR;
			preyCol[i] = preyC;
			grid[preyR][preyC] = i*2+2;
		}
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
		// Make the pool as large as you want, but not < numWolves
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < numWolves)
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
		int[][] moves = new int[numWolves][2];

		int cntr = 0;
		for (int i = 0; i < numPreys; i++) {
			int rowMove, colMove;
			do {
				rowMove = r.nextInt(3) - 1;
				colMove = r.nextInt(3) - 1;
				cntr++;
			} while (!empty(rowWrap(preyRow[i], rowMove), colWrap(preyCol[i], colMove))
					|| (cntr < 100 && captured(rowWrap(preyRow[i], rowMove), colWrap(preyCol[i], colMove))));
			grid[preyRow[i]][preyCol[i]] = 0;
			preyRow[i] = rowWrap(preyRow[i], rowMove);
			preyCol[i] = colWrap(preyCol[i], colMove);
			grid[preyRow[i]][preyCol[i]] = i*2+2;

		}

		int[][] safetyGrid;

		// You can alter the allowances of the Wolves by commenting or un-commenting the code pieces below.
		
		// Wolves can move diagonally
		/*for (int i = 0; i<3; i++) {
			safetyGrid = new int[grid.length][grid[0].length];
			for (int r=0; r<grid.length; r++)
				for (int s=0; s<grid[0].length; s++)
					safetyGrid[r][s] = grid[r][s];
			moves[i] = wolves[i].moveAll(i+1,safetyGrid);
		}
		*/
		// Wolves can not move diagonally
		for (int i = 0; i< numWolves; i++) {
			safetyGrid = new int[grid.length][grid[0].length];
			for (int r=0; r<grid.length; r++)
				for (int s=0; s<grid[0].length; s++)
					safetyGrid[r][s] = grid[r][s];

			int dir = wolves[i].moveLim(i+1,getWolfView(i));

			if (dir != 0) {
				dir = (dir-1 + lastMove[i] - 1) % 4 + 1; //transform from relative to absolute directions}
				lastMove[i] = dir;
			}
			switch (dir) {
			 	case 0: moves[i][0] = 0; moves[i][1] = 0; break; 
			 	case 1: moves[i][0] = -1; moves[i][1] = 0; break; 
			 	case 2: moves[i][0] = 0; moves[i][1] = 1; break; 
			 	case 3: moves[i][0] = 1; moves[i][1] = 0; break;
				case 4: moves[i][0] = 0; moves[i][1] = -1; break; 
			}
		}
		
		// Till here!!!
		
		for (int i = 0; i<numWolves; i++) {
			if (empty(rowWrap(wolfRow[i],moves[i][0]),colWrap(wolfCol[i],moves[i][1]))) {
				grid[wolfRow[i]][wolfCol[i]] = 0;
				wolfRow[i] = rowWrap(wolfRow[i],moves[i][0]);
				wolfCol[i] = colWrap(wolfCol[i],moves[i][1]);
				grid[wolfRow[i]][wolfCol[i]] = i*2+1;
			}
		}
		
		visuals.update();
		tickcounter++;

		for (int i = 0; i < numPreys; i++) { //add new captured to list
			if (capturedList.contains(i))
				continue;
			if (captured(preyRow[i],preyCol[i]))
				capturedList.add(i);
		}

		//check whether enough preys have been captured
		if (capturedList.size() >= minCaptured) {
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
		return (count>=min_surround);
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

	public boolean isWolf(int i, int j) { //Odd numbers are wolves
		return grid[i][j] % 2 == 1;
	}

	public boolean isPrey(int i, int j) { //Even numbers are sheeps
		return grid[i][j] != 0 & grid[i][j] % 2 == 0;
	}

	public void attach(WolvesUI wolvesUI) {
		visuals = wolvesUI;
	}

	public int ManhattanDistance(int x0, int y0, int x1, int y1) {
		return Math.abs(x1-x0) + Math.abs(y1-y0);
	}

	public List<int[]> getWolfView(int wolf) {
		List<int[]> agents = new ArrayList<>();
		for(int i=0;i<numWolves;i++){
			double angle = Math.toRadians(90*lastMove[wolf]);
			int cos = (int) Math.cos(angle);
			int sin = (int) Math.sin(angle);
			int relX = wolfRow[wolf]-wolfRow[i]; //relative looking at north
			int relY = wolfCol[wolf]-wolfCol[i];
			int x = (relX * cos) - (relY * sin); // transformed according to last move (orientation of the wolf)
			int y = (relX * sin) + (relY * cos);

			int[] agent = new int[]{i*2+1,x,y};
			agents.add(agent);
		}
		for (int i = 0; i < numPreys; i++) {
			if (ManhattanDistance(wolfRow[wolf],wolfCol[wolf],preyRow[i],preyCol[i]) > visibility)  {
				continue;
			}
			double angle = Math.toRadians(90*(lastMove[wolf]-1));
			int cos = (int) Math.cos(angle);
			int sin = (int) Math.sin(angle);
			int relX = wolfRow[wolf]-preyRow[i]; //relative looking at north
			int relY = wolfCol[wolf]-preyCol[i];
			int x = (relX * cos) - (relY * sin); // transformed according to last move (orientation of the wolf)
			int y = (relX * sin) + (relY * cos);
			int[] agent = new int[]{i*2+1,x,y};
			agents.add(agent);
		}

		return agents;
	}
}