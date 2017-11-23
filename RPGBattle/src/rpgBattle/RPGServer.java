import java.io.IOException;
import java.net.*;

public class RPGServer {
	
	public RPGServer() {
		
		try {
			ServerSocket serverSocket = new ServerSocket(8000	);
			
			while (true) {
				
				Socket player1 = serverSocket.accept();
				InetAddress p1InetAddress = player1.getInetAddress();
				
				Socket player2 = serverSocket.accept();
				InetAddress p2InetAddress = player2.getInetAddress();
				
				System.out.println("Player 1 address: " + p1InetAddress + " has connected to the server");
				System.out.println("Player 2 address: " + p2InetAddress + " has connected to the server");

				HandleSession task = new HandleSession(player1,player2);
				
				new Thread(task).start();
				
			}

			
		} catch(IOException ex) {
		      System.err.println(ex);
	    }
		
		
	}
	
	
	
}
