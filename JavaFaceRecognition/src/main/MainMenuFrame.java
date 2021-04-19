package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Button;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;

public class MainMenuFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuFrame window = new MainMenuFrame();
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
	public MainMenuFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 920, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(131, 0, 18, 491);
		frame.getContentPane().add(verticalStrut);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Alex\\OneDrive\\Desktop\\bgdl.jpg"));
		lblNewLabel.setBounds(142, 0, 829, 491);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.setBackground(new Color(81, 158, 153));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(0, 0, 139, 163);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Recognition");
		btnNewButton_1.setBounds(0, 162, 139, 163);
		btnNewButton_1.setBackground(new Color(81, 158, 153));
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Data");
		btnNewButton_2.setBounds(0, 321, 139, 170);
		btnNewButton_2.setBackground(new Color(81, 158, 153));
		frame.getContentPane().add(btnNewButton_2);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(0, 158, 139, 5);
		frame.getContentPane().add(horizontalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setBounds(0, 321, 139, 5);
		frame.getContentPane().add(horizontalStrut_1);
	}
}
