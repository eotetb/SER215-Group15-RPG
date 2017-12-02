package rpgBattle;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

public class Menu extends PlayerCharacter{
	private JFrame frame;
	private JLabel GameTitle;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
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
		frame.setTitle("Fighter Game 2");
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
				//window.frame2.setVisible(true);
			}
		});
		
		JLabel lblChooseYourClass = new JLabel("Choose your class");
		lblChooseYourClass.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseYourClass.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblChooseYourClass.setForeground(Color.RED);
		lblChooseYourClass.setBounds(135, 40, 157, 14);
		frame.getContentPane().add(lblChooseYourClass);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("img/knight.png"));
		label.setBounds(0, 89, 121, 112);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("img/archer.png"));
		label_1.setBounds(158, 87, 121, 112);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon("img/mage.png"));
		label_2.setBounds(313, 87, 121, 112);
		frame.getContentPane().add(label_2);
		
		JLabel lblComingSoon = new JLabel("Coming Soon");
		lblComingSoon.setBounds(168, 212, 84, 19);
		frame.getContentPane().add(lblComingSoon);
		
		JLabel label_3 = new JLabel("Coming Soon");
		label_3.setBounds(313, 212, 84, 21);
		frame.getContentPane().add(label_3);
	}
}

  
  