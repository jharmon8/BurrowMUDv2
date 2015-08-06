import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * A lot of static methods to make networking stuff prettier.
 */
public class NetworkUtils {
	
	/*
	 * Handles logging a player on for network manager.
	 * Will also create a player if appropriate.
	 * 
	 * Returns null if the player does not log on, at which point the NetworkManager 
	 *   should terminate the connection.
	 */
	public static String login(FormattedBufferedReader in, FormattedPrintWriter out) {
		try {
			while(true) {
				out.setFormat(true);
				
				out.println("What is your username?");
				out.println("\t(new users can enter whatever they'd like!)", false);
				
				out.print("Username: ");
				out.flush();
				
				// get username
				String username = in.readLine();
				if(username == null) return null;
				
				// if it's valid
				if(!UserFileUtils.isUsernameValid(username)) {
					out.println("Usernames must be alphanumeric and be between 6 and 12 characters.");
					continue;
				}
				
				// if it exists
				if(UserFileUtils.doesPlayerExist(username)) {
					int attempts = 5;
					
					// Ask for a password
					while(attempts > 0) {
						out.print("Password: ");
						out.flush();
						String password = in.readLine();
						
						if(!UserFileUtils.isPasswordValid(password)) {
							out.println("Passwords must be alphanumeric and be between 6 and 12 characters.");
							continue;
						}
						
						if(!UserFileUtils.isPasswordCorrect(username, password)) {
							out.println("Incorrect password.");
							attempts--;
							continue;
						}
						
						// Valid login! Return username
						out.println("Welcome back, " + username + "!");
						return username;
					}
				}
				
				// At this point, the player is new, so character creation should start
				out.println("Character does not exist. Would you like to create it? (y/n)");
				String yn = in.readLine();
				if(!ParseUtils.isYes(yn)) {
					continue;
				}
				
				// If the character creation succeeds, return username
				if(UserFileUtils.createCharacter(username, out, in)) {
					out.println("Welcome to Burrow, " + username + "!");
					return username;
				}
				
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
