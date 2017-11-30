

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;
import java.io.*;
import java.net.*;

import java.awt.Canvas;
import javax.swing.ImageIcon;


public class Menu extends PlayerCharacter{
	private JFrame frame;
	private JFrame frame2;
	private JLabel GameTitle;
	private ClassType knight = ClassType.Knight;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	 private boolean isStandAlone = false;
	 private String host = "localhost";
  
  // IO streams
	public void connectToServer(){ 
		try {
		      // Create a socket to connect to the server
		      Socket socket;
		        socket = new Socket( host, 7000);

		      // Create an input stream to receive data from the server
		      fromServer = new ObjectInputStream(socket.getInputStream());

		      // Create an output stream to send data to the server
		      toServer = new ObjectOutputStream(socket.getOutputStream());
		    }
		    catch (Exception ex) {
		      System.err.println(ex);
		    }

		    // Control the game on a separate thread
		    Thread thread = new Thread((Runnable) this);
		    thread.start();
		  }

 
  
  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
					Menu applet = new Menu();
					window.isStandAlone = true;
					 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		GameTitle = new JLabel("Fighter Game 2");
		GameTitle.setForeground(Color.RED);
		GameTitle.setBounds(0, 0, 434, 29);
		GameTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		GameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(GameTitle);
		
		JButton btnNewButton = new JButton("Knight");
		btnNewButton.setBounds(10, 210, 100, 23);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				
				ClassType knight = ClassType.Knight;
				PlayerCharacter player = new PlayerCharacter(knight);
				Client window = new Client(player);
				window.frame2.setVisible(true);  
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							
							connectToServer();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		JButton btnNewButton_1 = new JButton("Archer");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				
				ClassType archer = ClassType.Archer;
				PlayerCharacter player = new PlayerCharacter(archer);
				Client window = new Client(player);
				window.frame2.setVisible(true);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							
							
							connectToServer();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnNewButton_1.setBounds(167, 210, 100, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Mage");
		btnNewButton_2.setBounds(324, 210, 100, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				
				ClassType mage = ClassType.Mage;
				PlayerCharacter player = new PlayerCharacter(mage);
				Client window = new Client(player);
				window.frame2.setVisible(true);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							
							
							connectToServer();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		JLabel lblChooseYourClass = new JLabel("Choose your class");
		lblChooseYourClass.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseYourClass.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblChooseYourClass.setForeground(Color.RED);
		lblChooseYourClass.setBounds(135, 40, 157, 14);
		frame.getContentPane().add(lblChooseYourClass);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("knight.png"));
		label.setBounds(0, 89, 121, 112);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("archer.png"));
		label_1.setBounds(158, 87, 121, 112);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon("mage.png"));
		label_2.setBounds(313, 87, 121, 112);
		frame.getContentPane().add(label_2);
		
		
	}
}




    // Process events


  
  

