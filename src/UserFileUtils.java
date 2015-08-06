import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/*
 * Decided that reading/writing users was important enough to have it's own file.
 * Gotta make sure this one is really robust.
 */
public class UserFileUtils {
	private static String filepath = "C:/Users/Jacob/Documents/GitHub/BurrowMUDv2/users/";
//	private static String filepath = "users/";

	/*
	 * checks if this file is in users folder
	 */
	public static boolean doesPlayerExist(String username) {
		if(username == null) {return false;}
		
		File f = new File(filepath + username + ".txt");
		return f.exists() && !f.isDirectory();
	}
	
	/*
	 * ensures the name is alphanumeric, etc
	 */
	public static boolean isUsernameValid(String username) {
		if(username == null) {return false;}
		
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
		if(password == null) {return false;}

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
		if(username == null) {return false;}
		if(password == null) {return false;}
		
		try {
			FileReader fr = new FileReader(filepath + username + ".txt");
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
	
	/*
	 * Character creation. Return true if successful
	 */
	public static boolean createCharacter(String username, FormattedPrintWriter out, FormattedBufferedReader in) {
		if(username == null) return false;
		
		String password = "";
		String gender = "";
		int[] inventory = new int[2];
		int[] quest = new int[1];
		
		out.setFormat(true);
		String input = "";
		
		////
		// Password
		////
		while(true) {
			out.println("Please create a password.");
			out.print("Password: ");
			out.flush();
			
			try {
				input = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(input == null) return false;
			
			if(!isPasswordValid(input)) {
				out.println("Passwords must be alphanumeric and be between 6 and 12 characters.");
				continue;
			}
			
			password = input;
			out.println("Please confirm password.");
			out.print("Password: ");
			out.flush();
			
			try {
				input = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(input == null) return false;
			
			if(!input.equals(password)) {
				out.println("Passwords didn't match.");
				continue;
			}
			
			break;
		}
		
		if(input == null) return false;
		password = input;
		
		////
		// Boy/Girl
		////
		while(true) {
			out.println("Are you a boy or a girl? (or other)");
			out.print("Gender: ");
			out.flush();
			
			try {
				input = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(input == null) return false;
			gender = ParseUtils.parseGender(input);
			if(gender == null) {
				out.println("Couldn't parse. Please enter boy or girl (or other).");
				continue;
			}
			
			break;
		}
		
		if(gender == null) return false;
		
		////
		// Inventory
		////
		String color = "";
		String toy = "";
		
		while(true) {
			out.println("What is your favorite color?");
			out.print("Color: ");
			out.flush();
			
			try {
				input = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(input == null) return false;
			color = ParseUtils.parseColor(input);
			if(color == null) {
				out.println("Couldn't parse. Pick a more normal color!");
				continue;
			}
			
			break;
		}
		
		if(color == null) return false;
		
		/*toy stuff goes here*/
		toy = "Ball";
		
		// TODO: lkajeal;kj
		// We'll figure out inventory later
		
		inventory[0] = 1;
		inventory[1] = 1;
		quest[0] = 1;
		
		return makeNewCharacter(username, password, color, gender, out);
	}
	
	/*
	 * Does the busywork of writing a new character
	 */
	private static boolean makeNewCharacter(String username, String password,
											String color, String gender, FormattedPrintWriter out) {
		int[] stats = new int[BurrowDriver.NUMSTATS];
		int[] inventory = new int[2];
		int[] questFlags = new int[1];
		
		for(int i = 0; i < stats.length; i++) stats[i] = 1;
		for(int i = 0; i < inventory.length; i++) inventory[i] = 1;
		questFlags[0] = 1;
		
		return writeCharacter(username, password, 1, 0, stats, 0, inventory, gender, questFlags, out);
	}

	/*
	 * Updates/Creates the file for a character
	 * 
	 * If password is null, then the password is not changed
	 * If level is -1, the level is not changed
	 * If exp is -1, the exp is not changed
	 * If stats is null, the stats are not changed
	 * If coins is -1, the coin is not changed
	 * If inventory is null, then inventory is not changed
	 * If gender is null, then gender is not changed
	 * If quest flags is null, then the quest flags are not changed
	 * 
	 * returns true if successful
	 */
	public static boolean writeCharacter(String username, String password, int level, int exp, int[] stats, 
											int coins, int[] inventory, String gender, int[] questFlags, FormattedPrintWriter out) {
		
		// TODO: This method needs cleanup
		
		String[] output = new String[BurrowDriver.USER_LINES];
		output[0] = "#4";
		output[1] = username;
		
		if(doesPlayerExist(username)) {
			String[] input = readCharacter(username);
			if(input == null) return false;
			
			// The easy ones
			output[2] = password == null ? input[2] : password;
			output[3] = level    == -1   ? input[3] : "" + level;
			output[4] = exp      == -1   ? input[4] : "" + exp;
			output[6] = coins    == -1   ? input[6] : "" + coins;
			output[9] = gender   == null ? input[9] : gender;
			
			// The arrays are a little trickier
			if(stats != null) {
				String str = "";
				for(int i : stats) str += i + " ";
				output[5] = str;
			} else {output[5] = input[5];}
			
			if(inventory != null) {
				String str = "";
				for(int i : inventory) str += i + " ";
				output[7] = str;
			} else {output[7] = input[7];}
			
			if(inventory != null) {
				String str = "";
				for(int i : questFlags) str += i + " ";
				output[9] = str;
			} else {output[9] = input[9];}
		} else {
			if(password == null || stats == null || inventory == null || questFlags == null) {
				System.out.println("Failed write character for " + username);
				return false;
			}
			
			// The easy ones
			output[2] = password;
			output[3] = level + "";
			output[4] = exp + "";
			output[6] = coins + "";
			output[8] = gender;
			
			// Do the arrays real quick
			String str = "";
			for(int i : stats) str += i + " ";
			output[5] = str;
			
			str = "";
			for(int i : inventory) str += i + " ";
			output[7] = str;

			str = "";
			for(int i : questFlags) str += i + " ";
			output[9] = str;
		}
		
		try {
			// Replace the old user with this new file (or make a new file)
			if(doesPlayerExist(username)) {
				out.println("Someone created this user while you were in character creation. Weird!");
				return false;
			}
			
			FileWriter fw = new FileWriter(new File(filepath + username + ".txt"), false);
			for(String s : output) {
				fw.write(s + "\n");
			}
			fw.close();
			
			System.out.println("New user created: " + username);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/*
	 * Gets a users .txt and returns all of the info
	 * 
	 * returns null on an error (or inexistent character)
	 */
	public static String[] readCharacter(String username) {
		if(username == null) return null;
		
		ArrayList<String> input = new ArrayList<String>();
		
		if(doesPlayerExist(username)) {
			try {
				FileReader fr = new FileReader(filepath + username + ".txt");
				BufferedReader br = new BufferedReader(fr);
				
				String line = null;
				while((line = br.readLine()) != null) {
					input.add(line);
				}
				
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
		
		if(input.size() != 0 && input.size() != BurrowDriver.USER_LINES) {
			System.out.println("Error reading user: " + username);
			System.out.println("\tCheck size of the file at users/" + username + ".txt");
		}
		
		// TODO: Put some more error checking here
		String[] output = new String[input.size()];
		return input.toArray(output);
	}
}
