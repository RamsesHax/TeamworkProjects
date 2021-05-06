package databaseMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;





public class WriteIntoFileFromDatabase {
	 public WriteIntoFileFromDatabase() {
		List data = new ArrayList();
		
		ConDatabase connected = new ConDatabase();
		
		new Thread() {

			@Override
			public void run() {
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
						System.out.print(connected.resultSet.getString(2));
					}
					System.out.println("////");
					while(connected.resultSet.next()) {
						System.out.print(connected.resultSet.getString(3));
					}
					System.out.println("////");
					while(connected.resultSet.next()) {
						System.out.print(connected.resultSet.getString(4));
					}
					System.out.println("////");
					while(connected.resultSet.next()) {
						System.out.print(connected.resultSet.getString(5));
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
		}.start();

			/*	writeToFile(data);
				data.add(userName + " " + email + " " + date + " " + address);
				BufferedReader reader;
				try {
					reader = new BufferedReader(new FileReader("testareData.txt"));
					String line = reader.readLine();
					while (line != null) {
						ShowData.textArea.setText(line);
						System.out.println(line); // Syso il ia bine la fel si mai sus dar problema e ca nu afiseaza decat ultima linie din text doc in fisier
						while(line != null) {
						line = reader.readLine();
						}
					}
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		*/
	}

	private static void writeToFile(java.util.List<String> list) {
		
		BufferedWriter out = null;
		try {
			File file = new File("testareData.txt");
			out = new BufferedWriter(new FileWriter(file, true));
			new FileWriter(file,false).close();
			for (String s : list) {
				out.write(s);
				out.newLine();
				
			}
			out.close();
		} catch (IOException e) {
		}
	
	}
}