package databaseMain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControlPerson {
	ConDatabase connection = new ConDatabase();
	
	public void insert(ModelPerson person) {
		try {
			
			connection.connect();
			PreparedStatement pst = connection.connection.prepareStatement("INSERT INTO account (id, user, email , date, address) values (?, ?, ?, ?, ?)");
			pst.setInt(1, person.getId());
			pst.setString(2, person.getUsername());
			pst.setString(3, person.getEmail());
			pst.setString(4, person.getDateOfBirth());
			pst.setString(5, person.getAddress());
			pst.executeUpdate();
			System.out.println("Adaugat in baza de date : " + person.getUsername());
			connection.disconnect();
			
		}catch(SQLException e) {
			System.out.println("Error :"+e);
		}
	}
}
