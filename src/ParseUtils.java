/*
 * For simple parsing. This util class can parse y/n, etc.
 */
public class ParseUtils {
	
	/*
	 * check if the response was a yes
	 * If not a yes, returns a false, else returns true
	 */
	public static boolean isYes(String str) {
		String s = str.toLowerCase();
		if(s.equals("yes") || s.equals("y")) {
			return true;
		}
		return false;
	}

}
