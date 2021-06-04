import java.awt.Color;

public class Cell {
	private int x;
	private int y;
	public Color color;
	private int alive; //mostly functions as a boolean but is an int to help with neighbor counts
	private int cyclesDead; //remember to increment each cycle in module (or controller? idk i forget)
	private ColorPalette palette;
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public Color getColor() { return this.color; }
	public int isAlive() { return this.alive; }
	public void setLivingStatus(int newAlive) { 
		this.alive = newAlive; 
		if (this.alive == 1) { this.color = Color.white; this.cyclesDead = 0;}
		if (this.alive == 0) this.color = Color.black;	 
	}
	public int getCyclesDead() { return this.cyclesDead; }
	public void incrementCyclesDead() { this.cyclesDead++; }
	
	public Cell makeCopy() {
		return new Cell (this.x, this.y, this.color, this.alive, this.palette);
	}
	
	public Cell(int x, int y, Color color, int alive, ColorPalette palette) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.alive = alive;
		if (this.alive == 1) this.cyclesDead = 0;
		if (this.alive == 0) this.cyclesDead = 100000000; //# to get black
		this.palette = palette;
	}
	
	void conwayUpdate(int up, int upL, int upR, int left, int right, int down, int downL, int downR) {	
		//life status
		int neighborCount = up + upL + upR + left + right + down + downL + downR;
		if (this.alive == 1 && (neighborCount < 2 || neighborCount > 3)) this.alive = 0; //living cell dies
		else if (this.alive == 0 && neighborCount == 3) this.alive = 1; //dead cell comes to life
		if (this.alive == 1) cyclesDead = 0;
		
		//color
		if (this.alive == 1) this.color = Color.white;
		else this.color = palette.nextColor(cyclesDead); 
	}
	
}
