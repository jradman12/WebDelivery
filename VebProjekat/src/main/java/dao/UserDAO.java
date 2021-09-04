package dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Manager;
import beans.User;
import enums.Role;

public class UserDAO {
 
public static Map<String, User> users = new HashMap<>();	
	public UserDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	public UserDAO(String contextPath) {
		loadUsers(contextPath);
	}
	
	/**
	 * Vra�a korisnika za prosle�eno korisni�ko ime i �ifru. Vra�a null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	public User find(String username, String password) {
		if (!users.containsKey(username)) {
			return null;
		}
		User user = users.get(username);
		if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}
	
	public static Collection<User> findAll() {
		return users.values();
	}
	
	/**
	 * U�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Klju� je korisni�ko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public static void loadUsers(String contextPath) {
		
			
				Gson gs = new Gson();

				
				String usersJson = "";
				try {
					usersJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\users.json")));
					//customersJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, User>>() {}.getType();
				users.clear();
				users = gs.fromJson(usersJson, type);
				
				//just to check it out 
//				for(Map.Entry<String, User> entry : users.entrySet()) {
//					System.out.println(entry.getValue());
//				}
	}
	
		
		
	public static void saveUsersJson() {
		String path="C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\users.json";
		

		Map<String, User> allUsers = new HashMap<>();
		
		for (User u : findAll()) {
			allUsers.put(u.getUsername(),u);
		}

		Gson gs = new Gson();
		String json = gs.toJson(allUsers);
		byte[] inBytes = json.getBytes();
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(path);
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("Check the path u gave me!!");
		}
		try {
			fos.write(inBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public static void addUser(User user) {
		loadUsers("");
		if (!users.containsValue(user)) {
			users.put(user.getUsername(), user);
		}
		
	}
	
	public static void addNewUser(User user) {
		User newUser = new User();
		newUser.setFistName(user.getFistName());
		newUser.setLastName(user.getLastName());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		newUser.setDateOfBirth(user.getDateOfBirth());
		newUser.setGender(user.getGender());
		newUser.setRole(user.getRole());
		newUser.setDeleted(user.isDeleted());
		newUser.setBlocked(user.isBlocked());
		addUser(newUser);
		saveUsersJson();
	}
	
	
		
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public User getUserByUsername(String username) {
		if (users.containsKey(username)) {
			return users.get(username);
		}

		return null;
	}
	
	public Role getUsersRole(String username) {
		if (users.containsKey(username)) {
			return users.get(username).getRole();
		}

		return null;
	}

	public static void changeUser(String username,User user) {
		loadUsers("");
		System.out.println("userDAO");
		for (User u : users.values()) {
			if (u.getUsername().equals(username)) {
				u.setFistName(user.getFistName());
				u.setLastName(user.getLastName());
				u.setPassword(user.getPassword());
				saveUsersJson();
				
			}
		}
		
		
		
	}
	
	
}
