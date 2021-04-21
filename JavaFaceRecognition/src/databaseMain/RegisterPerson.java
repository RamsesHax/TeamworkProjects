package databaseMain;

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

import capture.CaptureFrame;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class RegisterPerson extends JFrame implements ActionListener{

	private JFrame frame;
	private JTextField usernameField;
	private JTextField mailField;
	private JTextField dateOfBirthField;
	private JTextField addressField;
	private ImageIcon image;
	Button signUp;
	ConDatabase connect = new ConDatabase();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterPerson window = new RegisterPerson();
					window.setUndecorated(isDefaultLookAndFeelDecorated());
					window.getFrame().setVisible(true);
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
		setFrame(new JFrame());
		getFrame().setBackground(Color.WHITE);
		getFrame().getContentPane().setBackground(Color.WHITE);
		getFrame().getContentPane().setLayout(null);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.DARK_GRAY);
		leftPanel.setBounds(0, 0, 354, 461);
		getFrame().getContentPane().add(leftPanel);
		leftPanel.setLayout(null);
		
		
		JLabel imageLabel = new JLabel("");
		imageLabel.setBounds(-52, 0, 443, 320); 
		leftPanel.add(imageLabel);
		image = new ImageIcon();
		image.setImage(ImageIO.read(getClass().getResource("/img/leftBgd.jpg"))); 
		imageLabel.setIcon(image);
		
		JLabel leftText = new JLabel("FACE RECOGNITION R&A");
		leftText.setForeground(Color.WHITE);
		leftText.setFont(loadFont("chinese.msyh.ttf", 16 ,Font.BOLD));
		leftText.setBounds(63, 323, 231, 64);
		leftPanel.add(leftText);
		
		signUp = new Button("SignUp");
		signUp.setForeground(SystemColor.textHighlightText);
		signUp.setBackground(new Color(25, 130, 183));
		signUp.setBounds(382, 344, 324, 35);
		signUp.setFont(loadFont("arial.ttf", 12 ,Font.BOLD));
		getFrame().getContentPane().add(signUp);
		signUp.addActionListener(this);
		
		usernameField = new JTextField();
		usernameField.setBounds(364, 43, 354, 35);
		getFrame().getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JSeparator separatorUsername = new JSeparator();
		separatorUsername.setBounds(364, 77, 354, 2);
		getFrame().getContentPane().add(separatorUsername);
		
		JLabel usernameLabel = new JLabel("USERNAME");
		usernameLabel.setFont(loadFont("Tahoma.ttf", 13 ,Font.PLAIN));
		usernameLabel.setBounds(364, 21, 76, 23);
		getFrame().getContentPane().add(usernameLabel);
		
		mailField = new JTextField();
		mailField.setColumns(10);
		mailField.setBounds(364, 111, 354, 35);
		getFrame().getContentPane().add(mailField);
		
		JSeparator separatorMail = new JSeparator();
		separatorMail.setBounds(364, 145, 354, 2);
		getFrame().getContentPane().add(separatorMail);
		
		JLabel mailLabel = new JLabel("EMAIL");
		mailLabel.setFont(loadFont("Tahoma.ttf", 13 ,Font.PLAIN));
		mailLabel.setBounds(364, 89, 76, 23);
		getFrame().getContentPane().add(mailLabel);
		
		dateOfBirthField = new JTextField();
		dateOfBirthField.setColumns(10);
		dateOfBirthField.setBounds(364, 179, 354, 35);
		getFrame().getContentPane().add(dateOfBirthField);
		
		JSeparator separatorPassword = new JSeparator();
		separatorPassword.setBounds(364, 213, 354, 2);
		getFrame().getContentPane().add(separatorPassword);
		
		JLabel birthLabel = new JLabel("DATE OF BIRTH");
		birthLabel.setFont(loadFont("Tahoma.ttf", 13 ,Font.PLAIN));
		birthLabel.setBounds(364, 157, 150, 23);
		getFrame().getContentPane().add(birthLabel);
		
		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(364, 247, 354, 35);
		getFrame().getContentPane().add(addressField);
		
		JSeparator separatorRepeatpw = new JSeparator();
		separatorRepeatpw.setBounds(364, 281, 354, 2);
		getFrame().getContentPane().add(separatorRepeatpw);
		
		JLabel cityLabel = new JLabel("ADDRESS");
		cityLabel.setFont(loadFont("Tahoma.ttf", 13 ,Font.PLAIN));
		cityLabel.setBounds(364, 225, 117, 23);
		getFrame().getContentPane().add(cityLabel);
		
		JCheckBox termsAndConditions = new JCheckBox("I have read and agree / agreed with the terms and conditions");
		termsAndConditions.setFont(loadFont("Tahoma.ttf", 11 ,Font.ITALIC));
		termsAndConditions.setBackground(SystemColor.activeCaption);
		termsAndConditions.setForeground(Color.WHITE);
		termsAndConditions.setBounds(365, 300, 324, 23);
		getFrame().getContentPane().add(termsAndConditions);
		getFrame().setBounds(100, 100, 750, 430);
		getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getFrame().setResizable(false);
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
	/*	String userName = usernameField.getText();
		String mail =  mailField.getText();
		String dob = dateOfBirthField.getText();
		String address = addressField.getText();
		new CaptureFrame(userName, mail, dob, address).setVisible(true);*/
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
