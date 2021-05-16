import java.awt.Color;
import java.awt.TextField;
import java.awt.event.*;
import javax.swing.*;

public class InputFrame extends JFrame{
	
	private Input I;
	private JLabel errorL;
	private JPanel panel;
	
	private JButton okB;
	private JButton randomB;
	TextField entry;
	
	public InputFrame(Input i) {
		this.I = i;
		okB = new JButton("OK");
		okB.setActionCommand("input entered");
		randomB = new JButton("random gen");
		errorL = new JLabel("");
		entry = new TextField(8);
	}
	
	public void open() {
		this.setTitle("Set Input");
	   
		panel = new JPanel();
	    this.setContentPane(panel);
	    
	    JLabel directionsL1 = new JLabel("Enter binary input number");
	    JLabel directionsL2 = new JLabel("It MUST be <= 8 digits and contain ONLY 0s and 1s");
		panel.add(directionsL1);
		panel.add(directionsL2);
		
		panel.add(entry);
		panel.add(okB);	
		panel.add(randomB);
		panel.add(errorL);
		
		
		this.pack();
	    this.setSize(400, 200);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);    
	}
	
	public void setInput() {
		String input = entry.getText();
		if (input == null) return;
		input = input.strip();
		if (input.length() > 8) {
			displayErrorMessage("Your input is too long.");
			return;
		}
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != '0' && c != '1') {
				displayErrorMessage(c + " isn't a 0 or 1.");
				return;
			}
		}
		while (input.length() < 8) input = "0" + input;
		I.setBinary(input);
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public void registerActionListeners(ActionListener l) {
		okB.addActionListener(l);
		randomB.addActionListener(l);
	}
	
	public void backToRandom()
	{
		I.setBinary(null);
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public void displayErrorMessage(String message) {
		errorL.setText(message);
		errorL.setForeground(Color.RED);
		panel.repaint();
	}
	
	
}
