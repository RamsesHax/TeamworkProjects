package databaseMain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControlPerson {
	ConDatabase connection = new ConDatabase();
	
	public void insert(ModelPerson person) {
		try {
			//in if sa intre si lucruri precum nr de caractere si eventual un format.
			if(!person.getUsername().equals("") || ...) {
			
			connection.connect();
			PreparedStatement pst = connection.connection.prepareStatement("INSERT INTO accounts (id, user, email , date, address) values (?, ?, ?, ?, ?)");
			pst.setInt(1, person.getId());
			pst.setString(2, person.getUsername());
			pst.setString(3, person.getEmail());
			pst.setString(4, person.getDateOfBirth());
			pst.setString(5, person.getAddress());
			pst.executeUpdate();
			System.out.println("Adaugat in baza de date : " + person.getUsername());
			connection.disconnect();
			
			}else {
				//instantiate new JOptionPane cu mesajul like :"Toate campurile trebuie sa fie valide"
			}
		}catch(SQLException e) {
			System.out.println("E :"+e);
		}
	}
}
