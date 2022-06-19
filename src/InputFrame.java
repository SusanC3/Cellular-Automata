import java.awt.Color;

import java.awt.TextField;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class InputFrame extends JFrame{
	
	private Input I;
	private JLabel errorL;
	private JPanel panel;
	
	private JButton okB1;
	private JButton okB2;
	private JButton randomB;
	TextField entry;
	private JComboBox favoritesCB;
	
	private SaveFrame saveFrame;
	File saved = new File ("savedInputs.txt");
	
	private String[] favoriteInputs = {};
	
	public InputFrame(Input i) {
		this.I = i;
		okB1 = new JButton("OK");
		okB1.setActionCommand("input entered");
		okB2 = new JButton("OK");
		okB2.setActionCommand("input selected");
		randomB = new JButton("random gen");
		errorL = new JLabel("");
		entry = new TextField(8);
		saveFrame = new SaveFrame(null, "input");
		
//		try { saved.createNewFile(); }
//		catch (IOException e) { e.printStackTrace(); }
		
		//read favoriteInputs from file
		try { 
			ArrayList<String> temp = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(saved));
			String binary;
		    while ((binary = br.readLine()) != null) {
		       temp.add(binary);
		    }
		    favoriteInputs = new String[temp.size()];
		    for (int j = 0; j < favoriteInputs.length; j++) favoriteInputs[j] = temp.get(j);
		} catch (Exception e) { e.printStackTrace(); }
		
		favoritesCB = new JComboBox(favoriteInputs); //gotta do this every time i make it a new array cause otherwise it still points to the old one
	}
	
	public String getComboSelectedItem() { return (String)favoritesCB.getSelectedItem(); } //should always be a string
	public void setEntryText(String text) { entry.setText(text); }
	public Input getInput() { return I; }
	public SaveFrame getSaveFrame() { return saveFrame; }
	
	public void open() {
		this.setTitle("Set Input");
	   
		panel = new JPanel();
	    this.setContentPane(panel);
	    
	    JLabel directionsL1 = new JLabel("Enter binary input number");
	    JLabel directionsL2 = new JLabel("It MUST be <= 8 digits and contain ONLY 0s and 1s");
		panel.add(directionsL1);
		panel.add(directionsL2);
		
		panel.add(entry);
		panel.add(okB1);	
		panel.add(randomB);
		panel.add(errorL);
		panel.add(new JLabel("Or choose from one of my favorites:"));
		panel.add(favoritesCB);	
		panel.add(okB2);
		
		this.pack();
	    this.setSize(400, 200);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);    
	}
	
	public boolean setInput() {
		String input = entry.getText();
		if (input == null) return true;
		input = input.strip();
		if (input.length() > 8) {
			displayErrorMessage("Your input is too long.");
			return false;
		}
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != '0' && c != '1') {
				displayErrorMessage(c + " isn't a 0 or 1.");
				return false;
			}
		}
		while (input.length() < 8) input = "0" + input;
		I.setBinary(input);
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		return true;
	}
	
	
	public void registerActionListeners(ActionListener l) {
		okB1.addActionListener(l);
		okB2.addActionListener(l);
		randomB.addActionListener(l);
		favoritesCB.addActionListener(l);
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
	
	public void saveInput() {
		System.out.println("here");
		String currInput = I.getBinary();
		//don't save input already there
		for (String s : favoriteInputs) {
			if (s.equals(currInput)) return; //we don't need to save it
		}
	    try {
	    	FileWriter writer = new FileWriter(saved, true);
			writer.append(currInput + '\n'); 
			writer.close();
			//gotta redo array as well
			ArrayList<String> temp = new ArrayList<String>();
  			BufferedReader br = new BufferedReader(new FileReader(saved));
  			String binary;
  		    while ((binary = br.readLine()) != null) {
  		       temp.add(binary);
  		    }
  		    System.out.println("favorite inputs being modified");
  		    favoriteInputs = new String[temp.size()];
  		    for (int j = 0; j < favoriteInputs.length; j++) favoriteInputs[j] = temp.get(j);
		} catch (IOException e) { e.printStackTrace(); }   
	    	
	  	favoritesCB = new JComboBox(favoriteInputs); //gotta do this every time i make it a new array cause otherwise it still points to the old one
	}
	
	
}
