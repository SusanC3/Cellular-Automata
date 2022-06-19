import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

import javax.swing.*;

public class RuleFrame extends JFrame{
	
	private JCheckBox checkBoxes[] = new JCheckBox[9];
	private JButton OKButton = new JButton("OK");
	private JButton comboOKButton = new JButton("picked rule");
	private JButton customRuleButton = new JButton("custom rule");
	private Rule rule;	
	ArrayList<ArrayList<Integer>> ruleArr = new ArrayList<ArrayList<Integer>>(2);
	
	
	private SaveFrame saveFrame;
	private String[] favoriteRuleStrings;
	private JComboBox favoritesCB;
	
	public SaveFrame getSaveFrame() { return this.saveFrame; }
	public Rule getRule() {return this.rule; }
	
	public RuleFrame(Rule rule) {
		ruleArr.add(new ArrayList<Integer>());
		ruleArr.add(new ArrayList<Integer>());
		this.rule = rule;
			
	    try {
			ArrayList<String> temp = new ArrayList<String>();
  			BufferedReader br = new BufferedReader(new FileReader(new File("savedRules.txt")));
  			String r;
  		    while ((r = br.readLine()) != null) {
  		       temp.add(r);
  		    }
  		    favoriteRuleStrings = new String[temp.size()];
  		   for (int j = 0; j < favoriteRuleStrings.length; j++) {
  			   favoriteRuleStrings[j] = temp.get(j);
  		   }
		} catch (IOException e) { e.printStackTrace(); }   
	    	
	  	favoritesCB = new JComboBox(favoriteRuleStrings);  //gotta do this every time i make it a new array cause otherwise it still points to the old one

	  	JPanel panel  = new JPanel();
		panel.add(new JLabel(favoriteRuleStrings[0])); //default rule, but I don't wanna read it from file cause that's hard
		saveFrame = new SaveFrame(panel, "rule");
	
	}
	
	public void open() {
		this.setTitle("CA Rule");
	    this.pack();
	    this.setSize(350, 200);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	    
	    favoritesOrCustom();
	}
	
	public void favoritesOrCustom() {
		JPanel panel = new JPanel();
		this.setContentPane(panel);
		this.validate();	
		panel.setLayout(new GridLayout(3, 1));
		
		JPanel comboBoxPanel = new JPanel();
		panel.add(comboBoxPanel);
		comboBoxPanel.add(new JLabel("Choose from my favorite rules"));
		comboBoxPanel.add(favoritesCB);
		comboOKButton.setText("OK");
		comboOKButton.setActionCommand("picked rule");
		comboBoxPanel.add(comboOKButton);
				
		JLabel orLabel = new JLabel("OR");
		orLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(orLabel);
		
		customRuleButton.setText("Make custom rule");
		customRuleButton.setHorizontalAlignment(JButton.CENTER);
		panel.add(customRuleButton);
		
		this.pack();
		this.setSize(350, 210);
	    this.setLocationRelativeTo(null);
		this.setVisible(true); 
	}
	
	public void pickDieNeighbors() { //maybe have it return an array?
		JPanel panel = new JPanel();
		this.setContentPane(panel);
		panel.add(new JLabel("  Select neighbor counts for live cells to die  "));
		for (int i = 0; i < checkBoxes.length; i++) {
			checkBoxes[i] = new JCheckBox(""+i);
			panel.add(checkBoxes[i]);
		}
		OKButton.setActionCommand("picked die neighbors");
		panel.add(OKButton);
		
		this.pack();
		this.setSize(350, 210);
	    this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	public void addDieNeighbors() {
		for (int i = 0; i < checkBoxes.length; i++) {
			if (checkBoxes[i].isSelected()) ruleArr.get(0).add(i);
		}
	}
	
	public void addReviveNeighbors() {
		for (int i = 0; i < checkBoxes.length; i++) {
			if (checkBoxes[i].isSelected()) ruleArr.get(1).add(i);
		}
	}
	
	public void pickReviveNeighbors() {
		//update rule list or smth
		JPanel panel = new JPanel();
		this.setContentPane(panel);
		this.validate();
		panel.add(new JLabel("Select neighbor counts for dead cells to revivify"));
		for (int i = 0; i < checkBoxes.length; i++) {
			checkBoxes[i] = new JCheckBox(""+i);
			panel.add(checkBoxes[i]);
		}
		OKButton.setActionCommand("picked revive neighbors");
		panel.add(OKButton);
	
		this.pack();
	    this.setSize(350, 200);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}
	
	public void registerActionListeners(ActionListener l) {
		OKButton.addActionListener(l);
		customRuleButton.addActionListener(l);
		comboOKButton.addActionListener(l);
	}
	
	public void selectRule() {
		this.rule.setRuleString(favoriteRuleStrings[favoritesCB.getSelectedIndex()]);
	}
	
	public void setRules()
	{
		int[] alive = new int[ruleArr.get(0).size()];
		for (int i = 0; i < ruleArr.get(0).size(); i++) alive[i] = ruleArr.get(0).get(i);
		int[] dead = new int[ruleArr.get(1).size()];
		for (int i = 0; i < ruleArr.get(1).size(); i++) dead[i] = ruleArr.get(1).get(i);
		int[][] newArr = { alive , dead };
		
		rule.setRuleArr(newArr);
	}
	
	public JPanel getCurrRulePanel() {
		JPanel panel  = new JPanel();
		panel.add(new JLabel(rule.toString())); //default rule, but I don't wanna read it from file cause that's hard
		return panel;
	}
	
	public void saveRule() { 
		String currInput = this.rule.toString();
		//don't save input already there
		for (String s : favoriteRuleStrings) {
			if (s.equals(currInput)) return; //we don't need to save it
		}
	    try {
	    	FileWriter writer = new FileWriter(new File("savedRules.txt"), true);
			writer.append(currInput + '\n'); 
			writer.close();
			//gotta redo array as well
			ArrayList<String> temp = new ArrayList<String>();
  			BufferedReader br = new BufferedReader(new FileReader(new File("savedRules.txt")));
  			String r;
  		    while ((r = br.readLine()) != null) {
  		       temp.add(r);
  		    }
  		    favoriteRuleStrings = new String[temp.size()];
  		   for (int j = 0; j < favoriteRuleStrings.length; j++) {
  			   favoriteRuleStrings[j] = temp.get(j);
  		   }
		} catch (IOException e) { e.printStackTrace(); }   
	    	
	  	favoritesCB = new JComboBox(favoriteRuleStrings);  //must do this every time i make it a new array cause otherwise it still points to the old one
	}

}
