import java.io.File;
import java.util.ArrayList;


/*
 * Blah blah
 */
public class World {
	ArrayList<Room> rooms = new ArrayList<Room>();
	ArrayList<Player> players = new ArrayList<Player>();
	
	/*
	 * blah blah
	 */
	public void init() {
		File[] files = new File("rooms").listFiles();
		
		for(File f : files) {
			rooms.add(new Room(f));
		}
	}

}
