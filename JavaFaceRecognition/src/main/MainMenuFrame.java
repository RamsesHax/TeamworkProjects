package main;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Button;
import javax.swing.JButton;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
	 * @throws IOException 
	 */
	public MainMenuFrame() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 920, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(131, 0, 18, 491);
		frame.getContentPane().add(verticalStrut);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/img/facialrecIcone.jpg"))));
		lblNewLabel.setBounds(142, 0, 770, 533);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.setBackground(new Color(81, 158, 153));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(0, 0, 142, 163);
		btnNewButton.setFocusable(false);
		btnNewButton.setBorder(BorderFactory.createBevelBorder(2));
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Recognition");
		btnNewButton_1.setBounds(0, 162, 142, 163);
		btnNewButton_1.setBackground(new Color(144, 214, 210));
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.setBorder(BorderFactory.createBevelBorder(2));
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Data");
		btnNewButton_2.setBounds(0, 322, 142, 169);
		btnNewButton_2.setBackground(new Color(81, 158, 153));
		btnNewButton_2.setFocusable(false);
		btnNewButton_2.setBorder(BorderFactory.createBevelBorder(2));
		frame.getContentPane().add(btnNewButton_2);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(0, 158, 139, 5);
		frame.getContentPane().add(horizontalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setBounds(0, 321, 139, 5);
		frame.getContentPane().add(horizontalStrut_1);
	}
}
