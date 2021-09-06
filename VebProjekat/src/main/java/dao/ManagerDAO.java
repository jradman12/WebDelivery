package dao;

import java.io.FileNotFoundException;
import java.util.List;
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
import beans.Product;
import beans.Restaurant;
import beans.User;
import enums.Role;

public class ManagerDAO {
	
private static Map<String, Manager> managers = new HashMap<>();


	
	
	public ManagerDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	public ManagerDAO(String contextPath) {
		loadManagers(contextPath);
	}
	
	/**
	 * Vra�a korisnika za prosle�eno korisni�ko ime i �ifru. Vra�a null ako korisnik ne postoji
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
	
	public static Collection<Manager> findAll() {
		return managers.values();
	}
	
	public void updateManagersRest(Manager managerToUpdate, Restaurant newRest) {
		for(Manager m : managers.values()) {
			if(m.getUsername().equals(managerToUpdate.getUsername())) {
				m.setRestaurant(newRest);
			}
		}
		saveManagersJSON();
	}
	
	/**
	 * U�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Klju� je korisni�ko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public static void loadManagers(String contextPath) {
		
			
				Gson gs = new Gson();
				String managersJson = "";
				try {
					managersJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\managers.json")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Manager>>() {}.getType();
				managers.clear();
				managers = gs.fromJson(managersJson, type);
				
				//just to check it out 
				for(Map.Entry<String, Manager> entry : managers.entrySet()) {
					System.out.println(entry.getValue().getFistName());
				}

	}
	
	
	
	public static void saveManagersJSON() {

		String path="C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\managers.json";
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
		newManager.setRole(Role.MANAGER);
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
	
	public static Boolean changeManager(String username,User user) {

		loadManagers("");
		for (Manager m : managers.values()) {
			if (m.getUsername().equals(username)) {
				m.setFistName(user.getFistName());
				m.setLastName(user.getLastName());
				m.setPassword(user.getPassword());
				UserDAO.changeUser(username,user);
				saveManagersJSON();

				return true;
			}
		}
		return false;
	}
	
	public static Restaurant getRestaurantForManager(String username) {

		loadManagers("");
		for (Manager m : managers.values()) {
			if (m.getUsername().equals(username)) {
				System.out.println(m.getRestaurant());
				return m.getRestaurant();
			}
		}
		return null;
	}
	
	
	public static List<Product> getProductsForRestaurant(String username) {

		Restaurant r=getRestaurantForManager(username);
		return r.getMenu();
		
	}
	
	public static boolean addNewProductToManagersRestaurant(String username,Product product) {
		loadManagers("");
		for(Manager m : findAll()) {
			if(m.getUsername().equals(username)) {
				Product newProduct=new Product();
				newProduct.setName(product.getName());
				newProduct.setDeleted(false);
				newProduct.setDescription(product.getDescription());
				newProduct.setLogo(product.getLogo());
				newProduct.setPrice(product.getPrice());
				newProduct.setRestaurant(m.getRestaurant());
				newProduct.setType(product.getType());
				m.getRestaurant().getMenu().add(newProduct);
				
				saveManagersJSON();
				RestaurantDAO.addNewProduct(m.getRestaurant().getId(),product);
				return true;
				
			}
		}
		
		return false;
		
	}
	
	
	
	
	


}
