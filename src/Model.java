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
	private Rule rule;
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
	public Rule getRule() { return this.rule; }
	public boolean inSandboxMode() {return this.inSandboxMode; }
	public void setInSandboxMode(boolean mode) { this.inSandboxMode = mode; }
	
	private void backupGrid() { //make a copy of the grid
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				gridCopy[i][j] = grid[i][j].makeCopy();
			}
		}
	}
	
	public Model(int rows, int columns, int cellSize, Input input, ColorPalette palette, Rule rule) {
		this.rows = rows;
		this.columns = columns;
		this.cellSize = cellSize;
		this.grid = new Cell[rows][columns]; 
		this.gridCopy = new Cell[rows][columns];
		this.input = input;
		this.palette = palette;
		this.rule = rule;
		this.inSandboxMode = false;
	}
	
	public void reset()
	{
		setDimensions(150, 200);
		this.cellSize = 5;
		this.input.setBinary(null);;
	//	this.palette = new ColorPalette();
		this.rule.setConway();
		this.inSandboxMode = false;
		initGrid();
	}
	
	//will add upon request
	public void saveConfiguration() {
		/*make a new text file in an appropriate folder
		 * in the file:
		 	* Color Palette
		 	* dimensions
		 	* CA rule
		 	* random gen / input
		 	* generation mode / sandbox mode
		 	* if random gen, x and y of all cells starting alive
		 	* if rule, just do the rule
		 	* have an option to just use the current settings for each
		 * ask user what they want the file to be called and save it as that
		 * let people save things like color palette, CA rule, and input as individual things to be chosen from those menus
		 */
		
	}
	
	public void chooseConfiguration() {
		/*
		 * have JComboBox where the options are the file names
		 * chosing the file name will enact that starting config
		 */
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
				Color c = (alive == 0) ? palette.getColors()[palette.getColors().length-1] : palette.getColors()[0];
				grid[i][j] = new Cell(j*cellSize, i*cellSize, c, alive, palette, rule); 
			}
		}
		backupGrid();
	}
	
	public void initGrid() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (this.input.getBinary() == null) { //random generation
					int r = new Random().nextInt(2); 
					Color c = (r == 0) ? Color.black : Color.white;
					grid[i][j] = new Cell(j*cellSize, i*cellSize, c, r, palette, rule); 
				} else { //all cells start dead
					if(i == rows-2 && j == columns/2) grid[i][j] = new Cell(j*cellSize, i*cellSize, palette.getColors()[0], 1, palette, rule);
					else grid[i][j] = new Cell(j*cellSize, i*cellSize, palette.getColors()[palette.getColors().length-1], 0, palette, rule);
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
				grid[i][j].Update(gridCopy[i-1][j].isAlive(), gridCopy[i-1][j-1].isAlive(), gridCopy[i-1][j+1].isAlive(), 
										gridCopy[i][j-1].isAlive(), gridCopy[i][j+1].isAlive(),
										gridCopy[i+1][j].isAlive(), gridCopy[i+1][j-1].isAlive(), gridCopy[i+1][j+1].isAlive());				
				if (grid[i][j].isAlive() == 0) grid[i][j].incrementCyclesDead();
			}
		}
		if (this.input.getBinary() == null) {
			for (int i = (rows-1)/2; i < rows-1; i++) {
				for (int j = 1; j < columns-1; j++) { //the white border is totally aesthetic and on purpose
					grid[i][j].Update(gridCopy[i-1][j].isAlive(), gridCopy[i-1][j-1].isAlive(), gridCopy[i-1][j+1].isAlive(), 
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
	
	
	//unused, but I'll keep in in here in case people are interested
	public void setupThanksForWatching() {
		clearGrid(0);
		
		grid[56][91].setLivingStatus(1);
		grid[56][92].setLivingStatus(1);
		grid[56][93].setLivingStatus(1);
		grid[56][94].setLivingStatus(1);
		grid[56][95].setLivingStatus(1);
		grid[56][96].setLivingStatus(1);
		grid[56][97].setLivingStatus(1);
		grid[56][98].setLivingStatus(1);
		grid[56][99].setLivingStatus(1);
		grid[56][100].setLivingStatus(1);
		grid[56][104].setLivingStatus(1);
		grid[56][105].setLivingStatus(1);
		grid[56][137].setLivingStatus(1);
		grid[56][138].setLivingStatus(1);
		grid[56][163].setLivingStatus(1);
		grid[56][164].setLivingStatus(1);
		grid[56][165].setLivingStatus(1);
		grid[56][166].setLivingStatus(1);
		grid[56][167].setLivingStatus(1);
		grid[56][168].setLivingStatus(1);
		grid[56][169].setLivingStatus(1);
		grid[56][170].setLivingStatus(1);
		grid[56][171].setLivingStatus(1);
		grid[57][91].setLivingStatus(1);
		grid[57][92].setLivingStatus(1);
		grid[57][93].setLivingStatus(1);
		grid[57][94].setLivingStatus(1);
		grid[57][95].setLivingStatus(1);
		grid[57][96].setLivingStatus(1);
		grid[57][97].setLivingStatus(1);
		grid[57][98].setLivingStatus(1);
		grid[57][99].setLivingStatus(1);
		grid[57][100].setLivingStatus(1);
		grid[57][104].setLivingStatus(1);
		grid[57][105].setLivingStatus(1);
		grid[57][137].setLivingStatus(1);
		grid[57][138].setLivingStatus(1);
		grid[57][143].setLivingStatus(1);
		grid[57][163].setLivingStatus(1);
		grid[57][164].setLivingStatus(1);
		grid[57][165].setLivingStatus(1);
		grid[57][166].setLivingStatus(1);
		grid[57][167].setLivingStatus(1);
		grid[57][168].setLivingStatus(1);
		grid[57][169].setLivingStatus(1);
		grid[57][170].setLivingStatus(1);
		grid[57][171].setLivingStatus(1);
		grid[58][95].setLivingStatus(1);
		grid[58][96].setLivingStatus(1);
		grid[58][104].setLivingStatus(1);
		grid[58][105].setLivingStatus(1);
		grid[58][137].setLivingStatus(1);
		grid[58][138].setLivingStatus(1);
		grid[58][142].setLivingStatus(1);
		grid[58][143].setLivingStatus(1);
		grid[58][163].setLivingStatus(1);
		grid[58][164].setLivingStatus(1);
		grid[59][95].setLivingStatus(1);
		grid[59][96].setLivingStatus(1);
		grid[59][104].setLivingStatus(1);
		grid[59][105].setLivingStatus(1);
		grid[59][137].setLivingStatus(1);
		grid[59][138].setLivingStatus(1);
		grid[59][141].setLivingStatus(1);
		grid[59][142].setLivingStatus(1);
		grid[59][163].setLivingStatus(1);
		grid[59][164].setLivingStatus(1);
		grid[60][95].setLivingStatus(1);
		grid[60][96].setLivingStatus(1);
		grid[60][104].setLivingStatus(1);
		grid[60][105].setLivingStatus(1);
		grid[60][126].setLivingStatus(1);
		grid[60][127].setLivingStatus(1);
		grid[60][137].setLivingStatus(1);
		grid[60][138].setLivingStatus(1);
		grid[60][140].setLivingStatus(1);
		grid[60][141].setLivingStatus(1);
		grid[60][147].setLivingStatus(1);
		grid[60][148].setLivingStatus(1);
		grid[60][149].setLivingStatus(1);
		grid[60][150].setLivingStatus(1);
		grid[60][151].setLivingStatus(1);
		grid[60][152].setLivingStatus(1);
		grid[60][153].setLivingStatus(1);
		grid[60][154].setLivingStatus(1);
		grid[60][163].setLivingStatus(1);
		grid[60][164].setLivingStatus(1);
		grid[60][185].setLivingStatus(1);
		grid[60][186].setLivingStatus(1);
		grid[61][95].setLivingStatus(1);
		grid[61][96].setLivingStatus(1);
		grid[61][104].setLivingStatus(1);
		grid[61][105].setLivingStatus(1);
		grid[61][106].setLivingStatus(1);
		grid[61][107].setLivingStatus(1);
		grid[61][108].setLivingStatus(1);
		grid[61][109].setLivingStatus(1);
		grid[61][110].setLivingStatus(1);
		grid[61][114].setLivingStatus(1);
		grid[61][115].setLivingStatus(1);
		grid[61][116].setLivingStatus(1);
		grid[61][117].setLivingStatus(1);
		grid[61][118].setLivingStatus(1);
		grid[61][119].setLivingStatus(1);
		grid[61][120].setLivingStatus(1);
		grid[61][121].setLivingStatus(1);
		grid[61][126].setLivingStatus(1);
		grid[61][127].setLivingStatus(1);
		grid[61][128].setLivingStatus(1);
		grid[61][129].setLivingStatus(1);
		grid[61][130].setLivingStatus(1);
		grid[61][131].setLivingStatus(1);
		grid[61][132].setLivingStatus(1);
		grid[61][133].setLivingStatus(1);
		grid[61][137].setLivingStatus(1);
		grid[61][138].setLivingStatus(1);
		grid[61][139].setLivingStatus(1);
		grid[61][140].setLivingStatus(1);
		grid[61][147].setLivingStatus(1);
		grid[61][148].setLivingStatus(1);
		grid[61][149].setLivingStatus(1);
		grid[61][150].setLivingStatus(1);
		grid[61][151].setLivingStatus(1);
		grid[61][152].setLivingStatus(1);
		grid[61][153].setLivingStatus(1);
		grid[61][154].setLivingStatus(1);
		grid[61][163].setLivingStatus(1);
		grid[61][164].setLivingStatus(1);
		grid[61][165].setLivingStatus(1);
		grid[61][166].setLivingStatus(1);
		grid[61][167].setLivingStatus(1);
		grid[61][168].setLivingStatus(1);
		grid[61][169].setLivingStatus(1);
		grid[61][173].setLivingStatus(1);
		grid[61][174].setLivingStatus(1);
		grid[61][175].setLivingStatus(1);
		grid[61][176].setLivingStatus(1);
		grid[61][177].setLivingStatus(1);
		grid[61][178].setLivingStatus(1);
		grid[61][179].setLivingStatus(1);
		grid[61][180].setLivingStatus(1);
		grid[61][181].setLivingStatus(1);
		grid[61][185].setLivingStatus(1);
		grid[61][186].setLivingStatus(1);
		grid[61][187].setLivingStatus(1);
		grid[61][188].setLivingStatus(1);
		grid[61][189].setLivingStatus(1);
		grid[61][190].setLivingStatus(1);
		grid[61][191].setLivingStatus(1);
		grid[61][192].setLivingStatus(1);
		grid[62][95].setLivingStatus(1);
		grid[62][96].setLivingStatus(1);
		grid[62][104].setLivingStatus(1);
		grid[62][105].setLivingStatus(1);
		grid[62][106].setLivingStatus(1);
		grid[62][107].setLivingStatus(1);
		grid[62][108].setLivingStatus(1);
		grid[62][109].setLivingStatus(1);
		grid[62][110].setLivingStatus(1);
		grid[62][114].setLivingStatus(1);
		grid[62][115].setLivingStatus(1);
		grid[62][116].setLivingStatus(1);
		grid[62][117].setLivingStatus(1);
		grid[62][118].setLivingStatus(1);
		grid[62][119].setLivingStatus(1);
		grid[62][120].setLivingStatus(1);
		grid[62][121].setLivingStatus(1);
		grid[62][126].setLivingStatus(1);
		grid[62][127].setLivingStatus(1);
		grid[62][128].setLivingStatus(1);
		grid[62][129].setLivingStatus(1);
		grid[62][130].setLivingStatus(1);
		grid[62][131].setLivingStatus(1);
		grid[62][132].setLivingStatus(1);
		grid[62][133].setLivingStatus(1);
		grid[62][137].setLivingStatus(1);
		grid[62][138].setLivingStatus(1);
		grid[62][139].setLivingStatus(1);
		grid[62][147].setLivingStatus(1);
		grid[62][163].setLivingStatus(1);
		grid[62][164].setLivingStatus(1);
		grid[62][165].setLivingStatus(1);
		grid[62][166].setLivingStatus(1);
		grid[62][167].setLivingStatus(1);
		grid[62][168].setLivingStatus(1);
		grid[62][169].setLivingStatus(1);
		grid[62][173].setLivingStatus(1);
		grid[62][174].setLivingStatus(1);
		grid[62][175].setLivingStatus(1);
		grid[62][176].setLivingStatus(1);
		grid[62][177].setLivingStatus(1);
		grid[62][178].setLivingStatus(1);
		grid[62][179].setLivingStatus(1);
		grid[62][180].setLivingStatus(1);
		grid[62][181].setLivingStatus(1);
		grid[62][185].setLivingStatus(1);
		grid[62][186].setLivingStatus(1);
		grid[62][187].setLivingStatus(1);
		grid[62][188].setLivingStatus(1);
		grid[62][189].setLivingStatus(1);
		grid[62][190].setLivingStatus(1);
		grid[62][191].setLivingStatus(1);
		grid[62][192].setLivingStatus(1);
		grid[63][95].setLivingStatus(1);
		grid[63][96].setLivingStatus(1);
		grid[63][104].setLivingStatus(1);
		grid[63][105].setLivingStatus(1);
		grid[63][109].setLivingStatus(1);
		grid[63][110].setLivingStatus(1);
		grid[63][114].setLivingStatus(1);
		grid[63][115].setLivingStatus(1);
		grid[63][120].setLivingStatus(1);
		grid[63][121].setLivingStatus(1);
		grid[63][126].setLivingStatus(1);
		grid[63][127].setLivingStatus(1);
		grid[63][132].setLivingStatus(1);
		grid[63][133].setLivingStatus(1);
		grid[63][137].setLivingStatus(1);
		grid[63][138].setLivingStatus(1);
		grid[63][139].setLivingStatus(1);
		grid[63][140].setLivingStatus(1);
		grid[63][147].setLivingStatus(1);
		grid[63][148].setLivingStatus(1);
		grid[63][149].setLivingStatus(1);
		grid[63][150].setLivingStatus(1);
		grid[63][151].setLivingStatus(1);
		grid[63][152].setLivingStatus(1);
		grid[63][153].setLivingStatus(1);
		grid[63][154].setLivingStatus(1);
		grid[63][163].setLivingStatus(1);
		grid[63][164].setLivingStatus(1);
		grid[63][173].setLivingStatus(1);
		grid[63][174].setLivingStatus(1);
		grid[63][180].setLivingStatus(1);
		grid[63][181].setLivingStatus(1);
		grid[63][185].setLivingStatus(1);
		grid[63][186].setLivingStatus(1);
		grid[63][191].setLivingStatus(1);
		grid[63][192].setLivingStatus(1);
		grid[64][95].setLivingStatus(1);
		grid[64][96].setLivingStatus(1);
		grid[64][104].setLivingStatus(1);
		grid[64][105].setLivingStatus(1);
		grid[64][109].setLivingStatus(1);
		grid[64][110].setLivingStatus(1);
		grid[64][114].setLivingStatus(1);
		grid[64][115].setLivingStatus(1);
		grid[64][120].setLivingStatus(1);
		grid[64][121].setLivingStatus(1);
		grid[64][126].setLivingStatus(1);
		grid[64][127].setLivingStatus(1);
		grid[64][132].setLivingStatus(1);
		grid[64][133].setLivingStatus(1);
		grid[64][137].setLivingStatus(1);
		grid[64][138].setLivingStatus(1);
		grid[64][140].setLivingStatus(1);
		grid[64][141].setLivingStatus(1);
		grid[64][147].setLivingStatus(1);
		grid[64][148].setLivingStatus(1);
		grid[64][149].setLivingStatus(1);
		grid[64][150].setLivingStatus(1);
		grid[64][151].setLivingStatus(1);
		grid[64][152].setLivingStatus(1);
		grid[64][153].setLivingStatus(1);
		grid[64][154].setLivingStatus(1);
		grid[64][163].setLivingStatus(1);
		grid[64][164].setLivingStatus(1);
		grid[64][173].setLivingStatus(1);
		grid[64][174].setLivingStatus(1);
		grid[64][180].setLivingStatus(1);
		grid[64][181].setLivingStatus(1);
		grid[64][185].setLivingStatus(1);
		grid[64][186].setLivingStatus(1);
		grid[65][95].setLivingStatus(1);
		grid[65][96].setLivingStatus(1);
		grid[65][104].setLivingStatus(1);
		grid[65][105].setLivingStatus(1);
		grid[65][109].setLivingStatus(1);
		grid[65][110].setLivingStatus(1);
		grid[65][114].setLivingStatus(1);
		grid[65][115].setLivingStatus(1);
		grid[65][120].setLivingStatus(1);
		grid[65][121].setLivingStatus(1);
		grid[65][126].setLivingStatus(1);
		grid[65][127].setLivingStatus(1);
		grid[65][132].setLivingStatus(1);
		grid[65][133].setLivingStatus(1);
		grid[65][137].setLivingStatus(1);
		grid[65][138].setLivingStatus(1);
		grid[65][141].setLivingStatus(1);
		grid[65][142].setLivingStatus(1);
		grid[65][153].setLivingStatus(1);
		grid[65][154].setLivingStatus(1);
		grid[65][163].setLivingStatus(1);
		grid[65][164].setLivingStatus(1);
		grid[65][173].setLivingStatus(1);
		grid[65][174].setLivingStatus(1);
		grid[65][180].setLivingStatus(1);
		grid[65][181].setLivingStatus(1);
		grid[65][185].setLivingStatus(1);
		grid[65][186].setLivingStatus(1);
		grid[66][95].setLivingStatus(1);
		grid[66][96].setLivingStatus(1);
		grid[66][104].setLivingStatus(1);
		grid[66][105].setLivingStatus(1);
		grid[66][109].setLivingStatus(1);
		grid[66][110].setLivingStatus(1);
		grid[66][114].setLivingStatus(1);
		grid[66][115].setLivingStatus(1);
		grid[66][116].setLivingStatus(1);
		grid[66][117].setLivingStatus(1);
		grid[66][118].setLivingStatus(1);
		grid[66][119].setLivingStatus(1);
		grid[66][120].setLivingStatus(1);
		grid[66][121].setLivingStatus(1);
		grid[66][126].setLivingStatus(1);
		grid[66][127].setLivingStatus(1);
		grid[66][132].setLivingStatus(1);
		grid[66][133].setLivingStatus(1);
		grid[66][137].setLivingStatus(1);
		grid[66][138].setLivingStatus(1);
		grid[66][142].setLivingStatus(1);
		grid[66][143].setLivingStatus(1);
		grid[66][147].setLivingStatus(1);
		grid[66][148].setLivingStatus(1);
		grid[66][149].setLivingStatus(1);
		grid[66][150].setLivingStatus(1);
		grid[66][151].setLivingStatus(1);
		grid[66][152].setLivingStatus(1);
		grid[66][153].setLivingStatus(1);
		grid[66][154].setLivingStatus(1);
		grid[66][163].setLivingStatus(1);
		grid[66][164].setLivingStatus(1);
		grid[66][173].setLivingStatus(1);
		grid[66][174].setLivingStatus(1);
		grid[66][175].setLivingStatus(1);
		grid[66][176].setLivingStatus(1);
		grid[66][177].setLivingStatus(1);
		grid[66][178].setLivingStatus(1);
		grid[66][179].setLivingStatus(1);
		grid[66][180].setLivingStatus(1);
		grid[66][181].setLivingStatus(1);
		grid[66][185].setLivingStatus(1);
		grid[66][186].setLivingStatus(1);
		grid[67][95].setLivingStatus(1);
		grid[67][96].setLivingStatus(1);
		grid[67][104].setLivingStatus(1);
		grid[67][105].setLivingStatus(1);
		grid[67][109].setLivingStatus(1);
		grid[67][110].setLivingStatus(1);
		grid[67][114].setLivingStatus(1);
		grid[67][115].setLivingStatus(1);
		grid[67][116].setLivingStatus(1);
		grid[67][117].setLivingStatus(1);
		grid[67][118].setLivingStatus(1);
		grid[67][119].setLivingStatus(1);
		grid[67][120].setLivingStatus(1);
		grid[67][121].setLivingStatus(1);
		grid[67][122].setLivingStatus(1);
		grid[67][126].setLivingStatus(1);
		grid[67][127].setLivingStatus(1);
		grid[67][132].setLivingStatus(1);
		grid[67][133].setLivingStatus(1);
		grid[67][137].setLivingStatus(1);
		grid[67][138].setLivingStatus(1);
		grid[67][143].setLivingStatus(1);
		grid[67][147].setLivingStatus(1);
		grid[67][148].setLivingStatus(1);
		grid[67][149].setLivingStatus(1);
		grid[67][150].setLivingStatus(1);
		grid[67][151].setLivingStatus(1);
		grid[67][152].setLivingStatus(1);
		grid[67][153].setLivingStatus(1);
		grid[67][154].setLivingStatus(1);
		grid[67][163].setLivingStatus(1);
		grid[67][164].setLivingStatus(1);
		grid[67][173].setLivingStatus(1);
		grid[67][174].setLivingStatus(1);
		grid[67][175].setLivingStatus(1);
		grid[67][176].setLivingStatus(1);
		grid[67][177].setLivingStatus(1);
		grid[67][178].setLivingStatus(1);
		grid[67][179].setLivingStatus(1);
		grid[67][180].setLivingStatus(1);
		grid[67][181].setLivingStatus(1);
		grid[67][185].setLivingStatus(1);
		grid[67][186].setLivingStatus(1);
		grid[79][132].setLivingStatus(1);
		grid[79][133].setLivingStatus(1);
		grid[79][149].setLivingStatus(1);
		grid[79][150].setLivingStatus(1);
		grid[80][104].setLivingStatus(1);
		grid[80][105].setLivingStatus(1);
		grid[80][115].setLivingStatus(1);
		grid[80][116].setLivingStatus(1);
		grid[80][132].setLivingStatus(1);
		grid[80][133].setLivingStatus(1);
		grid[80][149].setLivingStatus(1);
		grid[80][150].setLivingStatus(1);
		grid[81][104].setLivingStatus(1);
		grid[81][105].setLivingStatus(1);
		grid[81][115].setLivingStatus(1);
		grid[81][116].setLivingStatus(1);
		grid[81][132].setLivingStatus(1);
		grid[81][133].setLivingStatus(1);
		grid[81][149].setLivingStatus(1);
		grid[81][150].setLivingStatus(1);
		grid[82][104].setLivingStatus(1);
		grid[82][105].setLivingStatus(1);
		grid[82][115].setLivingStatus(1);
		grid[82][116].setLivingStatus(1);
		grid[82][128].setLivingStatus(1);
		grid[82][129].setLivingStatus(1);
		grid[82][130].setLivingStatus(1);
		grid[82][131].setLivingStatus(1);
		grid[82][132].setLivingStatus(1);
		grid[82][133].setLivingStatus(1);
		grid[82][134].setLivingStatus(1);
		grid[82][135].setLivingStatus(1);
		grid[82][136].setLivingStatus(1);
		grid[82][137].setLivingStatus(1);
		grid[82][149].setLivingStatus(1);
		grid[82][150].setLivingStatus(1);
		grid[82][159].setLivingStatus(1);
		grid[82][160].setLivingStatus(1);
		grid[83][104].setLivingStatus(1);
		grid[83][105].setLivingStatus(1);
		grid[83][115].setLivingStatus(1);
		grid[83][116].setLivingStatus(1);
		grid[83][128].setLivingStatus(1);
		grid[83][129].setLivingStatus(1);
		grid[83][130].setLivingStatus(1);
		grid[83][131].setLivingStatus(1);
		grid[83][132].setLivingStatus(1);
		grid[83][133].setLivingStatus(1);
		grid[83][134].setLivingStatus(1);
		grid[83][135].setLivingStatus(1);
		grid[83][136].setLivingStatus(1);
		grid[83][137].setLivingStatus(1);
		grid[83][149].setLivingStatus(1);
		grid[83][150].setLivingStatus(1);
		grid[84][104].setLivingStatus(1);
		grid[84][105].setLivingStatus(1);
		grid[84][115].setLivingStatus(1);
		grid[84][116].setLivingStatus(1);
		grid[84][132].setLivingStatus(1);
		grid[84][133].setLivingStatus(1);
		grid[84][149].setLivingStatus(1);
		grid[84][150].setLivingStatus(1);
		grid[84][164].setLivingStatus(1);
		grid[84][165].setLivingStatus(1);
		grid[84][183].setLivingStatus(1);
		grid[84][184].setLivingStatus(1);
		grid[85][104].setLivingStatus(1);
		grid[85][105].setLivingStatus(1);
		grid[85][115].setLivingStatus(1);
		grid[85][116].setLivingStatus(1);
		grid[85][120].setLivingStatus(1);
		grid[85][121].setLivingStatus(1);
		grid[85][122].setLivingStatus(1);
		grid[85][123].setLivingStatus(1);
		grid[85][124].setLivingStatus(1);
		grid[85][125].setLivingStatus(1);
		grid[85][126].setLivingStatus(1);
		grid[85][127].setLivingStatus(1);
		grid[85][132].setLivingStatus(1);
		grid[85][133].setLivingStatus(1);
		grid[85][139].setLivingStatus(1);
		grid[85][140].setLivingStatus(1);
		grid[85][141].setLivingStatus(1);
		grid[85][142].setLivingStatus(1);
		grid[85][143].setLivingStatus(1);
		grid[85][144].setLivingStatus(1);
		grid[85][145].setLivingStatus(1);
		grid[85][149].setLivingStatus(1);
		grid[85][150].setLivingStatus(1);
		grid[85][151].setLivingStatus(1);
		grid[85][152].setLivingStatus(1);
		grid[85][153].setLivingStatus(1);
		grid[85][154].setLivingStatus(1);
		grid[85][155].setLivingStatus(1);
		grid[85][159].setLivingStatus(1);
		grid[85][160].setLivingStatus(1);
		grid[85][164].setLivingStatus(1);
		grid[85][165].setLivingStatus(1);
		grid[85][166].setLivingStatus(1);
		grid[85][167].setLivingStatus(1);
		grid[85][168].setLivingStatus(1);
		grid[85][169].setLivingStatus(1);
		grid[85][170].setLivingStatus(1);
		grid[85][171].setLivingStatus(1);
		grid[85][175].setLivingStatus(1);
		grid[85][176].setLivingStatus(1);
		grid[85][177].setLivingStatus(1);
		grid[85][178].setLivingStatus(1);
		grid[85][179].setLivingStatus(1);
		grid[85][180].setLivingStatus(1);
		grid[85][181].setLivingStatus(1);
		grid[85][182].setLivingStatus(1);
		grid[85][183].setLivingStatus(1);
		grid[85][184].setLivingStatus(1);
		grid[86][104].setLivingStatus(1);
		grid[86][105].setLivingStatus(1);
		grid[86][115].setLivingStatus(1);
		grid[86][116].setLivingStatus(1);
		grid[86][120].setLivingStatus(1);
		grid[86][121].setLivingStatus(1);
		grid[86][122].setLivingStatus(1);
		grid[86][123].setLivingStatus(1);
		grid[86][124].setLivingStatus(1);
		grid[86][125].setLivingStatus(1);
		grid[86][126].setLivingStatus(1);
		grid[86][127].setLivingStatus(1);
		grid[86][132].setLivingStatus(1);
		grid[86][133].setLivingStatus(1);
		grid[86][139].setLivingStatus(1);
		grid[86][140].setLivingStatus(1);
		grid[86][141].setLivingStatus(1);
		grid[86][142].setLivingStatus(1);
		grid[86][143].setLivingStatus(1);
		grid[86][144].setLivingStatus(1);
		grid[86][145].setLivingStatus(1);
		grid[86][149].setLivingStatus(1);
		grid[86][150].setLivingStatus(1);
		grid[86][151].setLivingStatus(1);
		grid[86][152].setLivingStatus(1);
		grid[86][153].setLivingStatus(1);
		grid[86][154].setLivingStatus(1);
		grid[86][155].setLivingStatus(1);
		grid[86][159].setLivingStatus(1);
		grid[86][160].setLivingStatus(1);
		grid[86][164].setLivingStatus(1);
		grid[86][165].setLivingStatus(1);
		grid[86][166].setLivingStatus(1);
		grid[86][167].setLivingStatus(1);
		grid[86][168].setLivingStatus(1);
		grid[86][169].setLivingStatus(1);
		grid[86][170].setLivingStatus(1);
		grid[86][171].setLivingStatus(1);
		grid[86][175].setLivingStatus(1);
		grid[86][176].setLivingStatus(1);
		grid[86][177].setLivingStatus(1);
		grid[86][178].setLivingStatus(1);
		grid[86][179].setLivingStatus(1);
		grid[86][180].setLivingStatus(1);
		grid[86][181].setLivingStatus(1);
		grid[86][182].setLivingStatus(1);
		grid[86][183].setLivingStatus(1);
		grid[86][184].setLivingStatus(1);
		grid[87][104].setLivingStatus(1);
		grid[87][105].setLivingStatus(1);
		grid[87][109].setLivingStatus(1);
		grid[87][110].setLivingStatus(1);
		grid[87][111].setLivingStatus(1);
		grid[87][115].setLivingStatus(1);
		grid[87][116].setLivingStatus(1);
		grid[87][120].setLivingStatus(1);
		grid[87][121].setLivingStatus(1);
		grid[87][126].setLivingStatus(1);
		grid[87][127].setLivingStatus(1);
		grid[87][132].setLivingStatus(1);
		grid[87][133].setLivingStatus(1);
		grid[87][139].setLivingStatus(1);
		grid[87][140].setLivingStatus(1);
		grid[87][149].setLivingStatus(1);
		grid[87][150].setLivingStatus(1);
		grid[87][154].setLivingStatus(1);
		grid[87][155].setLivingStatus(1);
		grid[87][159].setLivingStatus(1);
		grid[87][160].setLivingStatus(1);
		grid[87][164].setLivingStatus(1);
		grid[87][165].setLivingStatus(1);
		grid[87][170].setLivingStatus(1);
		grid[87][171].setLivingStatus(1);
		grid[87][175].setLivingStatus(1);
		grid[87][176].setLivingStatus(1);
		grid[87][182].setLivingStatus(1);
		grid[87][183].setLivingStatus(1);
		grid[87][184].setLivingStatus(1);
		grid[88][104].setLivingStatus(1);
		grid[88][105].setLivingStatus(1);
		grid[88][108].setLivingStatus(1);
		grid[88][109].setLivingStatus(1);
		grid[88][111].setLivingStatus(1);
		grid[88][112].setLivingStatus(1);
		grid[88][115].setLivingStatus(1);
		grid[88][116].setLivingStatus(1);
		grid[88][120].setLivingStatus(1);
		grid[88][121].setLivingStatus(1);
		grid[88][126].setLivingStatus(1);
		grid[88][127].setLivingStatus(1);
		grid[88][132].setLivingStatus(1);
		grid[88][133].setLivingStatus(1);
		grid[88][139].setLivingStatus(1);
		grid[88][140].setLivingStatus(1);
		grid[88][149].setLivingStatus(1);
		grid[88][150].setLivingStatus(1);
		grid[88][154].setLivingStatus(1);
		grid[88][155].setLivingStatus(1);
		grid[88][159].setLivingStatus(1);
		grid[88][160].setLivingStatus(1);
		grid[88][164].setLivingStatus(1);
		grid[88][165].setLivingStatus(1);
		grid[88][170].setLivingStatus(1);
		grid[88][171].setLivingStatus(1);
		grid[88][175].setLivingStatus(1);
		grid[88][176].setLivingStatus(1);
		grid[88][182].setLivingStatus(1);
		grid[88][183].setLivingStatus(1);
		grid[88][184].setLivingStatus(1);
		grid[89][104].setLivingStatus(1);
		grid[89][105].setLivingStatus(1);
		grid[89][107].setLivingStatus(1);
		grid[89][108].setLivingStatus(1);
		grid[89][112].setLivingStatus(1);
		grid[89][113].setLivingStatus(1);
		grid[89][115].setLivingStatus(1);
		grid[89][116].setLivingStatus(1);
		grid[89][120].setLivingStatus(1);
		grid[89][121].setLivingStatus(1);
		grid[89][126].setLivingStatus(1);
		grid[89][127].setLivingStatus(1);
		grid[89][132].setLivingStatus(1);
		grid[89][133].setLivingStatus(1);
		grid[89][139].setLivingStatus(1);
		grid[89][140].setLivingStatus(1);
		grid[89][149].setLivingStatus(1);
		grid[89][150].setLivingStatus(1);
		grid[89][154].setLivingStatus(1);
		grid[89][155].setLivingStatus(1);
		grid[89][159].setLivingStatus(1);
		grid[89][160].setLivingStatus(1);
		grid[89][164].setLivingStatus(1);
		grid[89][165].setLivingStatus(1);
		grid[89][170].setLivingStatus(1);
		grid[89][171].setLivingStatus(1);
		grid[89][175].setLivingStatus(1);
		grid[89][176].setLivingStatus(1);
		grid[89][182].setLivingStatus(1);
		grid[89][183].setLivingStatus(1);
		grid[89][184].setLivingStatus(1);
		grid[90][104].setLivingStatus(1);
		grid[90][105].setLivingStatus(1);
		grid[90][106].setLivingStatus(1);
		grid[90][107].setLivingStatus(1);
		grid[90][113].setLivingStatus(1);
		grid[90][114].setLivingStatus(1);
		grid[90][115].setLivingStatus(1);
		grid[90][116].setLivingStatus(1);
		grid[90][120].setLivingStatus(1);
		grid[90][121].setLivingStatus(1);
		grid[90][122].setLivingStatus(1);
		grid[90][123].setLivingStatus(1);
		grid[90][124].setLivingStatus(1);
		grid[90][125].setLivingStatus(1);
		grid[90][126].setLivingStatus(1);
		grid[90][127].setLivingStatus(1);
		grid[90][132].setLivingStatus(1);
		grid[90][133].setLivingStatus(1);
		grid[90][139].setLivingStatus(1);
		grid[90][140].setLivingStatus(1);
		grid[90][141].setLivingStatus(1);
		grid[90][142].setLivingStatus(1);
		grid[90][143].setLivingStatus(1);
		grid[90][144].setLivingStatus(1);
		grid[90][145].setLivingStatus(1);
		grid[90][149].setLivingStatus(1);
		grid[90][150].setLivingStatus(1);
		grid[90][154].setLivingStatus(1);
		grid[90][155].setLivingStatus(1);
		grid[90][159].setLivingStatus(1);
		grid[90][160].setLivingStatus(1);
		grid[90][164].setLivingStatus(1);
		grid[90][165].setLivingStatus(1);
		grid[90][170].setLivingStatus(1);
		grid[90][171].setLivingStatus(1);
		grid[90][175].setLivingStatus(1);
		grid[90][176].setLivingStatus(1);
		grid[90][177].setLivingStatus(1);
		grid[90][178].setLivingStatus(1);
		grid[90][179].setLivingStatus(1);
		grid[90][180].setLivingStatus(1);
		grid[90][181].setLivingStatus(1);
		grid[90][182].setLivingStatus(1);
		grid[90][183].setLivingStatus(1);
		grid[90][184].setLivingStatus(1);
		grid[91][104].setLivingStatus(1);
		grid[91][105].setLivingStatus(1);
		grid[91][106].setLivingStatus(1);
		grid[91][114].setLivingStatus(1);
		grid[91][115].setLivingStatus(1);
		grid[91][116].setLivingStatus(1);
		grid[91][120].setLivingStatus(1);
		grid[91][121].setLivingStatus(1);
		grid[91][122].setLivingStatus(1);
		grid[91][123].setLivingStatus(1);
		grid[91][124].setLivingStatus(1);
		grid[91][125].setLivingStatus(1);
		grid[91][126].setLivingStatus(1);
		grid[91][127].setLivingStatus(1);
		grid[91][128].setLivingStatus(1);
		grid[91][132].setLivingStatus(1);
		grid[91][133].setLivingStatus(1);
		grid[91][139].setLivingStatus(1);
		grid[91][140].setLivingStatus(1);
		grid[91][141].setLivingStatus(1);
		grid[91][142].setLivingStatus(1);
		grid[91][143].setLivingStatus(1);
		grid[91][144].setLivingStatus(1);
		grid[91][145].setLivingStatus(1);
		grid[91][149].setLivingStatus(1);
		grid[91][150].setLivingStatus(1);
		grid[91][154].setLivingStatus(1);
		grid[91][155].setLivingStatus(1);
		grid[91][159].setLivingStatus(1);
		grid[91][160].setLivingStatus(1);
		grid[91][164].setLivingStatus(1);
		grid[91][165].setLivingStatus(1);
		grid[91][170].setLivingStatus(1);
		grid[91][171].setLivingStatus(1);
		grid[91][175].setLivingStatus(1);
		grid[91][176].setLivingStatus(1);
		grid[91][177].setLivingStatus(1);
		grid[91][178].setLivingStatus(1);
		grid[91][179].setLivingStatus(1);
		grid[91][180].setLivingStatus(1);
		grid[91][181].setLivingStatus(1);
		grid[91][182].setLivingStatus(1);
		grid[91][183].setLivingStatus(1);
		grid[91][184].setLivingStatus(1);
		grid[92][183].setLivingStatus(1);
		grid[92][184].setLivingStatus(1);
		grid[93][175].setLivingStatus(1);
		grid[93][176].setLivingStatus(1);
		grid[93][177].setLivingStatus(1);
		grid[93][178].setLivingStatus(1);
		grid[93][179].setLivingStatus(1);
		grid[93][180].setLivingStatus(1);
		grid[93][181].setLivingStatus(1);
		grid[93][182].setLivingStatus(1);
		grid[93][183].setLivingStatus(1);
		grid[93][184].setLivingStatus(1);
		grid[94][175].setLivingStatus(1);
		grid[94][176].setLivingStatus(1);
		grid[94][177].setLivingStatus(1);
		grid[94][178].setLivingStatus(1);
		grid[94][179].setLivingStatus(1);
		grid[94][180].setLivingStatus(1);
		grid[94][181].setLivingStatus(1);
		grid[94][182].setLivingStatus(1);
		grid[94][183].setLivingStatus(1);
		grid[94][184].setLivingStatus(1);
		
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
