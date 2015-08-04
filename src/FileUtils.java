import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 * A bunch of static methods for reading and writing to very generic files.
 * For reading/writing players, see UserFileUtils
 */
public class FileUtils {

	
	/*
	 * A slow but easy way to read a text file. Used for intro splash, etc.
	 */
	public static String TxtToString(String filename) {
		ArrayList<String> rawIn = new ArrayList<String>();
		
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			while ((line = br.readLine()) != null) {
				rawIn.add(line);
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int numlines = rawIn.size();
		String output = "";
		
		for (int i = 0; i < numlines; i++) {
			String s = rawIn.get(i);
			
			output = output.concat(s);
		}

		return output;
	}
	
	/*
	 * Takes an output stream and outputs a file line by line on it.
	 */
	public static void OutLineByLine(String filename, FormattedPrintWriter out) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			while ((line = br.readLine()) != null) {
				out.println(line, false);
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
