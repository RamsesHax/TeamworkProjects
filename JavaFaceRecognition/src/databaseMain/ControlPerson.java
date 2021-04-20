package databaseMain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ControlPerson {
	ConDatabase connection = new ConDatabase();
	
	public class WarningFrame {  
		JFrame warning;  
		WarningFrame(){  
		    warning = new JFrame();  
		    JOptionPane.showMessageDialog(warning,"All the fields have to be valid","Warning",JOptionPane.WARNING_MESSAGE);     
		}
	}
	
	public void insert(ModelPerson person) {
		try {
			if(!person.getUsername().isBlank() || !person.getEmail().isBlank() || !person.getDateOfBirth().isBlank() || !person.getAddress().isBlank() || person.getEmail().contains("@gmail") || person.getEmail().contains("@email") ) {
			//isBlank metoda verifica daca este empty sau contine doar spatii
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
				new WarningFrame();
			}
		}catch(SQLException e) {
			System.out.println("E :"+e);
		}
	}
	
	
	//Verificare daca adresa de mail exista(daca nu manca multe resurse o implementare de genu, am s-o fac)
/*	public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
 } */

}
