import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View extends JFrame{
	
	private boolean isOpen = false;
	public boolean isOpen() {return isOpen; }
	
	Model M; 
	private ConwayPanel conwayPanel;
	private ColorPaletteFrame colorPaletteFrame;
	private InputFrame inputFrame;
	private DimensionsFrame dimFrame;
	private InfoFrame infoFrame;
	
	private JMenuItem color;
	private JMenuItem dimensions;
	
	private JMenuItem input;
	private JMenuItem CARules;
	
	private JMenuItem conwayExplain;
	private JMenuItem inputExplain;
	private JMenuItem controls;
	
	public ConwayPanel getConwayPanel() {return this.conwayPanel;}
	public ColorPaletteFrame getColorPaletteFrame() {return this.colorPaletteFrame;}
	public InputFrame getInputFrame() {return this.inputFrame; }
	public DimensionsFrame getDimFrame() {return this.dimFrame; }
	public InfoFrame getInfoFrame() {return this.infoFrame; }
	
	public View (Model m) {
		super("Cellular Automata");
		
		this.M = m;
		this.conwayPanel = new ConwayPanel(M);
		this.colorPaletteFrame = new ColorPaletteFrame(M.getColorPalette());
		this.inputFrame = new InputFrame(M.getInput());
		this.dimFrame = new DimensionsFrame(M.getRows(), M.getColumns());
		this.infoFrame = new InfoFrame();
		
		setContentPane(conwayPanel);
	    
	    JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu appearance = new JMenu("Appearance");
        menuBar.add(appearance);
        color = new JMenuItem("Color Palette");
        appearance.add(color);
        dimensions = new JMenuItem("Dimensions");
        appearance.add(dimensions);
        
        JMenu settings = new JMenu("Settings");
        menuBar.add(settings);
        input = new JMenuItem("Input");
        settings.add(input);
        CARules = new JMenuItem("Change CA Rule");
       // settings.add(CARules);
        
        JMenu info = new JMenu("Info");
        menuBar.add(info);
        conwayExplain = new JMenuItem("Conway's Game of Life");
        info.add(conwayExplain);
        inputExplain = new JMenuItem("Simpler CA Input");
        info.add(inputExplain);
        controls = new JMenuItem("Controls");
        info.add(controls);
	}
	
	public void open() { 
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setSize(M.getCellSize()*M.getColumns(), M.getCellSize()*M.getRows() + 30);
	    setLocationRelativeTo(null); //i read that this works on stackoverflow... i hope it works on your computer lol
	    setVisible(true);
	    isOpen = true;
	    M.initGrid();
	}
	
	public void registerActionListeners(ActionListener l) {
		color.addActionListener(l);
		dimensions.addActionListener(l);
		
		input.addActionListener(l);
		CARules.addActionListener(l);
		
		conwayExplain.addActionListener(l);
		inputExplain.addActionListener(l);
		controls.addActionListener(l);
	}
}
