package rpgBattle;

import javax.swing.JFrame;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.TextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class Client implements Runnable,RPGConstants {

	JFrame frame2;
	PlayerCharacter playerObj;
	  // Indicate whether the player has the turn
	private boolean myTurn = false;
	// Create and initialize a title label
	private JLabel jlblTitle = new JLabel();
	
	private TextArea textArea = new TextArea();//text area
	private JTextField textField; //player 1 hp
	private JTextField textField_1 = new JTextField();; //player 2 hp
	private JTextField textField_2; //player 1 STAM
	private JTextField textField_3; //player 2 STAM
	
	public int myHP = 100;
	public int mySTAM = 100;
	public int theirHP = 100;
	public int theirSTAM = 100;
	
	private int moveSelected; //which move is selected
	
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	
	  // Continue to play?
	  private boolean continueToPlay = true;

	  // Wait for the player to make a move
	  private boolean waiting = true;
	  
	  // Host name or ip
	  private String host = "localhost";
	  
	  
	  private void connectToServer() {
		    try {
		      // Create a socket to connect to the server
		      Socket socket = new Socket(host, 8000);

		      // Create an input stream to receive data from the server
		      fromServer = new DataInputStream(socket.getInputStream());
		    
		      // Create an output stream to send data to the server
		      toServer = new DataOutputStream(socket.getOutputStream());
		     
		    }
		    catch (Exception ex) {
		      System.err.println(ex);
		    }

		    // Control the game on a separate thread
		    Thread thread = new Thread(this);
		    thread.start();
		  }
	
	  public void run() {
		    try {
		      // Get notification from the server
		      int player = fromServer.readInt();

		      // Am I player 1 or 2?
		      if (player == PLAYER1) {
		        textArea.append("You are player 1.\n");
		        textArea.append("Waiting for player 2 to join \n");

		        // Receive startup notification from the server
		        fromServer.readInt(); // Whatever read is ignored

		        // The other player has joined
		        textArea.append("Player 2 has joined. I start first \n");

		        // It is my turn
		        myTurn = true;
		      }
		      else if (player == PLAYER2) {
		        textArea.append("You are player 2.\n");
		        textArea.append("Waiting for player 1 to move \n");
		      }

		      // Continue to play
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
		      }
		    }
		    catch (Exception ex) {
		    }
		  }
		
	  /** Wait for the other player to attack 
	 * @throws IOException */
	  private void waitForPlayerAction() throws InterruptedException, IOException {
	    while (waiting) 
	    {
	    	Thread.sleep(100);     
	    }

	    waiting = true;
	  }
	  
	  /** Send this player's move to the server */
	  private void sendMove() throws IOException {
	    toServer.writeInt(moveSelected); // Send the selected move
	    
	  }
	  
	  /** Receive info from the server */
	  private void receiveInfoFromServer() throws IOException 
	  {
	    // Receive game status
	      receiveMove();
	      if (myHP <= 0)
	      {
	    	  continueToPlay = false;
	    	  textArea.append("I lost...");
		  }
	      else if (theirHP <= 0)
	      {
	    	  continueToPlay = false;
	    	  textArea.append("I won!");
		  }
	      else
	      {
		      textArea.append("My turn \n");
		      myTurn = true; // It is my turn
	      }
	  }
	  
	  private void receiveMove() throws IOException {
		    // Get the other player's move
		    int theirMove = fromServer.readInt();
		    switch(theirMove)
		    {
		    case 1: myHP = myHP - 10; //attack		   
		    break;
		    case 2: theirSTAM = theirSTAM + 20; //defend
		    break;
		    case 3: myHP = myHP - 40;
		    theirSTAM = theirSTAM - 40;
		    break;
	        case 4: theirHP = theirHP + 40;
	        break;
	        case 5: myHP = myHP - 30;
	        theirHP = theirHP + 20;
	        theirSTAM = theirSTAM - 40;
	        break;
	        case 6: myHP = myHP - 200;
	        theirSTAM = theirSTAM - 180;
	        break;
		    default:
		    break;
		    }
		    textField.setText(Integer.toString(myHP));
		    textField_1.setText(Integer.toString(theirHP));
		    textField_2.setText(Integer.toString(mySTAM));
		    textField_3.setText(Integer.toString(theirSTAM));
		    
		  }
	
					
	/**
	 * Create the application.
	 */
	public Client(PlayerCharacter player) 
	{
		this.playerObj = player;
		initialize();
		connectToServer();
		frame2.repaint();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 550, 500);
		frame2.setTitle("Fighter Game 2");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);	
		
		
		textArea.setBounds(0, 307, 524, 132);
		frame2.getContentPane().add(textArea);
		
		JButton btnAttack = new JButton("Attack");
		btnAttack.setBounds(0, 278, 89, 23);
		frame2.getContentPane().add(btnAttack);
		btnAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
								
		        if (myTurn) 
		        { 
		        	//if it is my turn
		        	textArea.append("You choose to attack \n");	            
		            moveSelected = 1;
		            theirHP = theirHP - 10;
		            textField_1.setText(Integer.toString(theirHP));
		            if (theirHP <= 0)
		  	    	{
		            	continueToPlay = false;
		            	textArea.append("I won!");
		  	    	}
		            else
		            	textArea.append("Waiting for the other player to move \n");
		            myTurn = false;
		            waiting = false; // Just completed a successful move
		       }
			}
		});
		
		JButton btnDefend = new JButton("Defend");
		btnDefend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        if (myTurn) 
		        { 
		        	//if it is my turn
		        	textArea.append("You choose to defend \n");	            
		            moveSelected = 2;
		            mySTAM = mySTAM + 20;
		            textField_2.setText(Integer.toString(mySTAM));
		            textArea.append("Waiting for the other player to move \n");
		            myTurn = false;
		            waiting = false; // Just completed a successful move
		        }
			}
		});
		btnDefend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send stuff to server here
			}
		});
		btnDefend.setBounds(88, 278, 89, 23);
		frame2.getContentPane().add(btnDefend);
		
		JButton btnSkill1 = new JButton(playerObj.getSkill1Name());
		btnSkill1.setToolTipText(playerObj.getSkill1Desc());
		btnSkill1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        if (myTurn && mySTAM >= 40) { //if it is my turn
		        	textArea.append("You use skill 1 \n");	            
		            moveSelected = 3;
		            theirHP = theirHP - 40;
		            textField_1.setText(Integer.toString(theirHP));
		            mySTAM = mySTAM - 40;
		            textField_2.setText(Integer.toString(mySTAM));
		            if (theirHP <= 0)
		  	    	{
		            	continueToPlay = false;
		            	textArea.append("I won!");
		  	    	}
		            else
		            	textArea.append("Waiting for the other player to move \n");
		            myTurn = false;
		            waiting = false; // Just completed a successful move
		          }
		        else
		        {
		        	textArea.append("Not enough stamina choose a different move \n");
		        }
			}
		});
		btnSkill1.setBounds(176, 278, 89, 23);
		frame2.getContentPane().add(btnSkill1);
		
		JButton btnSkill2 = new JButton(playerObj.getSkill2Name());
		btnSkill2.setToolTipText(playerObj.getSkill2Desc());
		btnSkill2.setBounds(265, 278, 89, 23);
		frame2.getContentPane().add(btnSkill2);
		
		btnSkill2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        if (myTurn && mySTAM >= 40) { //if it is my turn
		        	textArea.append("You use skill 2 \n");	            
		            moveSelected = 4;
		            myHP = myHP + 40;
		            textField.setText(Integer.toString(myHP));
		            mySTAM = mySTAM - 40;
		            textField_2.setText(Integer.toString(mySTAM));
		            textArea.append("Waiting for the other player to move \n");
		            myTurn = false;
		            waiting = false; // Just completed a successful move
		          }
		        else
		        {
		        	textArea.append("Not enough stamina choose a different move \n");
		        }
			}
		});
		
		JButton btnSkill3 = new JButton(playerObj.getSkill3Name());
		btnSkill3.setToolTipText(playerObj.getSkill3Desc());
		btnSkill3.setBounds(353, 278, 89, 23);
		frame2.getContentPane().add(btnSkill3);
		btnSkill3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        if (myTurn && mySTAM >= 50) { //if it is my turn
		        	textArea.append("You use skill 3 \n");	            
		            moveSelected = 5;
		            myHP = myHP + 20;
		            textField.setText(Integer.toString(myHP));
		            theirHP = theirHP - 30;
		            textField_1.setText(Integer.toString(theirHP));
		            mySTAM = mySTAM - 50;
		            textField_2.setText(Integer.toString(mySTAM));
		            if (theirHP <= 0)
		  	    	{
		            	continueToPlay = false;
		            	textArea.append("I won!");
		  	    	}
		            else
		            	textArea.append("Waiting for the other player to move \n");
		            myTurn = false;
		            waiting = false; // Just completed a successful move
		          }
		        else
		        {
		        	textArea.append("Not enough stamina choose a different move \n");
		        }
			}
		});
		
		JButton btnSkill4 = new JButton(playerObj.getSkill4Name());
		btnSkill4.setToolTipText(playerObj.getSkill4Desc());
		btnSkill4.setBounds(441, 278, 89, 23);
		frame2.getContentPane().add(btnSkill4);
		btnSkill4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        if (myTurn && mySTAM >= 180) { //if it is my turn
		        	textArea.append("You use skill 4 \n");	            
		            moveSelected = 6;
		            theirHP = theirHP - 200;
		            textField_1.setText(Integer.toString(theirHP));
		            mySTAM = mySTAM - 180;
		            textField_2.setText(Integer.toString(mySTAM));
		            if (theirHP <= 0)
		  	    	{
		            	continueToPlay = false;
		            	textArea.append("I won!");
		  	    	}
		            else
		            	textArea.append("Waiting for the other player to move \n");
		            myTurn = false;
		            waiting = false; // Just completed a successful move
		          }
		        else
		        {
		        	textArea.append("Not enough stamina choose a different move \n");
		        }
			}
		});
		String playerIcon = "img/knight.png";
		switch(playerObj.getClassType())
		{
		case Knight:
			playerIcon = "img/knight.png";
			break;
		case Mage:
			playerIcon = "img/mage.png";
			break;
		case Archer:
			playerIcon = "img/archer.png";
			break;
			
		}
		JLabel lblNewLabel = new JLabel(""); //player 1 sprite
		lblNewLabel.setIcon(new ImageIcon(playerIcon));
		lblNewLabel.setBounds(10, 92, 121, 121);
		frame2.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(playerIcon));
		label.setBounds(403, 92, 121, 121);
		frame2.getContentPane().add(label);
		
		textField = new JTextField(Integer.toString(myHP));
		textField.setBounds(58, 61, 44, 20); //top left hp
		frame2.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblHp = new JLabel("HP");
		lblHp.setBounds(17, 63, 18, 17);
		frame2.getContentPane().add(lblHp);
		
		JLabel label_1 = new JLabel("HP");
		label_1.setBounds(403, 64, 18, 17);
		frame2.getContentPane().add(label_1);
		
		textField_1 = new JTextField(Integer.toString(theirHP));
		textField_1.setColumns(10);
		textField_1.setBounds(445, 61, 44, 20);
		frame2.getContentPane().add(textField_1); // bottom right hp (dont use yet)
		
		textField_2 = new JTextField(Integer.toString(mySTAM));
		textField_2.setColumns(10);
		textField_2.setBounds(58, 40, 44, 20);
		frame2.getContentPane().add(textField_2); // top left stamina 
		
		textField_3 = new JTextField(Integer.toString(theirSTAM));		
		textField_3.setColumns(10);
		textField_3.setBounds(445, 40, 44, 20);
		frame2.getContentPane().add(textField_3); //top right stamina 
		textField_3.setVisible(false);
		
		JLabel lblStam = new JLabel("STAM");
		lblStam.setBounds(8, 42, 40, 17);
		frame2.getContentPane().add(lblStam);
		/*
		JLabel label_2 = new JLabel("STAM");
		label_2.setBounds(394, 43, 41, 17);
		frame2.getContentPane().add(label_2);
		*/
		jlblTitle = new JLabel("");
		jlblTitle.setBounds(31, -24, 350, 23);
		frame2.getContentPane().add(jlblTitle);
		
		frame2.setVisible(true);
	}
}
