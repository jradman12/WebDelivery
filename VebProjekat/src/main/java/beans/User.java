package beans;

import java.util.Date;

import enums.Gender;
import enums.Role;



public class User {
	
	private String username;
	private String password;
	private String fistName;
	private String lastName;
	private Gender gender;
	private Date dateOfBirth;
	private Role role;
	private boolean isDeleted;
	private boolean isBlocked;
	
	
	public User() {
		super();
	}


	public User(String username, String password, String fistName, String lastName, Gender gender, Date dateOfBirth,
			Role role, boolean isDeleted, boolean isBlocked) {
		super();
		this.username = username;
		this.password = password;
		this.fistName = fistName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
		this.isDeleted = isDeleted;
		this.isBlocked = isBlocked;
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


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public boolean isDeleted() {
		return isDeleted;
	}


	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


	public boolean isBlocked() {
		return isBlocked;
	}


	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	
	
	
	
	
	
	

}
