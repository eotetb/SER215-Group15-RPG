package rpgBattle;

import java.io.*;

import java.net.*;
import javax.swing.*;

import java.awt.*;
import java.util.Date;

public class RPGServer extends JFrame
    implements RPGConstants {
  public static void main(String[] args) {
    RPGServer frame = new RPGServer();
  }

  public RPGServer() {
    JTextArea jtaLog = new JTextArea();

    // Create a scroll pane to hold text area
    JScrollPane scrollPane = new JScrollPane(jtaLog);

    // Add the scroll pane to the frame
    add(scrollPane, BorderLayout.CENTER);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 300);
    setTitle("Fighter Game 2 Server");
    setVisible(true);

    try {
      // Create a server socket
      ServerSocket serverSocket = new ServerSocket(8000);
      jtaLog.append(new Date() +
        ": Server started at socket 8000\n");

      // Number a session
      int sessionNo = 1;

      // Ready to create a session for every two players
      while (true) {
        jtaLog.append(new Date() +
          ": Wait for players to join session " + sessionNo + '\n');

        // Connect to player 1
        Socket player1 = serverSocket.accept();

        jtaLog.append(new Date() + ": Player 1 joined session " +
          sessionNo + '\n');
        jtaLog.append("Player 1's IP address" +
          player1.getInetAddress().getHostAddress() + '\n');

        // Notify that the player is Player 1
        new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER1);

        // Connect to player 2
        Socket player2 = serverSocket.accept();

        jtaLog.append(new Date() +
          ": Player 2 joined session " + sessionNo + '\n');
        jtaLog.append("Player 2's IP address" +
          player2.getInetAddress().getHostAddress() + '\n');

        // Notify that the player is Player 2
        new DataOutputStream(player2.getOutputStream()).writeInt(PLAYER2);

        // Display this session and increment session number
        jtaLog.append(new Date() + ": Start a thread for session " +
          sessionNo++ + '\n');

        // Create a new thread for this session of two players
        HandleSession task = new HandleSession(player1, player2);

        // Start the new thread
        new Thread(task).start();
      }
    }
    catch(IOException ex) {
      System.err.println(ex);
    }
  }
}

class HandleSession extends PlayerCharacter implements Runnable, RPGConstants
{
	private Socket Player1;
	private Socket Player2;
	
	private PlayerCharacter player1Object;
	private PlayerCharacter player2Object;
	
	private int currentTurn;
	private int player1HP;
	private int player2HP;
	private int player1STAM;
	private int player2STAM;
	
	private boolean continueToPlay = true;
	
	//private PlayerCharacter player1Char = new PlayerCharacter();
	//private PlayerCharacter player2Char = new PlayerCharacter();
	
	public HandleSession(Socket player1, Socket player2) {
		
		this.Player1 = player1;
		this.Player2 = player2;
		
		ClassType knight = ClassType.Knight;
		
		player1Object = new PlayerCharacter(knight);
		player2Object = new PlayerCharacter(knight);
		
		player1HP = player1Object.getMaxHp();
		player2HP = player2Object.getMaxHp();
		
		player1STAM = player1Object.getMaxStamina();
		player2STAM = player2Object.getMaxStamina();
	}
	
	public void run()
	{
		try {

			
		    DataInputStream fromPlayer1 = new DataInputStream(Player1.getInputStream());
		    DataOutputStream toPlayer1 = new DataOutputStream(Player1.getOutputStream());
		    DataInputStream fromPlayer2 = new DataInputStream(Player2.getInputStream());
		    DataOutputStream toPlayer2 = new DataOutputStream(Player2.getOutputStream());
			 
		    //let player 1 know to start
		    toPlayer1.writeInt(1);
			
		    while (true) {
		    	//take in player 1 move
		    	
		        int moveSelected = fromPlayer1.readInt();
		        switch(moveSelected)		        
		        {
		        case 1: 
		        	player2HP = player2HP - 10;
		        	break;
		        case 2: 
		        	player1STAM = player1STAM + 20;
		        	break;
		        case 3: 
		        	player2HP = player2HP - 40;
		        	break;
		        case 4: 
		        	player1HP = player1HP + 40;
		        	break;
		        case 5: 
		        	player2HP = player2HP - 30;
		        	player1HP = player1HP + 20;
		        	break;
		        case 6: 
		        	player2HP = player2HP - 200;
		        	break;
		        default:
		        	break;
		        }
		        
	          // Notify player 2 to take the turn
		      //toPlayer2.writeInt(CONTINUE);

	          // Send player 1's move to player 2.
	          sendMove(toPlayer2, moveSelected);
	          currentTurn++;
	          
	          // Receive a move from Player 2
	           int moveSelected2 = fromPlayer2.readInt();
		        switch(moveSelected2)		        
		        {
		        case 1: 
		        	player1HP = player1HP - 10;
		        	break;
		        case 2: 
		        	player2STAM = player2STAM + 20;
		        	break;
		        case 3: 
		        	player1HP = player1HP - 40;
		        	break;
		        case 4: 
		        	player2HP = player2HP + 40;
		        	break;
		        case 5: 
		        	player1HP = player1HP - 30;
		        	player2HP = player2HP + 20;
		        	break;
		        case 6: 
		        	player1HP = player1HP - 200;
		        	break;
		        default:
		        	break;
		        }
		       	  // Notify player 2 to take the turn
		          //toPlayer1.writeInt(CONTINUE);

		          // Send player 2's move to player 1.
		          sendMove(toPlayer1, moveSelected2);
		          
		          System.out.println(new Date() +
		        		  " P1 HP " + player1HP + " P2 HP " + player2HP);
		    }
			
			
		} catch (IOException ex) {
			System.err.println(ex);
		}
		
	}
	 
	/** Send the move to other player */
	  
	private void sendMove(DataOutputStream out, int myMove)
	      throws IOException {
		
	    out.writeInt(myMove); // Send move number
	    
	  }
	

}