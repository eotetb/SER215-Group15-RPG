//package rpgBattle;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Canvas;
import java.awt.Color;
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

	  // Indicate the token for the player
	private char myToken = ' ';

	  // Indicate the token for the other player
	private char otherToken = ' ';
	
	// Create and initialize a title label
	private JLabel jlblTitle = new JLabel();
	
	private TextArea textArea = new TextArea();//text area
	private JTextField textField; //player 1 hp
	private JTextField textField_1 = new JTextField();; //player 2 hp
	private JTextField textField_2; //player 1 stamina
	private JTextField textField_3; //player 2 hp
	
	private int myHP = 100;
	private int mySTAM = 100;
	private int theirHP = 100;
	private int theirSTAM = 100;
	
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
		        myToken = '1';
		        otherToken = '2';
		        System.out.println("test");
		        textArea.append("Player 1 with token '1' \n");
		        textArea.append("Waiting for player 2 to join \n");

		        // Receive startup notification from the server
		        fromServer.readInt(); // Whatever read is ignored

		        // The other player has joined
		        textArea.append("Player 2 has joined. I start first \n");

		        // It is my turn
		        myTurn = true;
		      }
		      else if (player == PLAYER2) {
		        myToken = '2';
		        otherToken = '1';
		        textArea.append("Player 2 with token '2' \n");
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
		
	  /** Wait for the player to mark a cell */
	  private void waitForPlayerAction() throws InterruptedException {
	    while (waiting) {
	      Thread.sleep(100);
	    }

	    waiting = true;
	  }
	  
	  /** Send this player's move to the server */
	  private void sendMove() throws IOException {
	    toServer.writeInt(moveSelected); // Send the selected move
	    
	  }
	  
	  /** Receive info from the server */
	  private void receiveInfoFromServer() throws IOException {
	    // Receive game status
	    //int status = fromServer.readInt();
		  int status = 99;
	    if (status == PLAYER1_WON) {
	      // Player 1 won, stop playing
	      continueToPlay = false;
	      if (myToken == '1') {
	    	  textArea.append("I won! (1)");
	      }
	      else if (myToken == 'O') {
	    	  textArea.append("Player 1 (1) has won!");
	        receiveMove();
	      }
	    }
	    else if (status == PLAYER2_WON) {
	      // Player 2 won, stop playing
	      continueToPlay = false;
	      if (myToken == '2') {
	    	  textArea.append("I won! (O)");
	      }
	      else if (myToken == '1') {
	    	  textArea.append("Player 2 (O) has won!");
	        receiveMove();
	      }
	    }
	    else if (status == DRAW) {
	      // No winner, game is over
	      continueToPlay = false;
	      textArea.append("Game is over, no winner!");

	      if (myToken == '2') {
	        receiveMove();
	      }
	    }
	    else {
	      receiveMove();
	      textArea.append("My turn \n");
	      myTurn = true; // It is my turn
	    }
	  }
	  
	  private void receiveMove() throws IOException {
		    // Get the other player's move
		    int theirMove = fromServer.readInt();
		    switch(theirMove)
		    {
		    case 1: myHP = myHP - 10;
		    textField.setText(Integer.toString(myHP));
		    break;
		    case 2: theirSTAM = theirSTAM + 20;
		    break;
		    default:
		    break;
		    }
		    
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
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);	
		
		
		textArea.setBounds(0, 307, 524, 132);
		frame2.getContentPane().add(textArea);
		
		JButton btnAttack = new JButton("Attack");
		btnAttack.setBounds(0, 278, 89, 23);
		frame2.getContentPane().add(btnAttack);
		btnAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
								
		        if (myTurn) { //if it is my turn
		        	textArea.append("You choose to attack \n");	            
		            moveSelected = 1;		            
		            textArea.append("Waiting for the other player to move \n");
		            myTurn = false;
		            waiting = false; // Just completed a successful move
		          }
			}
		});
		
		JButton btnDefend = new JButton("Defend");
		btnDefend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//send stuff to server here
			}
		});
		btnDefend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send stuff to server here
			}
		});
		btnDefend.setBounds(88, 278, 89, 23);
		frame2.getContentPane().add(btnDefend);
		JButton btnNewButton = new JButton(playerObj.getSkill1Name());
		btnNewButton.setToolTipText(playerObj.getSkill1Desc());
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send stuff to server here
			}
		});
		btnNewButton.setBounds(176, 278, 89, 23);
		frame2.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(playerObj.getSkill2Name());
		btnNewButton_1.setToolTipText(playerObj.getSkill2Desc());
		btnNewButton_1.setBounds(265, 278, 89, 23);
		frame2.getContentPane().add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send stuff to server here
			}
		});
		
		JButton btnNewButton_2 = new JButton(playerObj.getSkill3Name());
		btnNewButton_2.setToolTipText(playerObj.getSkill3Desc());
		btnNewButton_2.setBounds(353, 278, 89, 23);
		frame2.getContentPane().add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send stuff to server here
			}
		});
		
		JButton btnNewButton_3 = new JButton(playerObj.getSkill4Name());
		btnNewButton_3.setToolTipText(playerObj.getSkill4Desc());
		btnNewButton_3.setBounds(441, 278, 89, 23);
		frame2.getContentPane().add(btnNewButton_3);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send stuff to server here
			}
		});
		String playerIcon = "knight.png";
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
		
		textField = new JTextField(Integer.toString(playerObj.maxHp));
		textField.setBounds(58, 61, 44, 20); //top left hp
		frame2.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblHp = new JLabel("HP");
		lblHp.setBounds(17, 63, 18, 17);
		frame2.getContentPane().add(lblHp);
		
		JLabel label_1 = new JLabel("HP");
		label_1.setBounds(403, 64, 18, 17);
		frame2.getContentPane().add(label_1);
		
		textField_1.setColumns(10);
		textField_1.setBounds(445, 61, 44, 20);
		frame2.getContentPane().add(textField_1); // bottom right hp (dont use yet)
		
		textField_2 = new JTextField(Integer.toString(playerObj.maxStamina));
		textField_2.setColumns(10);
		textField_2.setBounds(58, 40, 44, 20);
		frame2.getContentPane().add(textField_2); // top left stamina 
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(445, 40, 44, 20);
		frame2.getContentPane().add(textField_3); //top right stamina 
		
		JLabel lblStam = new JLabel("STAM");
		lblStam.setBounds(8, 42, 40, 17);
		frame2.getContentPane().add(lblStam);
		
		JLabel label_2 = new JLabel("STAM");
		label_2.setBounds(394, 43, 41, 17);
		frame2.getContentPane().add(label_2);
		
		jlblTitle = new JLabel("");
		jlblTitle.setBounds(31, -24, 350, 23);
		frame2.getContentPane().add(jlblTitle);
		
		frame2.setVisible(true);
	}
}

