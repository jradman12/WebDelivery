package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.Administrator;
import enums.Gender;
import enums.Role;

public class AdministratorDAO {

	private Map<String, Administrator> admins = new HashMap<>();
	
	
	public AdministratorDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public AdministratorDAO(String contextPath) {
		loadAdmins(contextPath);
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	public Administrator find(String username, String password) {
		if (!admins.containsKey(username)) {
			return null;
		}
		Administrator admin = admins.get(username);
		if (!admin.getPassword().equals(password)) {
			return null;
		}
		return admin;
	}
	
	public Collection<Administrator> findAll() {
		return admins.values();
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadAdmins(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/data/users.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String username = st.nextToken().trim();
					String password = st.nextToken().trim();
					String firstName = st.nextToken().trim();
					String lastName = st.nextToken().trim();
					String genderStr = st.nextToken().trim();
					String dateStr = st.nextToken().trim();
					String roleStr = st.nextToken().trim();
					String isDeletedStr = st.nextToken().trim();
					String isBlockedStr = st.nextToken().trim();
					Gender gender;
					if(genderStr.equals("MALE")) {
						gender=Gender.MALE;
					}else if(genderStr.equals("FEMALE")) {
						gender=Gender.FEMALE;
					}else {
						gender=Gender.OTHER;
					}
					Role role;
					if(roleStr.equals("ADMINISTRATOR")) {
						role=Role.ADMINISTRATOR;
					}else if(roleStr.equals("CUSTOMER")) {
						role=Role.CUSTOMER;
					}else if(roleStr.equals("DELIVERER")) {
						role=Role.DELIVERER;
					}else {
						role = Role.MANAGER;
					}
					
					Boolean isDeleted;
					if(isDeletedStr.equals("false")) {
						isDeleted=false;
					}else {
						isDeleted=true;
					}
					
					Boolean isBlocked;
					if(isBlockedStr.equals("false")) {
						isBlocked=false;
					}else {
						isBlocked=true;
					}
					 
				    Date dateOfBirth=new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);  
					admins.put(username, new Administrator(username, password, firstName, lastName,gender, dateOfBirth,role,isDeleted,isBlocked));
					
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
}

