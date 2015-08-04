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
	public static String login(BufferedReader in, FormattedPrintWriter out) {
		try {
			while(true) {
				out.setFormat(true);
				
				out.println("What is your username?");
				out.println("\t(new users can enter whatever they'd like!)", false);
				
				out.print("Username: ");
				out.flush();
				
				// get username
				String username = in.readLine();
				// if it's valid
				if(!UserFileUtils.isUsernameValid(username)) {
					out.println("Usernames must be alphanumeric and be between 6 and 12 characters.");
					continue;
				}
				
				// if it exists
				if(UserFileUtils.doesPlayerExist(username)) {
					System.out.println("Beep");
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
						out.println("Welcome, " + username + "!");
						return username;
					}
				}
				
				// At this point, the player is new, so character creation should start
				out.println("Character does not exist. Would you like to create it? (y/n)");
				String yn = in.readLine();
				if(!ParseUtils.isYes(yn)) {
					continue;
				}
				
				// client wants to create a character
				// TODO: I want to move this to a character creation class or something
				while(true) {
					out.println("Enter the password for your character.");
					out.print("Password: ");
					out.flush();
					String pass1 = in.readLine();
					if(!UserFileUtils.isPasswordValid(pass1)) {
						out.println("Passwords must be alphanumeric and be between 6 and 12 characters.");
						continue;
					}
					
					out.println("Confirm password for your character.");
					out.print("Password: ");
					out.flush();
					String pass2 = in.readLine();
					if(!pass1.equals(pass2)) {
						out.println("Passwords did not match.");
						continue;
					}
					
					// Gotta make the character here
					return username;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
