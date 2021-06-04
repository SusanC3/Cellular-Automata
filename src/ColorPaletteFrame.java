import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

public class ColorPaletteFrame extends JFrame{
	
	private ColorPalette C;
	
	private JButton OKButton;
	private JSlider slider;
	private JButton rgbButton;
	private JButton colorChooserButton;
	private JColorChooser colorChooser;
	
	private TextField rT;
	private TextField gT;
	private TextField bT;
	
	private int index = 0;
	private int numColors = 0;
	
	private ArrayList<Color> colors;
	
	
	public void setNextColor(Color newColor) { 
		if (newColor == null) colors.remove(Math.max(0, index-1)); //index has been increased by the time this is called
		else colors.set(Math.max(0, index-1), newColor);
	}
	
	public ColorPaletteFrame(ColorPalette C) {
		this.C = C;
		OKButton = new JButton("OK");
		OKButton.setActionCommand("numColorsPicked");
		
		rgbButton = new JButton("");
		rgbButton.setActionCommand("rgb");
		
		colorChooserButton = new JButton("");
		colorChooserButton.setActionCommand("colorChooser");
	}
	
	public void registerActionListeners(ActionListener l) {
		OKButton.addActionListener(l);
		rgbButton.addActionListener(l);
		colorChooserButton.addActionListener(l);
	}
	
	public void open() {
		index = 0;
		numColors = 1;
		this.setTitle("Color Palette");
	    this.pack();
	    this.setSize(400, 200);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	    
	    pickNumColors();
	    
	}
	
	public void pickNumColors() {
		JPanel numColorsPanel = new JPanel();
		this.setContentPane(numColorsPanel);
		numColorsPanel.setLayout(new GridLayout(3, 1));
		
		JLabel numColorsLabel = new JLabel("Select how many colors you want.");
		numColorsLabel.setHorizontalAlignment(JLabel.CENTER);
		numColorsPanel.add(numColorsLabel);
		
		slider = new JSlider(2, 16, C.getColors().length);
		slider.setMajorTickSpacing(2);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
		numColorsPanel.add(slider);
		
		numColorsPanel.add(OKButton);
	}
	
	public void pickColorsSetup() {
		numColors = slider.getValue();
		colors = new ArrayList<Color>();
		for (int i = 0; i < Math.min(6, slider.getValue()); i++) colors.add(C.getColors()[i]);
		for (int i = 0; i < (slider.getValue()-6); i++) colors.add(new Color(0, 0, 0));
		index = 0;
		pickColor();
	}
	
	public void pickColor() {
		if (index >= numColors) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			Color[] newColors = new Color[numColors];
			for (int i = 0; i < numColors; i++) newColors[i] = colors.get(i);
			C.setColors(newColors);
			return;
		}
		JPanel pickColorPanel = new JPanel();
		this.setContentPane(pickColorPanel);
		this.validate();	
		pickColorPanel.setLayout(new GridLayout(3, 1));
		
		rgbButton.setText("Select color " + (index + 1) + " via RGB values");
		rgbButton.setHorizontalAlignment(JButton.CENTER);
		pickColorPanel.add(rgbButton); 
		
		JLabel orLabel = new JLabel("OR");
		orLabel.setHorizontalAlignment(JLabel.CENTER);
		pickColorPanel.add(orLabel);
		
		
		colorChooserButton.setText("Select color " + (index + 1) + " via visual color chooser.");
		colorChooserButton.setHorizontalAlignment(JButton.CENTER);
		pickColorPanel.add(colorChooserButton);
		
		this.pack();
		this.setSize(400, 200);
	    this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
	//I just realized you can do this with the JColorChooser, but I spent hours on this so it's staying
	public void pickRGBColor() {
		JPanel container = new JPanel();
		this.setContentPane(container);
		this.validate();
		
		JPanel errorLabelPanel = new JPanel();
		JLabel errorLabel = new JLabel("If you enter bad input, this color will be discarded."); 
		errorLabel.setHorizontalAlignment(JLabel.CENTER);
		errorLabelPanel.add(errorLabel);	
		container.add(errorLabelPanel);
		
		JPanel RGBColorPanel = new JPanel();
		
		RGBColorPanel.setLayout(new GridLayout(3, 3, 100, 10));
		RGBColorPanel.setBorder(new EmptyBorder(0, 15, 15, 15));		
		container.add(RGBColorPanel);

		JLabel rL = new JLabel("R:");
		rL.setHorizontalAlignment(JLabel.CENTER);
		RGBColorPanel.add(rL);
		JLabel gL = new JLabel("G:");
		gL.setHorizontalAlignment(JLabel.CENTER);
		RGBColorPanel.add(gL);
		JLabel bL = new JLabel("B:");
		bL.setHorizontalAlignment(JLabel.CENTER);
		RGBColorPanel.add(bL);	
		
		rT = new TextField(3);
		RGBColorPanel.add(rT);
		gT = new TextField(3);
		RGBColorPanel.add(gT);
		bT = new TextField(3);
		RGBColorPanel.add(bT);
		
		JLabel temp = new JLabel("");
		RGBColorPanel.add(temp); //please don't bully me i've spend so long trying to make this thing look decent
		
		RGBColorPanel.add(OKButton);
		index++;
		OKButton.setActionCommand("color picked rgb");
		
		this.pack();
		this.setSize(400, 200);
	    this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public Color getRGBColor() {	
		try { return new Color(Integer.parseInt(rT.getText()), 
				Integer.parseInt(gT.getText()), Integer.parseInt(bT.getText())); }
		catch (Exception e) {return null; }
	}
	
	public Color getChooserColor() {
		return colorChooser.getColor();
	}
	
	
	public void pickColorChooser() {
		JPanel colorChooserPanel = new JPanel();
		this.setContentPane(colorChooserPanel);
		this.validate();
		
		colorChooser = new JColorChooser();
		colorChooserPanel.add(colorChooser);
		
		colorChooserPanel.add(OKButton);
		index++;
		OKButton.setActionCommand("color picked chooser");
		
		this.pack();
		this.setSize(650, 400);
	    this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
}
