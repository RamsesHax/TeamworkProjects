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
		 try {
		      File myObj = new File("D:\\filename.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        textArea.setText(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 698, 430);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().setResizable(false);
		getFrame().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 698, 460);
		panel.setBackground(Color.red);
		panel.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Serif", Font.ITALIC, 16));
		textArea.setLocation(0,0);
		textArea.setLineWrap(true); // taie din text si-l pune sub(un fel de resize al textului pentru a fii incadrat in frame)
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		//textArea.setText("Ana are mereasAna are mereasAna are mereasAna are mereasAna are mereasAna are mereasAna are mereasAna are mereasAna are mereasAna are mereasAna are mereasAna are mereasAna are mereasAna are mereasAna are mereas");
		// nu apare nici un fel de text pe pane
		textArea.setBackground(Color.white);
		
		scrollBar = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBar.setSize(new Dimension(20, 390));
		scrollBar.setLocation(660,0);
		
		panel.add(textArea);
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
	
