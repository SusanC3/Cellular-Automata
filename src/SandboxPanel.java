import java.awt.*;
import javax.swing.*;

public class SandboxPanel extends ConwayPanel{

	public SandboxPanel(Model m) {
		super(m);
	}
	
	public void open() {
		paused = true;
		M.setInSandboxMode(true);
		M.initGrid();
		M.clearGrid(0);
	}


}
