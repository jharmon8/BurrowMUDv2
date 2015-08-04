import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/*
 * Decided that reading/writing users was important enough to have it's own file.
 * Gotta make sure this one is really robust.
 */
public class UserFileUtils {
	
	/*
	 * checks if this file is in users folder
	 */
	public static boolean doesPlayerExist(String username) {
		File f = new File(username + ".txt");
		return f.exists() && !f.isDirectory();
	}
	
	/*
	 * ensures the name is alphanumeric, etc
	 */
	public static boolean isUsernameValid(String username) {
		// Check if alphanumeric
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		if(p.matcher(username).find()) {return false;}
		
		// Check if between 6 and 12 characters
		if(!(username.length() >= 6 && username.length() <= 12)) {return false;}
		
		
		return true;
	}
	
	/*
	 * ensures the password is a valid password
	 */
	public static boolean isPasswordValid(String password) {
		// Check if alphanumeric
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		if(p.matcher(password).find()) {return false;}
		
		// Check if between 6 and 12 characters
		if(!(password.length() >= 6 && password.length() <= 12)) {return false;}
		
		
		return true;
	}
	
	/*
	 * checks a user entered the correct password
	 */
	public static boolean isPasswordCorrect(String username, String password) {
		try {
			FileReader fr = new FileReader(username + ".txt");
			BufferedReader br = new BufferedReader(fr);
			
			// Get 3rd line
			String line = "";
			for (int i = 0; i < 3; i++) {
				line = br.readLine();
			}
			
			if(line.equals(password)) {
				return true;
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
