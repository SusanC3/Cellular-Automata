import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class View extends JFrame{
	
	private boolean isOpen = false;
	public boolean isOpen() {return isOpen; }
		
	Model M; 
	ColorPalette palette;
	private Rule rule;
	private ConwayPanel conwayPanel;
	private ControlPanel controlPanel;
	private SandboxPanel sandboxPanel;
	private ColorPaletteFrame colorPaletteFrame;
	private InputFrame inputFrame;
	private DimensionsFrame dimFrame;
	private InfoFrame infoFrame;
	private RuleFrame ruleFrame;
	
	private JMenuItem saveCurrConfig;
	private JMenuItem saveInitConfig;
	private JMenuItem chooseConfig;
	private JMenuItem savePalette;
	private JMenuItem saveInput;
	private JMenuItem saveRule;
	
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
	public ControlPanel getControlPanel() {return this.controlPanel;}
	public SandboxPanel getSandboxPanel() {return this.sandboxPanel;}
	public ColorPaletteFrame getColorPaletteFrame() {return this.colorPaletteFrame;}
	public InputFrame getInputFrame() {return this.inputFrame; }
	public DimensionsFrame getDimFrame() {return this.dimFrame; }
	public InfoFrame getInfoFrame() {return this.infoFrame; }
	public RuleFrame getRuleFrame() {return this.ruleFrame; }
	
	public void setSandboxModeMenuText(String s) { 
		goSandboxMode.setText(s);
		goSandboxMode.setActionCommand(s);
	}
	
	public View (Model m) {
		super("Cellular Automata");
		setLayout(new BorderLayout());
		
		this.M = m;
		this.palette = M.getColorPalette();
		this.rule = M.getRule();
		this.conwayPanel = new ConwayPanel(M);
		this.controlPanel = new ControlPanel();
		this.sandboxPanel = new SandboxPanel(M);
		this.colorPaletteFrame = new ColorPaletteFrame(M.getColorPalette());
		this.inputFrame = new InputFrame(M.getInput());
		this.dimFrame = new DimensionsFrame(M.getRows(), M.getColumns(), M.getCellSize());
		this.infoFrame = new InfoFrame();
		this.ruleFrame = new RuleFrame(rule);
		
	    
	    JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu save = new JMenu("Save");
        menuBar.add(save);
        saveCurrConfig = new JMenuItem("Save current configuration");
    //    save.add(saveCurrConfig);
        saveInitConfig = new JMenuItem("Save initial configuration");
   //     save.add(saveInitConfig);
        chooseConfig = new JMenuItem("Choose Saved Configuration");
    //    save.add(chooseConfig); 
        savePalette = new JMenuItem("Save color palette");
        save.add(savePalette);
        saveInput = new JMenuItem("Save input rule");
        save.add(saveInput);
        saveRule = new JMenuItem("Save CA rule");
        save.add(saveRule);
        
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
        settings.add(CARules); //for the future :3
        
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
        
	}
	
	public void open() { 
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    enterGenerationMode();
	    add(controlPanel, BorderLayout.SOUTH);
	    setLocationRelativeTo(null); 
	    setVisible(true);
	    isOpen = true;
	}
	
	public void enterGenerationMode() {
		
		conwayPanel.setPaused(false);
		palette.setColors(new Color[] {new Color(255, 255, 255), new Color(86, 207, 225), 
				new Color(78, 168, 222), new Color(94, 96, 206), new Color(116, 0, 184), new Color(0, 0, 0)});
		palette.setCycleLength(100);
		M.setInSandboxMode(false);
		remove(sandboxPanel);
		add(conwayPanel, BorderLayout.CENTER);
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
		remove(conwayPanel);
		add(sandboxPanel, BorderLayout.CENTER);
		validate();
		pack();
		M.setCellSize(10);
		setSize(M.getCellSize()*M.getColumns()/2, M.getCellSize()*M.getRows()/2 + 30);
		setSandboxModeMenuText("Exit Sandbox Mode");
		getSandboxPanel().open();
	}
	
	public void registerActionListeners(ActionListener l) {
		saveCurrConfig.addActionListener(l);
		saveInitConfig.addActionListener(l);
		chooseConfig.addActionListener(l);
		savePalette.addActionListener(l);
		saveInput.addActionListener(l);
		saveRule.addActionListener(l);
		
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
