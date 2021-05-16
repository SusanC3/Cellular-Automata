import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class Runner {
	
	public static void main(String[] args) throws IOException {
		Model m = new Model(150, 200, 5, new Input(), new ColorPalette());
		//190, 350 for larger size
		View v = new View(m);
		Controller c = new Controller(m, v);
		File f = new File("f.txt");
		v.getInfoFrame().displayConwayInfo();
	/*	if (f.createNewFile()) {
			v.getInfoFrame().displayConwayInfo();
		}
		else v.open(); */
	}
	/* Good inputs
	 * 01000101 (69)
	 * 		but tbh 00100111 is better
	 * 10101011 is kinda nice
	 * 10000001 cause triangles
	 * 01001011 maybe i'm too attatched but there was this nice butterfly pattern like thing on the right after a bit
	 * 11011011 the input is really symetrical & it reaches stablity quickly
	 * 00011110 (30) is an exception to the twin peaks but it is still interesting
	 */
}
