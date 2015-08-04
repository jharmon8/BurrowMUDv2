import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * A lot of static methods to make networking stuff prettier.
 */
public class NetworkUtils {
	
	public static String login(BufferedReader in, PrintWriter out) {
		out.println("What is your username?");
		out.print("Username: ");
		out.flush();
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
