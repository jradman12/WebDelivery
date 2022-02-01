package dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import beans.Deliverer;
import beans.Order;
import beans.User;
import enums.Role;

public class DelivererDAO {

	
private  Map<String,Deliverer> deliverers = new HashMap<String,Deliverer>();

public String path = "C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\deliverers.json";
public String basePath;


	public DelivererDAO() { 
		
	}
	
	public DelivererDAO(String contextPath) {
		loadDeliverers(contextPath);
	}
	
	public void setBasePath(String path) {
		this.basePath = path;
		loadDeliverers("");
	}
	
	public String getPath() {
		return (this.basePath + "deliverers.json");
	}
	
	public List<Order> getDeliverersOrders(String username) {
		if(deliverers.containsKey(username))
			return deliverers.get(username).getOrders();
		return null;
	}
	
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
	
	public void blockUser(String username) {
		if (deliverers.containsKey(username)) {
			deliverers.get(username).setBlocked(true);
			saveDeliverersJSON();
		}
	}
	
	public Collection<Deliverer> getAllAvailable(){
		Collection<Deliverer> availableUsers = new ArrayList<Deliverer>();
		for(Deliverer u : deliverers.values()) {
			if(!u.isDeleted()) 
				availableUsers.add(u);
		}
		return availableUsers;
	}
	
	public void deleteDeliverer(String userID) {
		deliverers.get(userID).setDeleted(true);
		saveDeliverersJSON();
	}
	
	public Collection<Deliverer> findAll() {
		return deliverers.values();
	}
	
	
	public void loadDeliverers(String contextPath) {
		
				Gson gs = new Gson();
				String deliverersJson = "";
				try {
					deliverersJson = new String(Files.readAllBytes(Paths.get(getPath())));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Deliverer>>() {}.getType();
				deliverers.clear();
				deliverers = gs.fromJson(deliverersJson, type);
				
				//just to check it out 
//				for(Map.Entry<String, Deliverer> entry : deliverers.entrySet()) {
//					System.out.println(entry.getValue().getFistName());
//				}
	}
	
	
	
	public void saveDeliverersJSON() {
		
		Map<String, Deliverer> allDeliverers = new HashMap<>();
		
		for (Deliverer d : findAll()) {
			allDeliverers.put(d.getUsername(),d);
		}

		Gson gs = new Gson();
		String json = gs.toJson(allDeliverers);
		byte[] inBytes = json.getBytes();
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(getPath());
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
	
	public  Boolean changeDeliverer(String username,User user) {

		for (Deliverer d : deliverers.values()) {
			if (d.getUsername().equals(username)) {
				d.setFistName(user.getFistName());
				d.setLastName(user.getLastName());
				d.setPassword(user.getPassword());
				UserDAO userDAO = new UserDAO();
				userDAO.setBasePath(basePath);
				userDAO.changeUser(username,user);
				saveDeliverersJSON();

				return true;
			}
		}
		return false;
	}
}
