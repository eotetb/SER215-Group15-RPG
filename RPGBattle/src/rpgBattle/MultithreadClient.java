import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JLabel;

public class MultithreadClient {
	
	private boolean myTurn = false;
	private PlayerCharacter myCharacter;
	private boolean rematch = false;
	private int moveSelected;
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	private boolean waiting = false;
	private boolean continueToPlay = true;
	
	public void connectToServer(){ //connect to server
	 try{
	        // Create a socket to connect to the server
	        Socket socket = new Socket("localhost", 8000);
	        
	        // Create an input stream to receive data from the server
	        fromServer = new DataInputStream( socket.getInputStream());

	        // Create an output stream to send data to the server
	        toServer =  new DataOutputStream( socket.getOutputStream());
	      }
	      catch (IOException ex) {
	     System.err.println(ex);
	      }
}
	
public void run() {
    try {
    	// Get notification from the server
    	int player = fromServer.readInt();

      // Am I player 1 or 2?
      if (player == PLAYER1) {
    	//  jlblTitle.setText("Player one choose class:");
    	  //jlblStatus.setText("Waiting for player 2 to join..");
      }
      else if( player == PLAYER2){
    	  //jlblStatus.setText("Player one's turn");
      }
      
      while (continueToPlay) {
          if (player == PLAYER1) {
            waitForPlayerAction(); // Wait for player 1 to move
            sendMove(); // Send the move to the server
            receiveInfoFromServer(); // Receive info from the server
          }
          else if (player == PLAYER2) {
            receiveInfoFromServer(); // Receive info from the server
            waitForPlayerAction(); // Wait for player 2 to move
            sendMove(); // Send player 2's move to the server
          }
catch (Exception ex) {
			}
      }
    }

private void receiveMove() throws IOException {
    // Get the other player's move
   //get the move from Button player chooses
	
  }
private void selectAction(){ //select Action
}
    }

private void sendMove() throws IOException { // send move
   // toServer 
   // toServer 
  }
private void receiveInfoFromServer() throws IOException {
    // Receive game status
    int status = fromServer.readInt();
    
    if (player == PLAYER1) {
        //jlblStatus.setText("You have defeated your enemy");
      }
      else if (player == PLAYER2) {
       // jlblStatus.setText("Player one has won!");
        receiveMove();
      }
      else if (status == PLAYER2_WON) {
          // Player 2 won, stop playing
          continueToPlay = false;
          if (player == PLAYER2) {
           // jlblStatus.setText("You have defeated your enemy");
          }
          else if (player == PLAYER1) {
            //jlblStatus.setText("Player two has won!");
            receiveMove();
          }
        }
      else if (status == DRAW) {
          // No winner, game is over
          continueToPlay = false;
         // jlblStatus.setText("The Match is over, there is no winner!");

          if (player == PLAYER2) {
            receiveMove();
          }
        }
        else {
          receiveMove();
         // jlblStatus.setText("Your turn to pick a move");
          myTurn = true; // It is my turn
        }
      }

private void waitForPlayerAction() throws InterruptedException { // wait for player Action
    while (waiting) {
      Thread.sleep(100);
    }

    waiting = true;
  }


}
