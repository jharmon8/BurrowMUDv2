import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class RoomFileUtils {
	private static String filepath = "C:/Users/Jacob/Documents/GitHub/BurrowMUDv2/rooms/";
	
	/*
	 * checks if this file is in room folder
	 */
	public static boolean doesRoomExist(String name) {
		if(name == null) {return false;}
		
		File f = new File(filepath + name + ".txt");
		return f.exists() && !f.isDirectory();
	}

	/*
	 * Gets a users .txt and returns all of the info
	 * 
	 * returns null on an error (or inexistent character)
	 */
	public static String[] readRoom(String name) {
		if(name == null) return null;
		
		ArrayList<String> input = new ArrayList<String>();
		
		if(doesRoomExist(name)) {
			try {
				FileReader fr = new FileReader(filepath + name + ".txt");
				BufferedReader br = new BufferedReader(fr);
				
				String line = null;
				while((line = br.readLine()) != null) {
					if(line.charAt(0) == BurrowDriver.parseChar) {
						input.add(line);
					} else {
						input.get(input.size()-1).concat("\n" + line);
					}
				}
				
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
		
		// TODO: Put some more error checking here
		String[] output = new String[input.size()];
		return input.toArray(output);
	}
}
