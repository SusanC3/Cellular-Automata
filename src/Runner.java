import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class Runner {
	
	public static void main(String[] args) throws IOException {
		Model m = new Model(150, 200, 5, new Input(), new ColorPalette(), new Rule());
		View v = new View(m);
		Controller c = new Controller(m, v);
		File f = new File("shownInstructions.txt");
		//the below is so the instructions don't show up at the beginning every time
		if (f.createNewFile()) {
			v.getInfoFrame().displayConwayInfo();
		}
		else v.open(); 
	}
	
}
