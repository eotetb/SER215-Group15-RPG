import java.io.IOException;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class RPGServer extends JFrame {
	// Text area for displaying contents
	  private JTextArea jta = new JTextArea();

	public static void main (String args[]) {
		new RPGServer();
	}
	
	public RPGServer() {

		// Place text area on the frame
	    setLayout(new BorderLayout());
	    add(new JScrollPane(jta), BorderLayout.CENTER);
	    setTitle("RPGServer");
	    setSize(500, 300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true); // It is necessary to show the frame here!

		try {
			jta.append("Just after try");

			ServerSocket serverSocket = new ServerSocket(8000);
			while (true) {
				jta.append("in while true");

				Socket player1 = serverSocket.accept();
				InetAddress p1InetAddress = player1.getInetAddress();
				jta.append("Player 1 address: " + p1InetAddress + " has connected to the server");
				
				
				Socket player2 = serverSocket.accept();
				InetAddress p2InetAddress = player2.getInetAddress();
				jta.append("Player 2 address: " + p2InetAddress + " has connected to the server");

				HandleSession task = new HandleSession(player1,player2);
				
				new Thread(task).start();
				
			}

			
		} catch(IOException ex) {
		      System.err.println(ex);
	    }
		
		
	}
	
	
	
}
