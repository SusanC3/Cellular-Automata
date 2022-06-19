import java.awt.*;


import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

//import sun.security.util.IOUtils; ;//might be i need newer java version

public class ColorPaletteFrame extends JFrame{
	
	private ColorPalette C;
	
	private JButton OKButton;
	private JButton comboOKButton;
	private JSlider slider;
	private JButton rgbButton;
	private JButton customPaletteButton;
	private JColorChooser colorChooser;
	private JComboBox favoritesCB;
	
	private SaveFrame saveFrame;
	
	private int index = 0;
	private int numColors = 0;
	
	private ArrayList<Color> colors;
	
	private JPanel[] palettePanels;
	private ColorPalette[] comboIndexToPalette;
	
	public int getComboIndex() { return favoritesCB.getSelectedIndex(); } 
	public ColorPalette getComboIndexToPalette(int i) { return comboIndexToPalette[i]; }
	public ColorPalette getPalette() { return C; }
	public SaveFrame getSaveFrame() { return saveFrame; }
	public JPanel[] getPalettePanels() { return palettePanels; }
	
	public void setNextColor(Color newColor) { 
		if (newColor == null) colors.remove(Math.max(0, index-1)); //index has been increased by the time this is called
		else colors.set(Math.max(0, index-1), newColor);
	}
	
	class customRenderer implements ListCellRenderer {	

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int i, boolean isSelected, boolean cellHasFocus) {
			if (i == -1) i = 0;
			if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }

