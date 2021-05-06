package databaseMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ShowData extends JFrame {
	private JFrame frame;
	private JPanel panel;
	JScrollPane scrollBar;
	JList<String> list;
	DefaultListModel<String> model;
	
	public ShowData() {
		 
		
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 698, 430);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().setResizable(false);
		getFrame().setLayout(new BorderLayout());
		
		panel = new JPanel();
		panel.setBounds(0, 0, 698, 460);
		panel.setBackground(Color.red);
		panel.setLayout(null);
		/*
		textArea.setFont(new Font("Serif", Font.ITALIC, 16));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setBackground(Color.RED);
		*/
		/*try {
		      File myObj = new File("D:\\testareData.txt"); // aici trebuie bagata baza de date
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		    	textArea.setText(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }*/
		
		
		//textArea.setForeground(Color.WHITE);
		//textArea.setOpaque(false);
		
		list = new JList<>();
		model=new DefaultListModel<>();
		WriteIntoFileFromDatabase();
		list.setModel(model);
		list.setCursor(new Cursor(Cursor.HAND_CURSOR));
		list.setBorder(BorderFactory.createLoweredBevelBorder());
		list.setBackground(new Color(28, 34, 38));
		list.setForeground(new Color(126, 247, 140));
		list.setOpaque(true);
		
		scrollBar = new JScrollPane(list);
		scrollBar.setBounds(0, 0, 650, 420);
		
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
	public void WriteIntoFileFromDatabase() {
		
		List data = new ArrayList();
				ConDatabase connected = new ConDatabase();
				
				try {
					connected.connect();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					String SQL = "SELECT * FROM accounts" ;
					connected.execSQL(SQL); 
					
					while(connected.resultSet.next()) {
						String u = connected.resultSet.getString("user");
						String.format("%s ----" , u);
						String m = connected.resultSet.getString("email");
						String d = connected.resultSet.getString("date");
						String a = connected.resultSet.getString("address");
						
						model.addElement(String.format("User: %-25s Mail: %-25s Date: %-25s Address: %-25s" , u,m,d,a)); 
						System.out.println("================================");
						System.out.println(String.format("User: %-25s Mail: %-25s Date: %-25s Address: %-25s" , u,m,d,a)); 						
						
					}
					
					
				}catch(Exception e) {
					
				}
				
				try {
					connected.disconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}

}
	
