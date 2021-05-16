import java.awt.Color;
import java.util.*;

public class Input {
	String binary = null;
	
	public String getBinary() { return this.binary; }
	public void setBinary(String b) { this.binary = b; }
	
	
	int inputMap(int[] key) { //this used to be a hash map but then it got too complicated because the key is an object so now it's a function	
		if (this.binary.length() != 8) {
			System.out.println("Binary length: " + binary.length());
			return 0;
		}
		if (key[0] == 1 && key[1] == 1 && key[2] == 1) return Integer.parseInt(""+this.binary.charAt(0));
		if (key[0] == 1 && key[1] == 1 && key[2] == 0) return Integer.parseInt(""+this.binary.charAt(1));
		if (key[0] == 1 && key[1] == 0 && key[2] == 1) return Integer.parseInt(""+this.binary.charAt(2));
		if (key[0] == 1 && key[1] == 0 && key[2] == 0) return Integer.parseInt(""+this.binary.charAt(3));
		if (key[0] == 0 && key[1] == 1 && key[2] == 1) return Integer.parseInt(""+this.binary.charAt(4));
		if (key[0] == 0 && key[1] == 1 && key[2] == 0) return Integer.parseInt(""+this.binary.charAt(5));
		if (key[0] == 0 && key[1] == 0 && key[2] == 1) return Integer.parseInt(""+this.binary.charAt(6));	
		if (key[0] == 0 && key[1] == 0 && key[2] == 0) return Integer.parseInt(""+this.binary.charAt(7));
		return 0;
	}
	
	public void update(Cell[][] grid) {
		//move up rows

		for (int i = (grid.length/2)-1; i < grid.length-1; i++) { 
			for (int j = 0; j < grid[i].length; j++) { //deep copy
				if (grid[i][j].isAlive() == 0) grid[i][j].incrementCyclesDead();
		//		else System.out.println(i + " " + j);
				grid[i-1][j] = grid[i][j].makeCopy();
			}
		}
		
		//use rule to make bottom row
		for (int j = 1; j < grid[0].length-1; j++) {
			int[] key = { grid[grid.length-3][j-1].isAlive() , grid[grid.length-3][j].isAlive() , grid[grid.length-3][j+1].isAlive() }; //3 cells above grid[1][i]
			grid[grid.length-2][j].setLivingStatus(inputMap(key));
		} 
		
		
	}
}
