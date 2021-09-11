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

import beans.Administrator;
import beans.User;


public class AdministratorDAO {

	private  Map<String, Administrator> admins = new HashMap<>();
	public String path = "C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\admins.json";
	private UserDAO userDAO = new UserDAO("");

	
	public AdministratorDAO() {
	}
	
	
	public AdministratorDAO(String contextPath) {
		loadAdmins(contextPath);
	}
	
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
	
	
	private void loadAdmins(String contextPath) {
		
		Gson gs = new Gson();
				String adminsJson = "";
				try {
					adminsJson = new String(Files.readAllBytes(Paths.get(path)));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Administrator>>() {}.getType();
				admins.clear();
				admins = gs.fromJson(adminsJson, type);
				
				//just to check it out 
//				for(Map.Entry<String, Administrator> entry : admins.entrySet()) {
//					System.out.println(entry.getValue().getFistName());
//				}
	}
	
	public void saveAdministratorsJSON() {		

		Map<String, Administrator> allAdmins = new HashMap<>();
		
		for (Administrator a : findAll()) {
			allAdmins.put(a.getUsername(),a);
		}

		Gson gs = new Gson();
		String json = gs.toJson(allAdmins);
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
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public boolean changeAdministrator(String username,User user) {
		loadAdmins("");
		System.out.println(user.getLastName());
		for (Administrator a : admins.values()) {
			if (a.getUsername().equals(user.getUsername())) {
				a.setFistName(user.getFistName());
				a.setLastName(user.getLastName());
				a.setPassword(user.getPassword());
				userDAO.changeUser(username,user);
				saveAdministratorsJSON();
				return true;
				
			}
		}
		
		return false;
		
	}
	
}

