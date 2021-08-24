package beans;

import java.util.Date;

import enums.Gender;
import enums.Role;

public class Administrator extends User {
	
	public Administrator() {
		super();
	}

	
	public Administrator(String username, String password, String fistName, String lastName, Gender gender, Date dateOfBirth,
			Role role, boolean isDeleted, boolean isBlocked) {
		super(username, password, fistName, lastName, gender, dateOfBirth, role, isDeleted, isBlocked);
	}
	
}
