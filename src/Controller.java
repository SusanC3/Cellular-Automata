import java.awt.event.*;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Controller implements KeyListener, ActionListener, MouseListener{
	private Model M;
	private View V;
	private ConwayPanel conwayPanel;
	private SandboxPanel sandboxPanel;
	private ColorPaletteFrame colorPaletteFrame;
	private InputFrame inputFrame;
	private DimensionsFrame dimFrame;
	private InfoFrame infoFrame;
	
	public Controller(Model m, View v) {
		this.M = m;
		this.V = v;
		
		V.registerActionListeners(this);
		
		this.conwayPanel = V.getConwayPanel();
		conwayPanel.addKeyListener(this);
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
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
			if (M.inSandboxMode()) sandboxPanel.setPaused(!sandboxPanel.getPaused());
			else conwayPanel.setPaused(!conwayPanel.getPaused());
		}
		if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
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
		if (conwayPanel.getPaused() == false) conwayPanel.setPaused(true);
		String command = evt.getActionCommand();
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
		if (command.equals("numColorsPicked")) colorPaletteFrame.pickColorsSetup();
		if (command.equals("color picked rgb")) {
			colorPaletteFrame.setNextColor(colorPaletteFrame.getRGBColor());
			colorPaletteFrame.pickColor(); 
		}
		if (command.equals("color picked chooser")) {
			colorPaletteFrame.setNextColor(colorPaletteFrame.getChooserColor());
			colorPaletteFrame.pickColor(); 
		}
			
		if (command.equals("rgb")) colorPaletteFrame.pickRGBColor();
		if (command.equals("colorChooser")) colorPaletteFrame.pickColorChooser();
		
		if (command.equals("Input")) inputFrame.open();
		if (command.equals("input entered")) {
			if (inputFrame.setInput()) {
				M.initGrid();
				conwayPanel.setPaused(false);
				conwayPanel.repaint();
			}
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
		
		if (command.equals("Go to simulation")) {
			infoFrame.dispatchEvent(new WindowEvent(infoFrame, WindowEvent.WINDOW_CLOSING));
			V.open();
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
