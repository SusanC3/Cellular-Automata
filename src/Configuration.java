
public class Configuration {

//honestly I kinda ran out of steam; if I get a request asking me to add this i will but this is just too much effort rn
	
	//grid. value is # of cycles dead
	private Cell initConfig[][];
	private Cell currConfig[][];
	
	//called after init grid
	public void saveinitConfig(Cell grid[][]) {
		initConfig = new Cell[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) { //basically took code from backupGrid() in model
			for (int j = 0; j < grid[i].length; j++) {
				initConfig[i][j] = grid[i][j].makeCopy();
			}
		}
	}
	
	//called after usr requests to save curr config
	public void saveCurrConfig(Cell grid[][]) { //no difference, just which arr they store to
		currConfig = new Cell[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				currConfig[i][j] = grid[i][j].makeCopy();
			}
		}
	}
	
	//should configuration save color palette and CA rule as well? both of those can be saved independently
	//hm, cells make you define them as alive or dead, you can't make them with certain # cycles dead
	
	//actually writes grid to file
	public void writeInitConfig() {
		//first line, write color palette
		//second line, write rule
		//3-3+n lines, write cycles dead in grid configuration
	}
	
	public void writeCurrConfig() {
		
	}

	public Cell[][] getSavedConfig(int index) {
		return currConfig;
		
	}

}
