package dto;

import java.util.Date;

import beans.Customer;
import beans.CustomerType;
import beans.User;
import enums.Gender;
import enums.Role;

public class UserDTO {
	
	private String username;
	private String password;
	private String fistName;
	private String lastName;
	private Gender gender;
	private Date dateOfBirth;
	private Role role;
	private boolean isDeleted;
	private boolean isBlocked;
	private CustomerType type;
	private int points;
	
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public UserDTO(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.fistName = user.getFistName();
		this.lastName = user.getLastName();
		this.gender = user.getGender();
		this.dateOfBirth = user.getDateOfBirth();
		this.role = user.getRole();
		this.isDeleted = user.isDeleted();
		this.isBlocked = user.isBlocked();
		this.type = null;
	}
	
	public UserDTO(Customer user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.fistName = user.getFistName();
		this.lastName = user.getLastName();
		this.gender = user.getGender();
		this.dateOfBirth = user.getDateOfBirth();
		this.role = user.getRole();
		this.isDeleted = user.isDeleted();
		this.isBlocked = user.isBlocked();
		this.type = user.getType();
		this.points = user.getPoints();
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

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}
	
	
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", role=" + role + ", type=" + type + "]";
	}
	
}
