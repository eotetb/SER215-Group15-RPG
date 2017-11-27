import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Canvas;
import javax.swing.ImageIcon;

public class TitleScreen {

	private JFrame frame;
	private JLabel GameTitle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TitleScreen window = new TitleScreen();
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
	public TitleScreen() {
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
		
		JButton btnNewButton_1 = new JButton("Archer");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(167, 210, 100, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Mage");
		btnNewButton_2.setBounds(324, 210, 100, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblChooseYourClass = new JLabel("Choose your class");
		lblChooseYourClass.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseYourClass.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblChooseYourClass.setForeground(Color.RED);
		lblChooseYourClass.setBounds(135, 40, 157, 14);
		frame.getContentPane().add(lblChooseYourClass);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\almos_000\\Desktop\\College Stuff\\Test\\img\\knight.png"));
		label.setBounds(0, 89, 121, 112);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("C:\\Users\\almos_000\\Desktop\\College Stuff\\Test\\img\\archer.png"));
		label_1.setBounds(158, 87, 121, 112);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon("C:\\Users\\almos_000\\Desktop\\College Stuff\\Test\\img\\mage.png"));
		label_2.setBounds(313, 87, 121, 112);
		frame.getContentPane().add(label_2);
	}
}
