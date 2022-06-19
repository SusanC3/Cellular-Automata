import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.nio.file.FileSystemNotFoundException;

import javax.swing.*;

public class DimensionsFrame extends JFrame {
	
	private JPanel panel;
	private TextField entryR;
	private TextField entryC;
	private TextField entryS;
	private JLabel errorL;
	private JButton okB;
	
	int rows; int columns; int cellSize;
	public int getRows() { return this.rows; }
	public int getColumns() { return this.columns; }
	public int getCellSize() { return this.cellSize; }
	
	public DimensionsFrame(Integer rows, Integer columns, Integer cellSize) {
		entryR = new TextField(rows.toString());
		entryC = new TextField(columns.toString());
		entryS = new TextField(cellSize.toString());
		errorL = new JLabel("");
		okB = new JButton("OK");
		okB.setActionCommand("dimensions entered");
	}
	
	public void open() {
		this.setTitle("Set Dimensions");
		   
		panel = new JPanel();
	    this.setContentPane(panel);
	    
	    JLabel directionsL1 = new JLabel("Enter number of rows and columns.");
	    JLabel directionsL2 = new JLabel("Keep in mind that a larger grid will lag more.              ");
		panel.add(directionsL1);
		panel.add(directionsL2);
		
		
		panel.add(new JLabel("rows: "));
		panel.add(entryR);
		panel.add(new JLabel("columns: "));
		panel.add(entryC);
		panel.add(new JLabel("cell size: "));
		panel.add(entryS);
		panel.add(okB);
		
		panel.add(errorL);
		
		this.pack();
	    this.setSize(350, 200);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);  
	}
	
	public void registerActionListeners(ActionListener l) {
		entryR.addActionListener(l);
		entryC.addActionListener(l);
		entryS.addActionListener(l);
		okB.addActionListener(l);
	}
	
	
	public boolean checkForError() { //false if no error, true if error
		try {
			rows = Integer.parseInt(entryR.getText());
			columns = Integer.parseInt(entryC.getText());
			cellSize = Integer.parseInt(entryS.getText());
		} catch (NumberFormatException e) {
			errorL.setText("Please enter positive integer values");
			errorL.setForeground(Color.RED);
			panel.repaint();
			return true;
		}
		if (rows < 1 || columns < 1 || cellSize < 1) {
			errorL.setText("Please enter positive integer values");
			errorL.setForeground(Color.RED);
			panel.repaint();
			return true;
		}
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		return false; //try catch worked so rows and columns have been set
	}
}
