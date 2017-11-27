import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.TextArea;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class BattleScene {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BattleScene window = new BattleScene();
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
	public BattleScene() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(0, 307, 524, 132);
		frame.getContentPane().add(textArea);
		
		JButton btnAttack = new JButton("Attack");
		btnAttack.setBounds(0, 278, 89, 23);
		frame.getContentPane().add(btnAttack);
		
		JButton btnDefend = new JButton("Defend");
		btnDefend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDefend.setBounds(88, 278, 89, 23);
		frame.getContentPane().add(btnDefend);
		
		JButton btnNewButton = new JButton("Skill 1");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(176, 278, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Skill 2");
		btnNewButton_1.setBounds(265, 278, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Skill 3");
		btnNewButton_2.setBounds(353, 278, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Skill 4");
		btnNewButton_3.setBounds(441, 278, 89, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\almos_000\\Desktop\\College Stuff\\Test\\img\\knight.png"));
		lblNewLabel.setBounds(10, 92, 121, 121);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\almos_000\\Desktop\\College Stuff\\Test\\img\\knight.png"));
		label.setBounds(403, 92, 121, 121);
		frame.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(58, 61, 44, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblHp = new JLabel("HP");
		lblHp.setBounds(17, 63, 18, 17);
		frame.getContentPane().add(lblHp);
		
		JLabel label_1 = new JLabel("HP");
		label_1.setBounds(403, 64, 18, 17);
		frame.getContentPane().add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(445, 61, 44, 20);
		frame.getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(58, 40, 44, 20);
		frame.getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(445, 40, 44, 20);
		frame.getContentPane().add(textField_3);
		
		JLabel lblStam = new JLabel("STAM");
		lblStam.setBounds(8, 42, 40, 17);
		frame.getContentPane().add(lblStam);
		
		JLabel label_2 = new JLabel("STAM");
		label_2.setBounds(394, 43, 41, 17);
		frame.getContentPane().add(label_2);
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
