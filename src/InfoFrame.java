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
	
	private JPanel panel;
	
	public InfoFrame() {
		conwayB = new JButton("Conway's Game of Life");
		inputB = new JButton("Simpler CA Input");	
		sandboxB = new JButton("Sandbox Mode");
		controlsB = new JButton("Controls");
		simB = new JButton("Go To Simulation");
		simB.setBackground(Color.GREEN);
	}

	
	public void displayConwayInfo() {
		panel = new JPanel();
		this.setContentPane(panel);
		this.validate();
		panel.setLayout(new BorderLayout());
		
		JPanel imagePanel = new JPanel();
		
		BufferedImage image = null;
		try { image = ImageIO.read(ResourceLoader.load("conwayInfo.jpg"));
		} catch (IOException e) { e.printStackTrace(); }
		
		 JLabel imageL = new JLabel(new ImageIcon(image));
		imagePanel.add(imageL);
		panel.add(imagePanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(inputB);
		buttonPanel.add(sandboxB);
		buttonPanel.add(controlsB);
		buttonPanel.add(simB);
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.pack();
		this.setSize(1000, 780);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void displayInputInfo() {
		panel = new JPanel();
		this.setContentPane(panel);
		this.validate();
		panel.setLayout(new BorderLayout());
		
		JPanel imagePanel = new JPanel();
		
		BufferedImage image = null;
		try { image = ImageIO.read(ResourceLoader.load("inputInfo.jpg"));
		} catch (IOException e) { e.printStackTrace(); }
		
		JLabel imageL = new JLabel(new ImageIcon(image));
		imagePanel.add(imageL);
		panel.add(imagePanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(conwayB);
		buttonPanel.add(sandboxB);
		buttonPanel.add(controlsB);
		buttonPanel.add(simB);
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.pack();
		this.setSize(1000, 780);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void displaySandboxInfo() {
		panel = new JPanel();
		this.setContentPane(panel);
		this.validate();
		panel.setLayout(new BorderLayout());
		
		JPanel imagePanel = new JPanel();
		
		BufferedImage image = null;
		try { image = ImageIO.read(ResourceLoader.load("sandboxInfo.jpg"));
		} catch (IOException e) { e.printStackTrace(); }
		
		JLabel imageL = new JLabel(new ImageIcon(image));
		imagePanel.add(imageL);
		panel.add(imagePanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(conwayB);
		buttonPanel.add(inputB);
		buttonPanel.add(controlsB);
		buttonPanel.add(simB);
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.pack();
		this.setSize(1000, 780);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void displayControlsInfo() {
		panel = new JPanel();
		this.setContentPane(panel);
		this.validate();
		panel.setLayout(new BorderLayout());
		
		JPanel imagePanel = new JPanel();		
		
		BufferedImage image = null;
		try { image = ImageIO.read(ResourceLoader.load("controlsInfo.jpg"));
		} catch (IOException e) { e.printStackTrace(); }
		
		JLabel imageL = new JLabel(new ImageIcon(image));
		imagePanel.add(imageL);
		panel.add(imagePanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(conwayB);
		buttonPanel.add(inputB);
		buttonPanel.add(sandboxB);
		buttonPanel.add(simB);
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
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
