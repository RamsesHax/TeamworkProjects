package databaseMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ShowData extends JFrame {
	private JFrame frame;
	private JPanel panel;
	private JScrollPane scrollBar;
	private JTextArea textArea;
	
	
	public ShowData() {
		 
		
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 698, 430);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().setResizable(false);
		getFrame().setLayout(new BorderLayout());
		
		panel = new JPanel();
		panel.setBounds(0, 0, 698, 460);
		panel.setBackground(Color.red);
		panel.setLayout(new BorderLayout());
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Serif", Font.ITALIC, 16));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setBackground(Color.RED);
		
		try {
		      File myObj = new File("D:\\filename.txt"); // aici trebuie bagata baza de date
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		    	textArea.setText(data);
		    	textArea.setOpaque(false);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		textArea.setForeground(Color.WHITE);
		
		scrollBar = new JScrollPane(textArea);
		scrollBar.setSize(new Dimension(20, 390));
		scrollBar.setLocation(660,0);
		
		scrollBar.getViewport().setOpaque(false);
		scrollBar.setOpaque(false);
		
		panel.add(scrollBar);
		getFrame().add(panel);
	
		
			
		
		getFrame().setVisible(true);
		
		
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

}
	
