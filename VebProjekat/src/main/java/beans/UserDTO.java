package beans;

import java.util.Date;

import enums.Gender;

public class UserDTO {
	
	private String username;
	private String password;
	private String fistName;
	private String lastName;
	private Gender gender;
	private Date dateOfBirth;
	
	public UserDTO() {
		
	}

	public UserDTO(String username, String password, String fistName, String lastName, Gender gender,
			Date dateOfBirth) {
		super();
		this.username = username;
		this.password = password;
		this.fistName = fistName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFistName() {
		return fistName;
	}

	public void setFistName(String fistName) {
		this.fistName = fistName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getFistName() + " " + this.getLastName() + ", username: " + this.getUsername() + ", pass: " +
				this.getPassword() + ", dob: " + this.getDateOfBirth() + ", gender: " + this.getGender();
	}
	

}
