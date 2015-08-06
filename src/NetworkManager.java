import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;



/*
 * The network manager listens on the port and stores all of the sockets currently connected.
 * It's also responsible for notifying the driver when a message is received.
 */
public class NetworkManager {
	
	static HashSet<SocketListener> connections = new HashSet<SocketListener>();
	static int timeout = 3600000; // Timeout of one hour
	
	static void beginListening(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Listening...");
			
			while(true) {
				Socket sock = ss.accept();
				sock.setSoTimeout(timeout); 
				SocketListener pw = new SocketListener(sock);
				connections.add(pw);
				System.out.println("Socket connected. IP: " + pw.s.getRemoteSocketAddress());
				(new Thread(pw)).start();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class SocketListener implements Runnable {
		Socket s;
		FormattedPrintWriter out;
		FormattedBufferedReader in;
		
		// The rest of the server will identify this person by their username
		String username;
		
		public SocketListener(Socket s) {
			this.s = s;
			
			try {
				out = new FormattedPrintWriter(s.getOutputStream(), true);
				in = new FormattedBufferedReader(
					new InputStreamReader(s.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			FileUtils.OutLineByLine("introsplash.txt", out);
		}
		                                                    
		@Override
		public void run() {
			// Probably wanna make a utils class to handle
			// all the message parsing, etc.
			System.out.println("The server is now monitoring " + connections.size() + " connections.");

			username = NetworkUtils.login(in, out);
			
			// Play the game
			if(username != null) {
				System.out.println(username + " has logged on!");
				
				play();
			}
			
			// Quit the game
			close();
			connections.remove(this);
			System.out.println("The server is now monitoring " + connections.size() + " connections.");
		}
		
		/*
		 * where the actual gameplay takes place. Right now it's just an echo server.
		 */
		private void play() {
			String inputLine = "";
			
			try {
				while((inputLine = in.readLine()) != null) {
					System.out.println("Recieved: " + inputLine);
					System.out.println("\tFrom: " + s.getRemoteSocketAddress());
					out.println("Server says: " + inputLine);
					if(inputLine.equals("bye")) {
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void close() {
			try {
				s.close();
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
