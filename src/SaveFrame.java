import java.awt.event.*;
import javax.swing.*;

public class SaveFrame extends JFrame{
	
	private JPanel currState;
	private String saveCategory;
	
	private JButton okB;
	
	public void registerokBActionListener(ActionListener l) { okB.addActionListener(l); }
	
	public SaveFrame(JPanel currState, String saveCategory) {
		this.currState = currState;
		this.saveCategory = saveCategory;
		okB = new JButton("OK");
		okB.setActionCommand(saveCategory + " saved");
	
	}
	
	public void setCurrState(JPanel newState) { currState = newState; }
	
	public void open() {
		this.setTitle("Save current " + saveCategory);
		JPanel panel = new JPanel();
	    this.setContentPane(panel);
	    
	    if (currState == null) {
	    	panel.add(new JLabel("You don't currently have a " + saveCategory + " set,"));
	    	panel.add(new JLabel("so there is nothing to save"));
	    	okB.setActionCommand("nothing to save: " + saveCategory);
	    }
	    else {
	    	panel.add(new JLabel("The current " + saveCategory + ": "));
	 	    panel.add(currState);
	 	    panel.add(new JLabel(" will be saved to your list of favorites"));
	 	    okB.setActionCommand(saveCategory + " saved");
	    }
	    panel.add(okB);
	    
	    this.pack();
	    this.setSize(400, 200);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}

}
