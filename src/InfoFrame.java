import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;

public class InfoFrame extends JFrame{
	
	private JButton conwayB;
	private JButton inputB;
	private JButton sandboxB;
	private JButton controlsB;
	
	private JButton simB;
	
	public InfoFrame() {
		conwayB = new JButton("Conway's Game of Life");
		inputB = new JButton("Simpler CA Input");	
		sandboxB = new JButton("Sandbox Mode");
		controlsB = new JButton("Controls");
		simB = new JButton("Go to simulation");
	}
	
	//make images for each on google drawing and have jpanel display the images
	
	public void displayConwayInfo() {
		JPanel panel = new JPanel();
		this.setContentPane(panel);
		this.validate();
		
		BufferedImage image = null;
		try { image = ImageIO.read(ResourceLoader.load("conwayInfo.jpg"));
		} catch (IOException e) { e.printStackTrace(); }
		
		 JLabel imageL = new JLabel(new ImageIcon(image));
		panel.add(imageL);
		panel.add(inputB);
		panel.add(sandboxB);
		panel.add(controlsB);
		panel.add(simB);
		
		this.pack();
		this.setSize(1000, 780);
	    this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void displayInputInfo() {
		JPanel panel = new JPanel();
		this.setContentPane(panel);
		this.validate();
		
		BufferedImage image = null;
		try { image = ImageIO.read(ResourceLoader.load("inputInfo.jpg"));
		} catch (IOException e) { e.printStackTrace(); }
		
		JLabel imageL = new JLabel(new ImageIcon(image));
		panel.add(imageL);
		panel.add(conwayB);
		panel.add(sandboxB);
		panel.add(controlsB);
		panel.add(simB);
		
		this.pack();
		this.setSize(1000, 780);
	    this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void displaySandboxInfo() {
		JPanel panel = new JPanel();
		this.setContentPane(panel);
		this.validate();
		
		BufferedImage image = null;
		try { image = ImageIO.read(ResourceLoader.load("sandboxInfo.jpg"));
		} catch (IOException e) { e.printStackTrace(); }
		
		JLabel imageL = new JLabel(new ImageIcon(image));
		panel.add(imageL);
		panel.add(conwayB);
		panel.add(inputB);
		panel.add(controlsB);
		panel.add(simB);
		
		this.pack();
		this.setSize(1000, 780);
	    this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void displayControlsInfo() {
		JPanel panel = new JPanel();
		this.setContentPane(panel);
		this.validate();
		
		BufferedImage image = null;
		try { image = ImageIO.read(ResourceLoader.load("controlsInfo.jpg"));
		} catch (IOException e) { e.printStackTrace(); }
		
		JLabel imageL = new JLabel(new ImageIcon(image));
		panel.add(imageL);
		panel.add(conwayB);
		panel.add(inputB);
		panel.add(sandboxB);
		panel.add(simB);
		
		this.pack();
		this.setSize(1000, 780);
	    this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void registerActionListeners(ActionListener l) {
		conwayB.addActionListener(l);
		inputB.addActionListener(l);
		sandboxB.addActionListener(l);
		controlsB.addActionListener(l);
		simB.addActionListener(l);
	}

}
