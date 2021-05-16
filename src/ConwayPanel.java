import java.awt.*;
import javax.swing.*;

public class ConwayPanel extends JPanel{
	
	Model M;
	private boolean paused = false; 
	
	public boolean getPaused() {return this.paused;}
	public void setPaused(boolean n) {this.paused = n;}
	
	public ConwayPanel(Model m) {
		this.M = m;
		setBackground(Color.BLACK);
	    setOpaque(true); 
	    this.setFocusable(true);
	    this.requestFocus();
	}

	public void paintComponent(Graphics g) {
		for (int i = 0; i < M.getRows(); i++) {
			for (int j = 0; j < M.getColumns(); j++) {
				int x = j*M.getCellSize();
				int y = i*M.getCellSize();
				g.setColor(M.getCellColor(i, j));
				g.fillRect(x, y, M.getCellSize(), M.getCellSize());
			}
		}
		if (!paused) {
			repaint();
			M.doConwayCycle(M.getInput());
		}
	//	try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace();}
	}
}
