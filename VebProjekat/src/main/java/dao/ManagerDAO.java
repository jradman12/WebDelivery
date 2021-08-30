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

public class ManagerDAO {
	
private static Map<String, Manager> managers = new HashMap<>();


	
	
	public ManagerDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public ManagerDAO(String contextPath) {
		loadManagers(contextPath);
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	public Manager find(String username, String password) {
		if (!managers.containsKey(username)) {
			return null;
		}
		Manager manager = managers.get(username);
		if (!manager.getPassword().equals(password)) {
			return null;
		}
		return manager;
	}
	
	public Collection<Manager> findAll() {
		return managers.values();
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadManagers(String contextPath) {
		
			
				Gson gs = new Gson();

				
				String customersJson = "";
				try {
					customersJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\managers.json")));
					//customersJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json")));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Manager>>() {}.getType();
				managers.clear();
				managers = gs.fromJson(customersJson, type);
				
				//just to check it out 
				for(Map.Entry<String, Manager> entry : managers.entrySet()) {
					System.out.println(entry.getValue().getFistName());
				}

	}
	
	
	
	public void saveManagersJSON() {

		String path="C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json";
		Map<String, Manager> allManagers = new HashMap<>();
		for (Manager m : findAll()) {
			allManagers.put(m.getUsername(),m);
		}

		Gson gs = new Gson();
		String json = gs.toJson(allManagers);
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
	
	
	public void addManager(Manager manager) {
		if (!managers.containsValue(manager)) {
			managers.put(manager.getUsername(), manager);
		}
		
	}
	
	public void addNewManager(Manager manager) {
		Manager newManager = new Manager();
		User newUser = new User(manager.getUsername(),manager.getPassword(),manager.getFistName(),manager.getLastName(),manager.getGender(),manager.getDateOfBirth(),Role.MANAGER,false,false);
		newManager.setFistName(manager.getFistName());
		newManager.setLastName(manager.getLastName());
		newManager.setUsername(manager.getUsername());
		newManager.setPassword(manager.getPassword());
		newManager.setDateOfBirth(manager.getDateOfBirth());
		newManager.setGender(manager.getGender());
		newManager.setRole(Role.CUSTOMER);
		newManager.setDeleted(false);
		newManager.setBlocked(false);
		addManager(newManager);
		UserDAO.addNewUser(newUser);
		saveManagersJSON();
	}
	
	
		
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public Manager getManagerByUsername(String username) {
		if (managers.containsKey(username)) {
			return managers.get(username);
		}

		return null;
	}

}
