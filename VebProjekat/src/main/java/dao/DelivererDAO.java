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
import beans.Deliverer;
import beans.User;
import enums.Role;

public class DelivererDAO {

	
private static Map<String,Deliverer> deliverers = new HashMap<>();


	
	
	public DelivererDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	public DelivererDAO(String contextPath) {
		loadDeliverers(contextPath);
	}
	
	/**
	 * Vra�a korisnika za prosle�eno korisni�ko ime i �ifru. Vra�a null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	public Deliverer find(String username, String password) {
		if (!deliverers.containsKey(username)) {
			return null;
		}
		Deliverer deliverer = deliverers.get(username);
		if (!deliverer.getPassword().equals(password)) {
			return null;
		}
		return deliverer;
	}
	
	public static Collection<Deliverer> findAll() {
		return deliverers.values();
	}
	
	/**
	 * U�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Klju� je korisni�ko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public static void loadDeliverers(String contextPath) {
		
				Gson gs = new Gson();
				String deliverersJson = "";
				try {
					deliverersJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\deliverers.json")));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Deliverer>>() {}.getType();
				deliverers.clear();
				deliverers = gs.fromJson(deliverersJson, type);
				
				//just to check it out 
				for(Map.Entry<String, Deliverer> entry : deliverers.entrySet()) {
					System.out.println(entry.getValue().getFistName());
				}
	}
	
	
	
	public static void saveDeliverersJSON() {
		
		String path="C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\deliverers.json";
		Map<String, Deliverer> allDeliverers = new HashMap<>();
		for (Deliverer d : findAll()) {
			allDeliverers.put(d.getUsername(),d);
		}

		Gson gs = new Gson();
		String json = gs.toJson(allDeliverers);
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
	
	
	public void addDeliverer(Deliverer deliverer) {
		if (!deliverers.containsValue(deliverer)) {
			deliverers.put(deliverer.getUsername(), deliverer);
		}
		
	}
	
	public void addNewDeliverer(Deliverer deliverer) {
		Deliverer newDeliverer = new Deliverer();
		User newUser = new User(deliverer.getUsername(),deliverer.getPassword(),deliverer.getFistName(),deliverer.getLastName(),deliverer.getGender(),deliverer.getDateOfBirth(),Role.DELIVERER,false,false);
		newDeliverer.setFistName(deliverer.getFistName());
		newDeliverer.setLastName(deliverer.getLastName());
		newDeliverer.setUsername(deliverer.getUsername());
		newDeliverer.setPassword(deliverer.getPassword());
		newDeliverer.setDateOfBirth(deliverer.getDateOfBirth());
		newDeliverer.setGender(deliverer.getGender());
		newDeliverer.setRole(Role.DELIVERER);
		newDeliverer.setDeleted(false);
		newDeliverer.setBlocked(false);
		addDeliverer(newDeliverer);
		UserDAO.addNewUser(newUser);
		saveDeliverersJSON();
	}
	
	
		
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public Deliverer getDelivererByUsername(String username) {
		if (deliverers.containsKey(username)) {
			return deliverers.get(username);
		}

		return null;
	}
	
	public static Boolean changeDeliverer(User user) {

		loadDeliverers("");
		for (Deliverer d : deliverers.values()) {
			if (d.getUsername().equals(user.getUsername())) {
				d.setFistName(user.getFistName());
				d.setLastName(user.getLastName());
				d.setPassword(user.getPassword());
				UserDAO.changeUser(user);
				saveDeliverersJSON();

				return true;
			}
		}
		return false;
	}
}
