import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class Client {

	JFrame frame2;
	PlayerCharacter player;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	  
	  
		public void connectToServer(){ 
			 try{
			        Socket socket = new Socket("localhost", 8000);
			        
			       fromServer = new ObjectInputStream( socket.getInputStream());
			       toServer =  new ObjectOutputStream(socket.getOutputStream());
			      }
			      catch (IOException ex) {
			     System.err.println(ex);
			      }
			 
		}
		
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BattleScene window = new BattleScene();
					window.frame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	
		
		/*if (player.getIsDraw()){
			textField.setText("Is a Draw!!!");
		}else if(player.getYouAreDefeated()){
			textField.setText ("You are Defeated!");
		}else if ( player.getOpponentDefeated()){
			textField.setText("You are the Winner!!!");
		}else{} */
					
	/**
	 * Create the application.
	 */
	public Client(PlayerCharacter x) {
		player = x;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 550, 500);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(0, 307, 524, 132);
		frame2.getContentPane().add(textArea);
		
		JButton btnAttack = new JButton("Attack");
		btnAttack.setBounds(0, 278, 89, 23);
		frame2.getContentPane().add(btnAttack);
		btnAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send stuff to server here
				player.setIsAttacking(true);
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
		JButton btnNewButton = new JButton(player.getSkill1Name());
		btnNewButton.setToolTipText(player.getSkill1Desc());
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send stuff to server here
			}
		});
		btnNewButton.setBounds(176, 278, 89, 23);
		frame2.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(player.getSkill2Name());
		btnNewButton_1.setToolTipText(player.getSkill2Desc());
		btnNewButton_1.setBounds(265, 278, 89, 23);
		frame2.getContentPane().add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send stuff to server here
			}
		});
		
		JButton btnNewButton_2 = new JButton(player.getSkill3Name());
		btnNewButton_2.setToolTipText(player.getSkill3Desc());
		btnNewButton_2.setBounds(353, 278, 89, 23);
		frame2.getContentPane().add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send stuff to server here
			}
		});
		
		JButton btnNewButton_3 = new JButton(player.getSkill4Name());
		btnNewButton_3.setToolTipText(player.getSkill4Desc());
		btnNewButton_3.setBounds(441, 278, 89, 23);
		frame2.getContentPane().add(btnNewButton_3);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send stuff to server here
			}
		});
		String playerIcon = "knight.png";
		switch(player.getClassType())
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
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(playerIcon));
		lblNewLabel.setBounds(10, 92, 121, 121);
		frame2.getContentPane().add(lblNewLabel);
		/*
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("knight.png"));
		label.setBounds(403, 92, 121, 121);
		frame2.getContentPane().add(label);
		*/
		textField = new JTextField();
		textField.setBounds(58, 61, 44, 20);
		frame2.getContentPane().add(textField);
		textField.setColumns(10); //bottom left hp
		
	/*	try {
			player = (PlayerCharacter) fromServer.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		textField.setText(String.valueOf(player.getCurrentHp()));
		
		JLabel lblHp = new JLabel("HP");
		lblHp.setBounds(17, 63, 18, 17);
		frame2.getContentPane().add(lblHp);
		
		JLabel label_1 = new JLabel("HP");
		label_1.setBounds(403, 64, 18, 17);
		frame2.getContentPane().add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(445, 61, 44, 20);
		frame2.getContentPane().add(textField_1); // bottom right hp (dont use yet)
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(58, 40, 44, 20);
		frame2.getContentPane().add(textField_2); // top left stamina 
		int A = 3;
		String myString = Integer.toString(A);
		textField_2.setText(myString);
		
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
	
	}


	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

