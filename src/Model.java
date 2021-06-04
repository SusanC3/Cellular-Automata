import java.awt.*;
import java.util.*;

public class Model {
	private int rows;
	private int columns;
	private int cellSize;
	private Cell grid[][];
	private Cell gridCopy[][]; //for updating independently
	private Input input;
	private ColorPalette palette;
	private boolean inSandboxMode;
	
	public int getRows() { return this.rows; }
	public int getColumns() { return this.columns; }
	public int getCellSize() { return this.cellSize; }
	public void setCellSize(int s) { this.cellSize = s; }
	public Color getCellColor(int i, int j) {
		return grid[i][j].getColor();
	}
	public Input getInput() { return this.input; }
	public ColorPalette getColorPalette() { return this.palette; }
	public boolean inSandboxMode() {return this.inSandboxMode; }
	public void setInSandboxMode(boolean mode) { this.inSandboxMode = mode; }
	
	private void backupGrid() { //make a copy of the grid
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				gridCopy[i][j] = grid[i][j].makeCopy();
			}
		}
	}
	
	public Model(int rows, int columns, int cellSize, Input input, ColorPalette palette) {
		this.rows = rows;
		this.columns = columns;
		this.cellSize = cellSize;
		this.grid = new Cell[rows][columns]; 
		this.gridCopy = new Cell[rows][columns];
		this.input = input;
		this.palette = palette;
		this.inSandboxMode = false;
	}
	
	public void setDimensions(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		grid = new Cell[rows][columns];
		gridCopy = new Cell[rows][columns];
	}
	
	public void clearGrid(int alive) { //make all cells alive or all dead
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Color c = (alive == 0) ? Color.black : Color.white;
				grid[i][j] = new Cell(j*cellSize, i*cellSize, c, alive, palette); 
			}
		}
		backupGrid();
	}
	
	public void initGrid() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				//if (inSandboxMode)  grid[i][j] = new Cell(j*cellSize, i*cellSize, Color.black, 0, palette);
				/*else*/ if (this.input.getBinary() == null) { //random generation
					int r = new Random().nextInt(2); //found this shorthand on stackoverflow
					Color c = (r == 0) ? Color.black : Color.white;
					grid[i][j] = new Cell(j*cellSize, i*cellSize, c, r, palette); 
				} else { //all cells start dead
					if(i == rows-2 && j == columns/2) grid[i][j] = new Cell(j*cellSize, i*cellSize, Color.white, 1, palette);
					else grid[i][j] = new Cell(j*cellSize, i*cellSize, Color.black, 0, palette);
				}	
			}
		}
		backupGrid();
	}
	
	public void toggleLivingStatus(int x, int y) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (x >= grid[i][j].getX() && x <= grid[i][j].getX()+cellSize 
					&& y >= grid[i][j].getY() && y <= grid[i][j].getY()+cellSize) {
					grid[i][j].setLivingStatus((grid[i][j]).isAlive() == 1 ? 0 : 1); 
				}
			}
		}
		backupGrid();
	}
	
	public void doConwayCycle(Input input) { 
		for (int i = 1; i < (rows-1)/2; i++) {
			for (int j = 1; j < columns-1; j++) {
				grid[i][j].conwayUpdate(gridCopy[i-1][j].isAlive(), gridCopy[i-1][j-1].isAlive(), gridCopy[i-1][j+1].isAlive(), 
										gridCopy[i][j-1].isAlive(), gridCopy[i][j+1].isAlive(),
										gridCopy[i+1][j].isAlive(), gridCopy[i+1][j-1].isAlive(), gridCopy[i+1][j+1].isAlive());				
				if (grid[i][j].isAlive() == 0) grid[i][j].incrementCyclesDead();
			}
		}
		if (this.input.getBinary() == null) {
			for (int i = (rows-1)/2; i < rows-1; i++) {
				for (int j = 1; j < columns-1; j++) { //the white border is totally aesthetic and on purpose
					grid[i][j].conwayUpdate(gridCopy[i-1][j].isAlive(), gridCopy[i-1][j-1].isAlive(), gridCopy[i-1][j+1].isAlive(), 
											gridCopy[i][j-1].isAlive(), gridCopy[i][j+1].isAlive(),
											gridCopy[i+1][j].isAlive(), gridCopy[i+1][j-1].isAlive(), gridCopy[i+1][j+1].isAlive());				
					if (grid[i][j].isAlive() == 0) grid[i][j].incrementCyclesDead();
				}
			}
		} else {
			input.update(grid);
		}
		
		backupGrid();
	}
	
	public void setupFastGrowExample() {
		inSandboxMode = true;
		input.setBinary(null);
		cellSize = 5;
		clearGrid(0);
		
		grid[67][80].setLivingStatus(1);
		grid[65][80].setLivingStatus(1);
		grid[64][79].setLivingStatus(1);
		grid[64][78].setLivingStatus(1);
		grid[64][77].setLivingStatus(1);
		grid[64][76].setLivingStatus(1);
		grid[65][76].setLivingStatus(1);
		grid[66][76].setLivingStatus(1);
		grid[67][77].setLivingStatus(1);
		grid[71][80].setLivingStatus(1);
		grid[73][80].setLivingStatus(1);
		grid[74][79].setLivingStatus(1);
		grid[74][78].setLivingStatus(1);
		grid[74][77].setLivingStatus(1);
		grid[74][76].setLivingStatus(1);
		grid[73][76].setLivingStatus(1);
		grid[72][76].setLivingStatus(1);
		grid[71][77].setLivingStatus(1);
		grid[71][87].setLivingStatus(1);
		grid[70][88].setLivingStatus(1);
		grid[69][88].setLivingStatus(1);
		grid[68][88].setLivingStatus(1);
		grid[67][87].setLivingStatus(1);
		grid[72][85].setLivingStatus(1);
		grid[71][84].setLivingStatus(1);
		grid[71][83].setLivingStatus(1);
		grid[70][82].setLivingStatus(1);
		grid[69][82].setLivingStatus(1);
		grid[68][82].setLivingStatus(1);
		grid[67][83].setLivingStatus(1);
		grid[67][84].setLivingStatus(1);
		grid[66][85].setLivingStatus(1);
		grid[63][95].setLivingStatus(1);
		grid[62][96].setLivingStatus(1);
		grid[62][97].setLivingStatus(1);
		grid[62][98].setLivingStatus(1);
		grid[63][98].setLivingStatus(1);
		grid[64][98].setLivingStatus(1);
		grid[65][98].setLivingStatus(1);
		grid[66][98].setLivingStatus(1);
		grid[63][105].setLivingStatus(1);
		grid[62][104].setLivingStatus(1);
		grid[62][103].setLivingStatus(1);
		grid[62][102].setLivingStatus(1);
		grid[63][102].setLivingStatus(1);
		grid[64][102].setLivingStatus(1);
		grid[65][102].setLivingStatus(1);
		grid[66][102].setLivingStatus(1);
		grid[68][96].setLivingStatus(1);
		grid[68][97].setLivingStatus(1);
		grid[68][98].setLivingStatus(1);
		grid[69][97].setLivingStatus(1);
		grid[70][97].setLivingStatus(1);
		grid[70][98].setLivingStatus(1);
		grid[70][99].setLivingStatus(1);
		grid[70][100].setLivingStatus(1);
		grid[70][101].setLivingStatus(1);
		grid[70][102].setLivingStatus(1);
		grid[70][103].setLivingStatus(1);
		grid[69][103].setLivingStatus(1);
		grid[68][102].setLivingStatus(1);
		grid[68][103].setLivingStatus(1);
		grid[68][104].setLivingStatus(1);
		grid[72][95].setLivingStatus(1);
		grid[71][96].setLivingStatus(1);
		grid[72][96].setLivingStatus(1);
		grid[72][97].setLivingStatus(1);
		grid[72][98].setLivingStatus(1);
		grid[72][99].setLivingStatus(1);
		grid[72][100].setLivingStatus(1);
		grid[72][101].setLivingStatus(1);
		grid[72][102].setLivingStatus(1);
		grid[72][103].setLivingStatus(1);
		grid[71][104].setLivingStatus(1);
		grid[72][104].setLivingStatus(1);
		grid[72][105].setLivingStatus(1);
		grid[71][91].setLivingStatus(1);
		grid[71][90].setLivingStatus(1);
		grid[72][89].setLivingStatus(1);
		grid[72][90].setLivingStatus(1);
		grid[73][90].setLivingStatus(1);
		grid[73][91].setLivingStatus(1);
		grid[74][91].setLivingStatus(1);
		grid[75][92].setLivingStatus(1);
		grid[74][92].setLivingStatus(1);
		grid[74][93].setLivingStatus(1);
		grid[75][94].setLivingStatus(1);
		grid[74][94].setLivingStatus(1);
		grid[74][95].setLivingStatus(1);
		grid[74][96].setLivingStatus(1);
		grid[74][97].setLivingStatus(1);
		grid[74][98].setLivingStatus(1);
		grid[74][99].setLivingStatus(1);
		grid[74][100].setLivingStatus(1);
		grid[74][101].setLivingStatus(1);
		grid[74][102].setLivingStatus(1);
		grid[74][103].setLivingStatus(1);
		grid[74][104].setLivingStatus(1);
		grid[74][105].setLivingStatus(1);
		grid[74][106].setLivingStatus(1);
		grid[75][106].setLivingStatus(1);
		grid[74][107].setLivingStatus(1);
		grid[74][108].setLivingStatus(1);
		grid[75][108].setLivingStatus(1);
		grid[74][109].setLivingStatus(1);
		grid[73][109].setLivingStatus(1);
		grid[73][110].setLivingStatus(1);
		grid[72][110].setLivingStatus(1);
		grid[72][111].setLivingStatus(1);
		grid[71][110].setLivingStatus(1);
		grid[71][109].setLivingStatus(1);
		grid[77][95].setLivingStatus(1);
		grid[76][95].setLivingStatus(1);
		grid[76][96].setLivingStatus(1);
		grid[76][97].setLivingStatus(1);
		grid[76][98].setLivingStatus(1);
		grid[76][99].setLivingStatus(1);
		grid[76][100].setLivingStatus(1);
		grid[76][101].setLivingStatus(1);
		grid[76][102].setLivingStatus(1);
		grid[76][103].setLivingStatus(1);
		grid[76][104].setLivingStatus(1);
		grid[76][105].setLivingStatus(1);
		grid[77][105].setLivingStatus(1);
		grid[78][96].setLivingStatus(1);
		grid[78][97].setLivingStatus(1);
		grid[78][98].setLivingStatus(1);
		grid[78][99].setLivingStatus(1);
		grid[78][100].setLivingStatus(1);
		grid[79][100].setLivingStatus(1);
		grid[78][101].setLivingStatus(1);
		grid[78][102].setLivingStatus(1);
		grid[78][103].setLivingStatus(1);
		grid[78][104].setLivingStatus(1);
		grid[80][96].setLivingStatus(1);
		grid[80][97].setLivingStatus(1);
		grid[80][98].setLivingStatus(1);
		grid[81][98].setLivingStatus(1);
		grid[80][102].setLivingStatus(1);
		grid[81][102].setLivingStatus(1);
		grid[80][103].setLivingStatus(1);
		grid[80][104].setLivingStatus(1);
		grid[83][99].setLivingStatus(1);
		grid[83][98].setLivingStatus(1);
		grid[83][97].setLivingStatus(1);
		grid[84][99].setLivingStatus(1);
		grid[84][98].setLivingStatus(1);
		grid[84][97].setLivingStatus(1);
		grid[85][99].setLivingStatus(1);
		grid[85][98].setLivingStatus(1);
		grid[85][96].setLivingStatus(1);
		grid[86][98].setLivingStatus(1);
		grid[86][97].setLivingStatus(1);
		grid[86][96].setLivingStatus(1);
		grid[87][97].setLivingStatus(1);
		grid[83][101].setLivingStatus(1);
		grid[83][102].setLivingStatus(1);
		grid[83][103].setLivingStatus(1);
		grid[84][101].setLivingStatus(1);
		grid[84][102].setLivingStatus(1);
		grid[84][103].setLivingStatus(1);
		grid[85][101].setLivingStatus(1);
		grid[85][102].setLivingStatus(1);
		grid[85][104].setLivingStatus(1);
		grid[86][102].setLivingStatus(1);
		grid[86][103].setLivingStatus(1);
		grid[86][104].setLivingStatus(1);
		grid[87][103].setLivingStatus(1);
		grid[71][113].setLivingStatus(1);
		grid[70][112].setLivingStatus(1);
		grid[69][112].setLivingStatus(1);
		grid[68][112].setLivingStatus(1);
		grid[67][113].setLivingStatus(1);
		grid[72][115].setLivingStatus(1);
		grid[71][116].setLivingStatus(1);
		grid[71][117].setLivingStatus(1);
		grid[70][118].setLivingStatus(1);
		grid[69][118].setLivingStatus(1);
		grid[68][118].setLivingStatus(1);
		grid[67][117].setLivingStatus(1);
		grid[67][116].setLivingStatus(1);
		grid[66][115].setLivingStatus(1);
		grid[67][120].setLivingStatus(1);
		grid[65][120].setLivingStatus(1);
		grid[64][121].setLivingStatus(1);
		grid[64][122].setLivingStatus(1);
		grid[64][123].setLivingStatus(1);
		grid[64][124].setLivingStatus(1);
		grid[65][124].setLivingStatus(1);
		grid[66][124].setLivingStatus(1);
		grid[67][123].setLivingStatus(1);
		grid[71][120].setLivingStatus(1);
		grid[73][120].setLivingStatus(1);
		grid[74][121].setLivingStatus(1);
		grid[74][122].setLivingStatus(1);
		grid[74][123].setLivingStatus(1);
		grid[74][124].setLivingStatus(1);
		grid[73][124].setLivingStatus(1);
		grid[72][124].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		grid[71][123].setLivingStatus(1);
		
		backupGrid();
	}
	
	
	public void setupGliderExample() {
		inSandboxMode = true;
		input.setBinary(null);;
		clearGrid(0);
		
		grid[10][0].setLivingStatus(1);
		grid[10][1].setLivingStatus(1);
		grid[11][0].setLivingStatus(1);
		grid[11][1].setLivingStatus(1);
		
		grid[9][12].setLivingStatus(1);
		grid[10][12].setLivingStatus(1);
		grid[11][12].setLivingStatus(1);
		grid[12][12].setLivingStatus(1);
		grid[13][12].setLivingStatus(1);
		grid[8][13].setLivingStatus(1);
		grid[10][13].setLivingStatus(1);
		grid[11][13].setLivingStatus(1);
		grid[12][13].setLivingStatus(1);
		grid[14][13].setLivingStatus(1);			
		grid[9][14].setLivingStatus(1);
		grid[13][14].setLivingStatus(1);	
		grid[10][15].setLivingStatus(1);
		grid[11][15].setLivingStatus(1);
		grid[12][15].setLivingStatus(1);		
		grid[11][16].setLivingStatus(1);
		
		grid[9][20].setLivingStatus(1);
		grid[9][21].setLivingStatus(1);
		grid[8][22].setLivingStatus(1);
		grid[10][22].setLivingStatus(1);
		grid[8][23].setLivingStatus(1);
		grid[10][23].setLivingStatus(1);
		grid[7][23].setLivingStatus(1);
		grid[11][23].setLivingStatus(1);
		grid[6][24].setLivingStatus(1);
		grid[12][24].setLivingStatus(1);
		grid[9][25].setLivingStatus(1);
		grid[6][26].setLivingStatus(1);
		grid[7][26].setLivingStatus(1);
		grid[11][26].setLivingStatus(1);
		grid[12][26].setLivingStatus(1);
		
		grid[10][29].setLivingStatus(1);
		grid[10][30].setLivingStatus(1);
		grid[11][31].setLivingStatus(1);
		
		grid[8][34].setLivingStatus(1);
		grid[9][34].setLivingStatus(1);
		grid[8][35].setLivingStatus(1);
		grid[9][35].setLivingStatus(1);
		
		backupGrid();
	}


}
