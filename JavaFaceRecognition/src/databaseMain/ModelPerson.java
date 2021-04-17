package databaseMain;

public class ModelPerson {
	
	private int id;
	private String username;
	private String email;
	private String dateOfBirth;
	private String address;
	
	public ModelPerson() {	
	}
	
	public ModelPerson(int id , String username , String email , String dateOfBirth, String address) {
		this.id = id;
		this.username = username;
		this.email = email ;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
