package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Recognizer.Recognizer;
import capture.CaptureFrame;
import databaseMain.RegisterPerson;
import databaseMain.ShowData;
import databaseMain.WriteIntoFileFromDatabase;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import java.awt.Color;

public class MainMenuFrame implements ActionListener{
	JButton btnNewButton;
	JButton btnNewButton_1;
	JButton btnNewButton_2;
	private static JFrame frame;
	boolean frameCheck = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainMenuFrame();
					MainMenuFrame.frame.setVisible(true);
					new WriteIntoFileFromDatabase();
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
		frame = new JFrame("FaceRecognition");
		frame.setBounds(100, 100, 920, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(131, 0, 18, 491);
		frame.getContentPane().add(verticalStrut);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/img/facialrecIcone.jpg"))));
		lblNewLabel.setBounds(134, 0, 770, 533);
		frame.getContentPane().add(lblNewLabel);
		
		btnNewButton = new JButton("Register");
		btnNewButton.setBackground(new Color(4,13,30));
		btnNewButton.setForeground(new Color(159,240,248));
		btnNewButton.setBounds(0, 0, 134, 163);
		btnNewButton.setFocusable(false);
		btnNewButton.setBorder(BorderFactory.createLineBorder(new Color(1,68,98)));
		btnNewButton.addActionListener(this);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("Recognition");
		btnNewButton_1.setBounds(0, 162, 134, 163);
		btnNewButton_1.setBackground(new Color(4,13,30));
		btnNewButton_1.setForeground(new Color(159,240,248));
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.setBorder(BorderFactory.createLineBorder(new Color(1,68,98)));
		btnNewButton_1.addActionListener(this);
		frame.getContentPane().add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Data");
		btnNewButton_2.setBounds(0, 322, 134, 169);
		btnNewButton_2.setBackground(new Color(4,13,30));
		btnNewButton_2.setForeground(new Color(159,240,248));
		btnNewButton_2.setFocusable(false);
		btnNewButton_2.setBorder(BorderFactory.createLineBorder(new Color(1,68,98)));
		btnNewButton_2.addActionListener(this);
		frame.getContentPane().add(btnNewButton_2);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(0, 158, 139, 5);
		frame.getContentPane().add(horizontalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setBounds(0, 321, 139, 5);
		frame.getContentPane().add(horizontalStrut_1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== btnNewButton) {
			new Thread() {
				public void run() {
					try {
						RegisterPerson window = new RegisterPerson();
						window.getFrame().setVisible(true);
								frame.setEnabled(false);
								window.getFrame().addWindowListener(new WindowAdapter(){
									public void windowClosing(WindowEvent e) {
										if(e.getSource()==window.getFrame()) {
											frame.setEnabled(true);
										}
									}
								});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		} else if(e.getSource()== btnNewButton_1) { 
			new Thread() {
				public void run() {
					try {
						Recognizer window = new Recognizer();
						window.getFrame().setVisible(true);
								frame.setEnabled(false);
								window.getFrame().addWindowListener(new WindowAdapter(){
									public void windowClosing(WindowEvent e) {
										if(e.getSource()==window.getFrame()) {
											frame.setEnabled(true);
										}
									}
								});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		} else if(e.getSource()== btnNewButton_2) { 
			new Thread() {
				public void run() {
					try {
						ShowData window = new ShowData();
						window.getFrame().setVisible(true);
								frame.setEnabled(false);
								window.getFrame().addWindowListener(new WindowAdapter(){
									public void windowClosing(WindowEvent e) {
										if(e.getSource()==window.getFrame()) {
											frame.setEnabled(true);
										}
									}
								});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		
	}

	public static JFrame getFrame() {
		// TODO Auto-generated method stub
		return frame;
		
	}
	
}
