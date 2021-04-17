package Database;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RegisterPerson extends JFrame implements ActionListener {

	JLabel l1 = new JLabel();
	JTextField t1 = new JTextField();
	JTextField t2 = new JTextField();
	
	RegisterPerson(){

	l1.setText("This person gets a ID x");
	l1.setVerticalTextPosition(JLabel.NORTH);
	l1.setHorizontalAlignment(JLabel.CENTER);
	l1.setBounds(150, 50, 500, 100);
	l1.setBackground(Color.GRAY);
	l1.setOpaque(true);
	l1.setFont(new Font("Times New Roman",Font.PLAIN, 22));
	
	t1.setPreferredSize(new Dimension(250,40));
	t2.setPreferredSize(new Dimension(250,40));
		
	this.setTitle("Login");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	this.setSize(800,800);
	this.setLayout(null);
	this.setVisible(true);
	this.getContentPane().setBackground(Color.DARK_GRAY);
	this.add(l1);
	this.add(t1);
	this.add(t2);
	
	
	
	
	}
 public static void main (String args[]){
	 new RegisterPerson();
 }
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}

}