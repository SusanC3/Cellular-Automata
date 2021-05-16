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
	
	public int getRows() { return this.rows; }
	public int getColumns() { return this.columns; }
	public int getCellSize() { return this.cellSize; }
	public Color getCellColor(int i, int j) {
		return grid[i][j].getColor();
	}
	public Input getInput() { return this.input; }
	public ColorPalette getColorPalette() { return this.palette; }
	
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
				if (this.input.getBinary() == null) { //random generation
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
	

}
