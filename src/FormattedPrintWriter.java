import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;

/*
 * This class is a print writer that has some added functionality to format
 *   any messages the server sends when needed.
 */
public class FormattedPrintWriter extends PrintWriter {
	
	private boolean format = false;
	
	public FormattedPrintWriter(OutputStream outputStream, boolean b) {super(outputStream, b);}

	/*
	 * (non-Javadoc)
	 * @see java.io.PrintWriter#println(java.lang.String)
	 * 
	 * Adds formatting to the print writer.
	 * To not format, use setFormat(false)
	 */
	@Override
	public void println(String s) {
		if(format) {
			super.println();
		}
		super.println(s);
		super.flush();
	}
	
	/*
	 * set whether or not 
	 */
	public void setFormat(boolean format) {
		this.format = format;
	}
	
	/*
	 * overrides the format setting and prints with the given format
	 */
	public void println(String s, boolean format) {
		boolean prevformat = this.format;
		this.format = format;
		
		println(s);
		
		this.format = prevformat;
	}
}
