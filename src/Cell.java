import java.awt.Color;

public class Cell {
	private int x;
	private int y;
	public Color color;
	private int alive; //mostly functions as a boolean but is an int to help with neighbor counts
	private int cyclesDead;
	private ColorPalette palette;
	private Rule rule;
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public Color getColor() { return this.color; }
	public int isAlive() { return this.alive; }
	public void setLivingStatus(int newAlive) { 
		this.alive = newAlive; 
		if (this.alive == 1) { this.color = palette.getColors()[0]; this.cyclesDead = 0;}
		if (this.alive == 0) this.color = palette.getColors()[palette.getColors().length-1];	 
	}
	public int getCyclesDead() { return this.cyclesDead; }
	public void incrementCyclesDead() { this.cyclesDead++; }
	
	public Cell makeCopy() {
		return new Cell (this.x, this.y, this.color, this.alive, this.palette, this.rule);
	}
	
	public Cell(int x, int y, Color color, int alive, ColorPalette palette, Rule rule) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.alive = alive;
		if (this.alive == 1) this.cyclesDead = 0;
		if (this.alive == 0) this.cyclesDead = 100000000; // not recently dead
		this.palette = palette;
		this.rule = rule;
	}
	
	void Update(int up, int upL, int upR, int left, int right, int down, int downL, int downR) {	
		//life status
		this.alive = rule.conwayUpdate(this.alive, up, upL, upR, left, right, down, downL,downR);
		if (this.alive == 1) cyclesDead = 0;
		
		//color
		if (this.alive == 1) this.color = palette.getColors()[0];
		else this.color = palette.nextColor(cyclesDead); 
	}
	
}
