import java.awt.*;
import javax.swing.*;

public class ColorPalette {
	
	private Color[] colors; //in life --> death order
	private int cycleLength; //num cyclesDead until black
	
	public Color[] getColors() { return this.colors; }
	public void setColors(Color[] colors) { this.colors = colors; }
	public int getCycleLength() { return this.cycleLength; }
	public void setCycleLength(int c) {this.cycleLength = c; }
	
	public ColorPalette() { //note that first color needs to be white
		this.colors = new Color[] {new Color(255, 255, 255), new Color(86, 207, 225), 
				new Color(78, 168, 222), new Color(94, 96, 206), new Color(116, 0, 184), new Color(0, 0, 0)};
		this.cycleLength = 100;	
	}
	
	public Color nextColor(int cyclesDead) {
		if (cyclesDead > cycleLength) return colors[colors.length-1];
		int cycleSeg = cycleLength / colors.length;
		for (int i = 0; i < colors.length-1; i++) {
			if (cyclesDead < cycleSeg*(i+1) ) {
				cyclesDead -= cycleSeg*i;
				Color last = colors[i];
				Color next = colors[i+1];		
				return new Color(last.getRed() - ((last.getRed() - next.getRed()) / cycleSeg)*cyclesDead, 
						last.getGreen() - ((last.getGreen() - next.getGreen()) / cycleSeg)*cyclesDead, 
						last.getBlue() - ((last.getBlue() - next.getBlue()) / cycleSeg)*cyclesDead);
			}
		}
		
		return colors[colors.length-1]; //just in case
	}
	
}
