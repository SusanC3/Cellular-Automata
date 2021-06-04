import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View extends JFrame{
	
	private boolean isOpen = false;
	public boolean isOpen() {return isOpen; }
		
	Model M; 
	ColorPalette palette;
	private ConwayPanel conwayPanel;
	private SandboxPanel sandboxPanel;
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
	private JMenuItem sandboxExplain;
	private JMenuItem controls;
	
	private JMenuItem goSandboxMode;
	private JMenuItem clearGrid;
	private JMenuItem fastGrowEx;
	private JMenuItem gliderEx;
	
	
	public ConwayPanel getConwayPanel() {return this.conwayPanel;}
	public SandboxPanel getSandboxPanel() {return this.sandboxPanel;}
	public ColorPaletteFrame getColorPaletteFrame() {return this.colorPaletteFrame;}
	public InputFrame getInputFrame() {return this.inputFrame; }
	public DimensionsFrame getDimFrame() {return this.dimFrame; }
	public InfoFrame getInfoFrame() {return this.infoFrame; }
	
	public void setSandboxModeMenuText(String s) { 
		goSandboxMode.setText(s);
		goSandboxMode.setActionCommand(s);
	}
	
	public View (Model m) {
		super("Cellular Automata");
		
		this.M = m;
		this.palette = M.getColorPalette();
		this.conwayPanel = new ConwayPanel(M);
		this.sandboxPanel = new SandboxPanel(M);
		this.colorPaletteFrame = new ColorPaletteFrame(M.getColorPalette());
		this.inputFrame = new InputFrame(M.getInput());
		this.dimFrame = new DimensionsFrame(M.getRows(), M.getColumns(), M.getCellSize());
		this.infoFrame = new InfoFrame();
		
	    
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
       // settings.add(CARules); //for the future :3
        
        JMenu info = new JMenu("Info");
        menuBar.add(info);
        conwayExplain = new JMenuItem("Conway's Game of Life");
        info.add(conwayExplain);
        inputExplain = new JMenuItem("Simpler CA Input");
        info.add(inputExplain);
        sandboxExplain = new JMenuItem("Sandbox Mode");
        info.add(sandboxExplain);
        controls = new JMenuItem("Controls");
        info.add(controls);
        
        JMenu sandbox = new JMenu("Sandbox Mode");
        menuBar.add(sandbox);
        goSandboxMode = new JMenuItem("Enter Sandbox Mode");
        sandbox.add(goSandboxMode);
        clearGrid = new JMenuItem("Clear Grid");
        sandbox.add(clearGrid);
        fastGrowEx = new JMenuItem("Fast Growing Example");
        sandbox.add(fastGrowEx);
        gliderEx = new JMenuItem("Glider Gun Example");
        sandbox.add(gliderEx);   
        
        JMenu pauseMessage = new JMenu("Remember to hit the space bar to pause/play!");
        menuBar.add(pauseMessage);
	}
	
	public void open() { 
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    enterGenerationMode();
	    setLocationRelativeTo(null); //i read that this works on stackoverflow... i hope it works on your computer lol
	    setVisible(true);
	    isOpen = true;
	}
	
	public void enterGenerationMode() {
		conwayPanel.setPaused(false);
		palette.setColors(new Color[] {new Color(255, 255, 255), new Color(86, 207, 225), 
				new Color(78, 168, 222), new Color(94, 96, 206), new Color(116, 0, 184), new Color(0, 0, 0)});
		palette.setCycleLength(100);
		M.setInSandboxMode(false);
		setContentPane(conwayPanel);
		validate();
		pack();
		M.setCellSize(5);
		setSize(M.getCellSize()*M.getColumns(), M.getCellSize()*M.getRows() + 30);
		setSandboxModeMenuText("Enter Sandbox Mode");
		M.initGrid();
	}
	
	public void enterSandboxMode() {
		palette.setColors(new Color[] {Color.WHITE, Color.BLACK});
		palette.setCycleLength(2);
		M.setInSandboxMode(true);
		setContentPane(sandboxPanel);
		validate();
		pack();
		M.setCellSize(10);
		setSize(M.getCellSize()*M.getColumns()/2, M.getCellSize()*M.getRows()/2 + 30);
		setSandboxModeMenuText("Exit Sandbox Mode");
		getSandboxPanel().open();
	}
	
	public void registerActionListeners(ActionListener l) {
		color.addActionListener(l);
		dimensions.addActionListener(l);

		input.addActionListener(l);
		CARules.addActionListener(l);
		
		conwayExplain.addActionListener(l);
		inputExplain.addActionListener(l);
		sandboxExplain.addActionListener(l);
		controls.addActionListener(l);
		
		goSandboxMode.addActionListener(l);
		clearGrid.addActionListener(l);
		fastGrowEx.addActionListener(l);
		gliderEx.addActionListener(l);
	}
}
