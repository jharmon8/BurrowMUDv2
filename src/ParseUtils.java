/*
 * For simple parsing. This util class can parse y/n, etc.
 */
public class ParseUtils {
	
	/*
	 * check if the response was a yes
	 * If not a yes, returns a false, else returns true
	 */
	public static boolean isYes(String str) {
		if(str==null) {return false;}
		
		String s = str.toLowerCase();
		if(s.equals("yes") || s.equals("y")) {
			return true;
		}
		return false;
	}

	/*
	 * check if the response is a boy or a girl (or something else valid)
	 * 
	 * returns null on an error
	 */
	public static String parseGender(String str) {
		if(str==null) {return null;}
		
		String s = str.toLowerCase();
		if(s.equals("unspecified") || s.equals("u")) {
			return "Unspecified";
		}
		
		if(s.equals("boy") || s.equals("b")) {
			return "Boy";
		}
		
		if(s.equals("girl") || s.equals("g")) {
			return "Girl";
		}
		
		if(s.equals("other") || s.equals("o")) {
			return "Other";
		}
		
		return null;
	}
	
	/*
	 * check if the response is a color
	 * 
	 * returns null on an error
	 */
	public static String parseColor(String str) {
		// TODO: Make this a text file
		if(str==null) {return null;}
		
		String s = str.toLowerCase();
		if(s.equals("red")) {
			return "Red";
		}
		
		if(s.equals("orange")) {
			return "Red";
		}
		
		if(s.equals("yellow")) {
			return "Red";
		}
		
		if(s.equals("green")) {
			return "Red";
		}
		
		if(s.equals("blue")) {
			return "Red";
		}
		
		if(s.equals("purple")) {
			return "Red";
		}
		
		if(s.equals("white")) {
			return "Red";
		}
		
		if(s.equals("black")) {
			return "Red";
		}
		
		if(s.equals("grey")) {
			return "Red";
		}
		
		if(s.equals("pink")) {
			return "Red";
		}
		
		if(s.equals("rainbow")) {
			return "Red";
		}
		
		return null;
	}
}
