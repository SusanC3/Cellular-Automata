import java.awt.event.*;

import javax.swing.*;

public class Controller implements KeyListener, ActionListener, MouseListener{
	private Model M;
	private View V;
	private ConwayPanel conwayPanel;
	private ControlPanel controlPanel;
	private SandboxPanel sandboxPanel;
	private ColorPaletteFrame colorPaletteFrame;
	private InputFrame inputFrame;
	private DimensionsFrame dimFrame;
	private InfoFrame infoFrame;
	private RuleFrame ruleFrame;
	
	public Controller(Model m, View v) {
		this.M = m;
		this.V = v;
		
		V.registerActionListeners(this);
		
		this.conwayPanel = V.getConwayPanel();
		conwayPanel.addKeyListener(this);
		this.controlPanel = V.getControlPanel();
		controlPanel.registerActionListeners(this);
		this.sandboxPanel = V.getSandboxPanel();
		sandboxPanel.addKeyListener(this); 
		sandboxPanel.addMouseListener(this);

		this.colorPaletteFrame = V.getColorPaletteFrame();
		colorPaletteFrame.registerActionListeners(this);
		
		this.inputFrame = V.getInputFrame();
		inputFrame.registerActionListeners(this);
		
		this.dimFrame = V.getDimFrame();
		dimFrame.registerActionListeners(this);
		
		this.infoFrame = V.getInfoFrame();
		infoFrame.registerActionListeners(this);
		
		this.ruleFrame = V.getRuleFrame();
		ruleFrame.registerActionListeners(this); 
		
		inputFrame.getSaveFrame().registerokBActionListener(this);
		colorPaletteFrame.getSaveFrame().registerokBActionListener(this);
		ruleFrame.getSaveFrame().registerokBActionListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
			if (M.inSandboxMode()) {
				sandboxPanel.setPaused(!sandboxPanel.getPaused());
				if (sandboxPanel.getPaused()) controlPanel.getPauseB().setText("Play");
				else controlPanel.getPauseB().setText("Pause");
			}
			else {
				conwayPanel.setPaused(!conwayPanel.getPaused());
				if (conwayPanel.getPaused()) controlPanel.getPauseB().setText("Play");
				else controlPanel.getPauseB().setText("Pause");
			}
			
		}
		if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
			conwayPanel.setPaused(true);
			controlPanel.getPauseB().setText("Play");
			M.doConwayCycle(M.getInput());
		}
		
		if (M.inSandboxMode()) {
			sandboxPanel.repaint();
			sandboxPanel.requestFocusInWindow();	
		}
		else {
			conwayPanel.repaint();
			conwayPanel.requestFocusInWindow();	
		}
       
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		String command = evt.getActionCommand();
		if (conwayPanel.getPaused() == false && !(command.equals("Pause") || command.equals("Play"))) 
			conwayPanel.setPaused(true);
		
		if (command.contains("nothing to save")) {
			if (command.contains("input")) inputFrame.getSaveFrame().dispatchEvent(new WindowEvent(inputFrame.getSaveFrame(), WindowEvent.WINDOW_CLOSING));
		}
		if (command.equals("Save input rule")) {
			if (inputFrame.getInput().getBinary() == null) 
				inputFrame.getSaveFrame().setCurrState(null);
			else {
				JPanel j = new JPanel();
				j.add(new JLabel(inputFrame.getInput().getBinary()));
				inputFrame.getSaveFrame().setCurrState(j);
			}
			inputFrame.getSaveFrame().open();				
		}
		if (command.equals("input saved")) {
			inputFrame.saveInput();
			inputFrame.getSaveFrame().dispatchEvent(new WindowEvent(inputFrame.getSaveFrame(), WindowEvent.WINDOW_CLOSING));
		}
		if (command.equals("Save color palette")) {
			colorPaletteFrame.getSaveFrame().setCurrState(colorPaletteFrame.getCurrPalettePanel());
			colorPaletteFrame.getSaveFrame().open();
		}
		if (command.equals("color palette saved")) {
			colorPaletteFrame.savePalette();
			colorPaletteFrame.getSaveFrame().dispatchEvent(new WindowEvent(colorPaletteFrame.getSaveFrame(), WindowEvent.WINDOW_CLOSING));
		}
		if (command.equals("Save CA rule")) {
			ruleFrame.getSaveFrame().setCurrState(ruleFrame.getCurrRulePanel());
			ruleFrame.getSaveFrame().open();
		}
		if (command.equals("rule saved")) {
			ruleFrame.saveRule();
			ruleFrame.getSaveFrame().dispatchEvent(new WindowEvent(inputFrame.getSaveFrame(), WindowEvent.WINDOW_CLOSING));
		}
		
		if (command.equals("Pause") || command.equals("Play")) {
			if (M.inSandboxMode()) {
				sandboxPanel.setPaused(!sandboxPanel.getPaused());
				if (sandboxPanel.getPaused()) controlPanel.getPauseB().setText("Play");
				else controlPanel.getPauseB().setText("Pause");
			}
			else {
				conwayPanel.setPaused(!conwayPanel.getPaused());
				if (conwayPanel.getPaused()) controlPanel.getPauseB().setText("Play");
				else controlPanel.getPauseB().setText("Pause");
			}
		}
		if (command.equals("Step Through")) {
			conwayPanel.setPaused(true);
			controlPanel.getPauseB().setText("Play");
			M.doConwayCycle(M.getInput());
		}
		if (command.equals("Reset")) {     
			M.reset();
			V.open();
		}
		
		if (command.equals("Enter Sandbox Mode") || command.equals("Clear Grid")) {
			V.enterSandboxMode();
		}
		if (command.equals("Exit Sandbox Mode")) {
			V.enterGenerationMode();
		}
		if (command.equals("Fast Growing Example")) {
			V.enterSandboxMode();
			M.setupFastGrowExample();			
		}
		if (command.equals("Glider Gun Example")) {
			V.enterSandboxMode();
			M.setupGliderExample();
		}
		if (command.equals("Color Palette")) colorPaletteFrame.open();
		if (command.equals("palette selected")) {
			int index = colorPaletteFrame.getComboIndex();
			ColorPalette c = colorPaletteFrame.getComboIndexToPalette(index);
			colorPaletteFrame.getPalette().setColors(c.getColors());
			colorPaletteFrame.dispatchEvent(new WindowEvent(colorPaletteFrame, WindowEvent.WINDOW_CLOSING));
		}
		if (command.equals("numColorsPicked")) colorPaletteFrame.pickColorsSetup();
		if (command.equals("color picked chooser")) {
			colorPaletteFrame.setNextColor(colorPaletteFrame.getChooserColor());
			colorPaletteFrame.pickColor(); 
		}
			
		if (command.equals("customPalette")) colorPaletteFrame.pickNumColors();
		
		if (command.equals("Input")) inputFrame.open();
		if (command.equals("input entered")) {
			if (inputFrame.setInput()) {
				M.initGrid();
				conwayPanel.setPaused(false);
				conwayPanel.repaint();
			}
		}
		if (command.equals("input selected")) {	
			inputFrame.getInput().setBinary(inputFrame.getComboSelectedItem());
			inputFrame.dispatchEvent(new WindowEvent(inputFrame, WindowEvent.WINDOW_CLOSING));
			M.initGrid();
			conwayPanel.setPaused(false);
			conwayPanel.repaint();
		}
		if (command.equals("random gen")) {
			inputFrame.backToRandom();
			M.initGrid();
			conwayPanel.repaint();
		}
		
		if (command.equals("Dimensions")) dimFrame.open();
		if (command.equals("dimensions entered")) {
			if (!dimFrame.checkForError()) {
				M.setDimensions(dimFrame.getRows(), dimFrame.getColumns());
				M.setCellSize(dimFrame.getCellSize());
				V.setSize(M.getCellSize()*M.getColumns(), M.getCellSize()*M.getRows() + 30);
				M.initGrid();
			}
		}
		
		if (command.equals("Conway's Game of Life")) infoFrame.displayConwayInfo();
		if (command.equals("Simpler CA Input")) infoFrame.displayInputInfo();
		if (command.equals("Sandbox Mode")) infoFrame.displaySandboxInfo();
		if (command.equals("Controls")) infoFrame.displayControlsInfo();
		
		if (command.equals("Go To Simulation")) {
			infoFrame.dispatchEvent(new WindowEvent(infoFrame, WindowEvent.WINDOW_CLOSING));
			V.open();
		}
		if (command.equals("Change CA Rule")) ruleFrame.open();
		if (command.equals("picked rule")) { 
			ruleFrame.selectRule();
			ruleFrame.dispatchEvent(new WindowEvent(ruleFrame, WindowEvent.WINDOW_CLOSING));	
		}
		if (command.equals("Make custom rule")) ruleFrame.pickDieNeighbors();
		if (command.equals("picked die neighbors")) {
			ruleFrame.addDieNeighbors();
			ruleFrame.pickReviveNeighbors();
		}
		if (command.equals("picked revive neighbors")) {
			ruleFrame.addReviveNeighbors();
			ruleFrame.setRules();
			ruleFrame.dispatchEvent(new WindowEvent(ruleFrame, WindowEvent.WINDOW_CLOSING));		
		}
		
		if (M.inSandboxMode()) {
			sandboxPanel.repaint();
			sandboxPanel.requestFocusInWindow();	
		}
		else {
			conwayPanel.repaint();
			conwayPanel.requestFocusInWindow();	
		}
	}


	@Override
	public void mouseClicked(MouseEvent evt) {
		if (M.inSandboxMode()) {
			M.toggleLivingStatus(evt.getX(), evt.getY());
			sandboxPanel.repaint();
			sandboxPanel.requestFocusInWindow();
		}
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}