	        return (JPanel)value;
		}
		
	}
	
	
	
	public ColorPaletteFrame(ColorPalette C) {
		this.C = C;
		OKButton = new JButton("OK");
		OKButton.setActionCommand("numColorsPicked");
		
		comboOKButton = new JButton("OK");
		comboOKButton.setActionCommand("palette selected");
		
		rgbButton = new JButton("");
		rgbButton.setActionCommand("rgb");
		
		customPaletteButton = new JButton("");
		customPaletteButton.setActionCommand("customPalette");
		
	/*	try(OutputStream outputStream = new FileOutputStream(saved)){
		    IOUtils.copy(ResourceLoader.load("savedPalettes.txt"), outputStream);
		} catch (FileNotFoundException e) {
		    // handle exception here
		} catch (IOException e) {
		    // handle exception here
		} */
		
		String[] lines = {};
		try { 
			ArrayList<String> temp = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(new File("savedPalettes.txt")));
			String binary;
		    while ((binary = br.readLine()) != null) {
		       temp.add(binary);
		    }
		    lines = new String[temp.size()];
		    for (int j = 0; j < lines.length; j++) lines[j] = temp.get(j);
		} catch (Exception e) { e.printStackTrace(); }
		
		
		palettePanels = new palettePanel[lines.length];
		comboIndexToPalette = new ColorPalette[lines.length];	
	
		for (int i = 0; i < lines.length; i++) {
			comboIndexToPalette[i] = savedStringToPalette(lines[i]);
			palettePanels[i] = new palettePanel(comboIndexToPalette[i]);
		}
		favoritesCB = new JComboBox(palettePanels);
		customRenderer r = new customRenderer();
		favoritesCB.setRenderer(r);
	
		saveFrame = new SaveFrame(palettePanels[0], "color palette"); 
		
	}
	
	public void registerActionListeners(ActionListener l) {
		OKButton.addActionListener(l);
		rgbButton.addActionListener(l);
		customPaletteButton.addActionListener(l);
		favoritesCB.addActionListener(l);
		comboOKButton.addActionListener(l);
	}
	
	public void open() {
		index = 0;
		numColors = 1;
		this.setTitle("Color Palette");
	    this.pack();
	    this.setSize(400, 200);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	    
	    favoritesOrCustom();
	    
	}
	
	public void favoritesOrCustom() {
		JPanel pickColorPanel = new JPanel();
		this.setContentPane(pickColorPanel);
		this.validate();	
		pickColorPanel.setLayout(new GridLayout(3, 1));
		
		JPanel comboBoxPanel = new JPanel();
		pickColorPanel.add(comboBoxPanel);
		comboBoxPanel.add(new JLabel("Choose from my favorite palettes"));
		comboBoxPanel.add(favoritesCB);
		comboBoxPanel.add(comboOKButton);
				
		JLabel orLabel = new JLabel("OR");
		orLabel.setHorizontalAlignment(JLabel.CENTER);
		pickColorPanel.add(orLabel);
			
		customPaletteButton.setText("Make custom color palette");
		customPaletteButton.setHorizontalAlignment(JButton.CENTER);
		pickColorPanel.add(customPaletteButton);
		
		this.pack();
		this.setSize(400, 200);
	    this.setLocationRelativeTo(null);
		this.setVisible(true); 
	}
	
	public void pickNumColors() {
		JPanel numColorsPanel = new JPanel();
		this.setContentPane(numColorsPanel);
		this.validate();
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
		
		this.pack();
		this.setSize(400, 200);
	    this.setLocationRelativeTo(null);
		this.setVisible(true); 
	}
	
	public void pickColorsSetup() {
		numColors = slider.getValue();
		colors = new ArrayList<Color>();
		for (int i = 0; i < Math.min(C.getColors().length, slider.getValue()); i++) colors.add(C.getColors()[i]);
		while (colors.size() < numColors) colors.add(new Color(0, 0, 0));
		index = 0;
		pickColor();
	}
	
	public void pickColor() {
		if (index >= numColors) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			Color[] newColors = new Color[numColors];
			for (int i = 0; i < numColors; i++) newColors[i] = colors.get(i); 
			C.setColors(newColors);
			OKButton.setActionCommand("numColorsPicked");
			return;
		}
		pickColorChooser();
		
	}
	
	public Color getChooserColor() {
		return colorChooser.getColor();
	}
	
	
	public void pickColorChooser() {
		JPanel colorChooserPanel = new JPanel();
		this.setContentPane(colorChooserPanel);
		this.validate();
		this.setTitle("Choose color " + (index+1) + "/" + numColors);
		
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
	
	public class palettePanel extends JPanel {
		ColorPalette palette;
		
		public palettePanel(ColorPalette palette) {
			setOpaque(true);
			this.palette = palette;
			setPreferredSize(new Dimension(160, 25));
		}
		
		public void paintComponent(Graphics g) {
		    super.paintComponent(g);  
		    Color[] colorArr = palette.getColors();
		    if (colorArr.length == 0) return;
		    int width = 156/colorArr.length;
	        for (int i = 0; i < colorArr.length; i++) {
	        	g.setColor(colorArr[i]);
	        	g.fillRect(6+i*width, 0, width, 22);
	        }   
		 }
	}
	
	public palettePanel getCurrPalettePanel() {
		return new palettePanel(C);		
	}
	
	
	public ColorPalette savedStringToPalette(String s) {
		//string of numbers, each triad is a color
		String[] values = s.split("\\s+");
		ArrayList<Color> colorList = new ArrayList<Color>();
		for (int i = 0; i+2 < values.length; i += 3) {
			colorList.add(new Color(Integer.parseInt(values[i]), Integer.parseInt(values[i+1]), Integer.parseInt(values[i+2])));
		}
		ColorPalette c = new ColorPalette();
		Color[] colorArr = new Color[colorList.size()];
		for (int i = 0; i < colorArr.length; i++) colorArr[i] = colorList.get(i);
		c.setColors(colorArr);
		return c;
	}
	
	public void savePalette() { 
		for (ColorPalette c : comboIndexToPalette) { //for some reason my thing bugs out so this is super hard and complicated and annoying
			boolean same = true;
			for (int i = 0; i < c.getColors().length; i++) {
				if (c.getColors().length != C.getColors().length) { same = false; break; }
				if (!(c.getColors()[i].getRed() == C.getColors()[i].getRed() && c.getColors()[i].getGreen() == C.getColors()[i].getGreen() && c.getColors()[i].getBlue() == C.getColors()[i].getBlue()))
					same = false;
			}
			if (same) return;
		}	
		
		String s = "";
		for (Color c : C.getColors()) s += c.getRed() + " " + c.getGreen() + " " + c.getBlue() + " ";
		String[] lines = {};
	    try {
	    	FileWriter writer = new FileWriter(new File("savedPalettes.txt"), true); 
	    	writer.append(s + '\n'); 
			writer.close();
			//gotta redo array as well
			ArrayList<String> temp = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(new File("savedPalettes.txt")));
			String values;
		    while ((values = br.readLine()) != null) temp.add(values);
		    lines = new String[temp.size()];
		    for (int j = 0; j < lines.length; j++) lines[j] = temp.get(j);	    
		} catch (IOException e) { e.printStackTrace(); }   
	  
		palettePanels = new palettePanel[lines.length];
		comboIndexToPalette = new ColorPalette[lines.length];	
		for (int i = 0; i < lines.length; i++) {
			comboIndexToPalette[i] = savedStringToPalette(lines[i]);
			palettePanels[i] = new palettePanel(comboIndexToPalette[i]);
		}
		favoritesCB = new JComboBox(palettePanels);
		customRenderer r = new customRenderer();
		favoritesCB.setRenderer(r);
	}
}
