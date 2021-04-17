package Database;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegisterPerson extends JFrame implements ActionListener {

	JLabel textLabel ;
	JPanel frameColor ;
	JTextField usernameField;
	JTextField passwordField;
	
	RegisterPerson(){
		
		textLabel = new JLabel();
		frameColor = new JPanel();
		usernameField = new JTextField();
		passwordField = new JTextField();

	textLabel.setText("This person gets a ID x");
	textLabel.setVerticalTextPosition(JLabel.NORTH);
	textLabel.setHorizontalAlignment(JLabel.CENTER);
	textLabel.setBounds(150, 50, 500, 100);
	textLabel.setBackground(Color.GRAY);
	textLabel.setOpaque(true);
	textLabel.setFont(new Font("Times New Roman",Font.PLAIN, 22));


	frameColor.setBackground(Color.DARK_GRAY);
	frameColor.setSize(new Dimension(800,800));
	
	usernameField.setPreferredSize(new Dimension(250,40));
	
	passwordField.setPreferredSize(new Dimension(250,40));
	
	this.setTitle("Login");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	this.setSize(800,800);
	this.setLayout(null);	
	
	frameColor.add(passwordField);
	frameColor.add(usernameField);
	this.add(textLabel);
	this.add(frameColor);
	
	this.setVisible(true);
	}
 public static void main (String[] args){
	 new RegisterPerson();
 }
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}

}