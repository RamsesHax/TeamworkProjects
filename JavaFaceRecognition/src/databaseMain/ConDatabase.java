package databaseMain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConDatabase {
	
	public Statement stm;
	public ResultSet resultSet;
	
	private final String driver = "org.mysql.driver";
	private final String root = "jdbc:mysql://sql11.freesqldatabase.com/sql11406818";
	private final String user = "sql11406818";
	private final String pass = "hBANiHPYel";
	
	public Connection connection;
	
	public void connect() throws SQLException{
		System.setProperty("jdbc.Driver", driver);
		connection = DriverManager.getConnection(root, user, pass);
		
		System.out.println("OK");
	}
	
	public void disconnect() throws SQLException{
		connection.close();
	}
	
	public void execSQL(String SQL) throws SQLException{
		stm = connection.createStatement(resultSet.TYPE_SCROLL_SENSITIVE, resultSet.CONCUR_READ_ONLY);
		resultSet = stm.executeQuery(SQL);
	}
	
}
