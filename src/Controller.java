import java.awt.event.*;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Controller implements KeyListener, ActionListener{
	private Model M;
	private View V;
	private ConwayPanel conwayPanel;
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

		//action listener for color palette frame stuff?
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
			conwayPanel.setPaused(!conwayPanel.getPaused());
		}
		if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
			M.doConwayCycle(M.getInput());
		}
		
		conwayPanel.repaint();
        conwayPanel.requestFocusInWindow();	
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		conwayPanel.setPaused(true);
		String command = evt.getActionCommand();
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
			inputFrame.setInput();
			M.initGrid();
			conwayPanel.repaint();
		}
		if (command.equals("random gen")) {
			inputFrame.backToRandom();
			M.initGrid();
			conwayPanel.repaint();
		}
		
		if (command.equals("Dimensions")) dimFrame.open();
		if (command.equals("dimensions entered")) {
			while (dimFrame.checkForError()) continue; //keep doing it until no error
			M.setDimensions(dimFrame.getRows(), dimFrame.getColumns());
			V.setSize(M.getCellSize()*M.getColumns(), M.getCellSize()*M.getRows() + 30);
			M.initGrid();
		}
		
		if (command.equals("Conway's Game of Life")) infoFrame.displayConwayInfo();
		if (command.equals("Simpler CA Input")) infoFrame.displayInputInfo();
		if (command.equals("Controls")) infoFrame.displayControlsInfo();
		
		if (command.equals("Go to simulation")) {
			if (V.isOpen()) {
				infoFrame.dispatchEvent(new WindowEvent(infoFrame, WindowEvent.WINDOW_CLOSING));
			}
			else V.open();
		}
		
		conwayPanel.requestFocusInWindow();
	}


	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}

}
