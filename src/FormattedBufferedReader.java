import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * Pretty much just a workaround for the backspace problem 
 *    (Telnet clients sending 0/8 for backspace, etc.)
 */
public class FormattedBufferedReader extends BufferedReader {

	public FormattedBufferedReader(InputStreamReader inputStreamReader) {super(inputStreamReader);}
	
	@Override
	public String readLine() throws IOException {
		String in = null;
		try {
			in = super.readLine();
		} catch(SocketTimeoutException e) {
			System.out.println("Socket timeout.");
			return null;
		}
		
		if(in == null) {return null;}
		
		String pattern = "([^])";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(in);
		
		while(m.find()) {
			in = in.replaceAll(pattern, "");
			m = r.matcher(in);
		}
		
		in = in.replaceAll("", "");
		
		return in;
	}
}
