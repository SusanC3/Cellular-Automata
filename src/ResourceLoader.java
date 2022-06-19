//load function from https://www.youtube.com/watch?v=rCoed3MKpEA

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

final public class ResourceLoader {
	
	public static InputStream load(String path) {
		InputStream input = ResourceLoader.class.getResourceAsStream(path);
		if (input == null) {
			input = ResourceLoader.class.getResourceAsStream("/"+path);
		}
		return input;
	}
	
	//files in jars are read-only. gotta put the files in the folder
}