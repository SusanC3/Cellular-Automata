import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ControlPanel extends JPanel{
	
	private JButton reset;
	private JButton pause;
	private JButton stepThrough;
	
	public JButton getResetB() { return reset; }
	public JButton getPauseB() { return pause; }
	public JButton getStepThroughB() { return stepThrough; }
	
	public ControlPanel() {
		reset = new JButton("Reset");
		add(reset);
		pause = new JButton("Pause");
		add(pause);
		stepThrough = new JButton("Step Through");
		add(stepThrough);
		setOpaque(true); 
	}
	
	public void registerActionListeners(ActionListener l) {
		reset.addActionListener(l);
		pause.addActionListener(l);
		stepThrough.addActionListener(l);
	}
}
