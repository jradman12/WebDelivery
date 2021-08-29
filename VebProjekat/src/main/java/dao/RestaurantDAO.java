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
import beans.Restaurant;

public class RestaurantDAO {
private static Map<String, Restaurant> restaurants = new HashMap<>();


	
	
	public RestaurantDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public RestaurantDAO(String contextPath) {
		loadRestaurants(contextPath);
	}
	
	public Restaurant findByName(String name) {
		for(Restaurant r : restaurants.values()) {
			if(r.getName().equals(name)) {
				return r;
			}
		}
		
		
		return null;
	}
	
	public Collection<Restaurant> findAll() {
		return restaurants.values();
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadRestaurants(String contextPath) {
		
				Gson gs = new Gson();
				String restaurantsJson = "";
				try {
					restaurantsJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\restaurants.json")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Restaurant>>() {}.getType();
				restaurants.clear();
				restaurants = gs.fromJson(restaurantsJson, type);
				
				//just to check it out 
				for(Map.Entry<String, Restaurant> entry : restaurants.entrySet()) {
					System.out.println(entry.getValue().getName());
				}
	}
		
		
	public void saveRestaurantsJSON() {

		String path="C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\restaurants.json";
		Map<String, Restaurant> allRestaurants = new HashMap<>();
		for (Restaurant r : findAll()) {
			allRestaurants.put(r.getId(),r);
		}
		Gson gs = new Gson();
		String json = gs.toJson(allRestaurants);
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
	
	
	public void addRestaurant(Restaurant restaurant) {
		if (!restaurants.containsValue(restaurant)) {
			restaurants.put(restaurant.getId(), restaurant);
		}
		
	}
	
	public void addNewRestaurant(Restaurant restaurant) {
		Restaurant newRestaurant = new Restaurant();
		newRestaurant.setName(restaurant.getName());
		newRestaurant.setId(restaurant.getId());
		newRestaurant.setMenu(restaurant.getMenu());
		newRestaurant.setLocation(restaurant.getLocation());
		newRestaurant.setStatus(restaurant.getStatus());
		newRestaurant.setTypeOfRestaurant(restaurant.getTypeOfRestaurant());
		addRestaurant(newRestaurant);
		saveRestaurantsJSON();
	}
	
	
		
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public Restaurant getRestaurantById(String id) {
		if (restaurants.containsKey(id)) {
			return restaurants.get(id);
		}

		return null;
	}
	
	

}
