package databaseMain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class WriteIntoFileFromDatabase {
	public static void main(String[] args) {
		final String root = "jdbc:mysql://sql11.freesqldatabase.com/sql11406818";
		final String user = "sql11406818";
		final String pass = "hBANiHPYel";
		List data = new ArrayList();


		try {
			
			Connection con = null;
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(root, user, pass);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM accounts");

			while (rs.next()) {
				String userName = rs.getString("user");
				String email = rs.getString("email");
				String date = rs.getString("date");
				String address = rs.getString("address");
				data.add(userName + " " + email + " " + date + " " + address);

			}
			writeToFile(data, "D:\\testareData.txt");
			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void writeToFile(java.util.List<String> list, String path) {
		
		BufferedWriter out = null;
		try {
			File file = new File(path);
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