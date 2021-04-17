package Database;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FontFormatException;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class RegisterPerson extends JFrame{

	private JFrame frame;
	private JTextField usernameField;
	private JTextField mailField;
	private JTextField passwordField;
	private JTextField repeatPasswordField;
	private ImageIcon image;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterPerson window = new RegisterPerson();
					window.setUndecorated(isDefaultLookAndFeelDecorated());
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
	public RegisterPerson() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBackground(Color.WHITE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.DARK_GRAY);
		leftPanel.setBounds(0, 0, 354, 461);
		frame.getContentPane().add(leftPanel);
		leftPanel.setLayout(null);
		
		
		JLabel imageLabel = new JLabel("");
		imageLabel.setBounds(-52, 0, 443, 320);
		leftPanel.add(imageLabel);
		image = new ImageIcon();
		image.setImage(ImageIO.read(getClass().getResource("/img/leftBgd.jpg"))); 
		imageLabel.setIcon(image);
		
		JLabel leftText = new JLabel("FACE RECOGNITION R&A");
		leftText.setForeground(Color.WHITE);
		leftText.setFont(loadFont("chinese.msyh.ttf", 16 ,Font.TYPE1_FONT));
		leftText.setBounds(63, 323, 231, 64);
		leftPanel.add(leftText);
		
		Button signUp = new Button("SignUp");
		signUp.setForeground(SystemColor.textHighlightText);
		signUp.setBackground(new Color(25, 130, 183));
		signUp.setBounds(382, 344, 324, 35);
		signUp.setFont(new Font("Arial", Font.BOLD, 12));
		frame.getContentPane().add(signUp);
		
		usernameField = new JTextField();
		usernameField.setBounds(364, 43, 354, 35);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JSeparator separatorUsername = new JSeparator();
		separatorUsername.setBounds(364, 77, 354, 2);
		frame.getContentPane().add(separatorUsername);
		
		JLabel usernameLabel = new JLabel("USERNAME");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		usernameLabel.setBounds(364, 21, 76, 23);
		frame.getContentPane().add(usernameLabel);
		
		mailField = new JTextField();
		mailField.setColumns(10);
		mailField.setBounds(364, 111, 354, 35);
		frame.getContentPane().add(mailField);
		
		JSeparator separatorMail = new JSeparator();
		separatorMail.setBounds(364, 145, 354, 2);
		frame.getContentPane().add(separatorMail);
		
		JLabel mailLabel = new JLabel("EMAIL");
		mailLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		mailLabel.setBounds(364, 89, 76, 23);
		frame.getContentPane().add(mailLabel);
		
		passwordField = new JTextField();
		passwordField.setColumns(10);
		passwordField.setBounds(364, 179, 354, 35);
		frame.getContentPane().add(passwordField);
		
		JSeparator separatorPassword = new JSeparator();
		separatorPassword.setBounds(364, 213, 354, 2);
		frame.getContentPane().add(separatorPassword);
		
		JLabel passwordLabel = new JLabel("PASSWORD");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		passwordLabel.setBounds(364, 157, 76, 23);
		frame.getContentPane().add(passwordLabel);
		
		repeatPasswordField = new JTextField();
		repeatPasswordField.setColumns(10);
		repeatPasswordField.setBounds(364, 247, 354, 35);
		frame.getContentPane().add(repeatPasswordField);
		
		JSeparator separatorRepeatpw = new JSeparator();
		separatorRepeatpw.setBounds(364, 281, 354, 2);
		frame.getContentPane().add(separatorRepeatpw);
		
		JLabel repeatpwLabel = new JLabel("REPEAT PASSWORD");
		repeatpwLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		repeatpwLabel.setBounds(364, 225, 117, 23);
		frame.getContentPane().add(repeatpwLabel);
		
		JCheckBox termsAndConditions = new JCheckBox("I have read and agree / agreed with the terms and conditions");
		termsAndConditions.setFont(new Font("Tahoma", Font.ITALIC, 11));
		termsAndConditions.setBackground(SystemColor.activeCaption);
		termsAndConditions.setForeground(Color.WHITE);
		termsAndConditions.setBounds(365, 300, 324, 23);
		frame.getContentPane().add(termsAndConditions);
		frame.setBounds(100, 100, 750, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
	}
	
	private static Font loadFont(String fontName, float size, int style) {

        InputStream openStream = RegisterPerson.class
                .getResourceAsStream("/fontRes/"
                        + fontName);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, openStream);
            Font finalFont = font.deriveFont((float) size).deriveFont(style);
            System.out.println("Loading font " + fontName + " " + finalFont);
            return finalFont;
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (openStream != null) {
                try {
                    openStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
