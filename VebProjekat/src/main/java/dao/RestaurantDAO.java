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
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Product;
import beans.Restaurant;
import dto.RestaurantDTO;
import enums.RestaurantStatus;

public class RestaurantDAO {
	
	public  Map<String, Restaurant> restaurants = new HashMap<String, Restaurant>();
	public String path = "C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\restaurants.json";
	public String basePath;

	public RestaurantDAO() {
		
	}
	
	public RestaurantDAO(String contextPath) {
		loadRestaurants(contextPath);
	}
	
	
	public void setBasePath(String path) {
		this.basePath = path;
		loadRestaurants("");
	}
	
	public String getPath() {
		return (this.basePath + "restaurants.json");
	}
	
	
	public Restaurant findByName(String name) {
		for(Restaurant r : getAllAvailable()) {
			if(r.getName().equals(name)) {
				return r;
			}
		}
		
		return null;
	}
	
	public Collection<Restaurant> getAllAvailable(){
		Collection<Restaurant> availableUsers = new ArrayList<Restaurant>();
		loadRestaurants("");
		for(Restaurant u : restaurants.values()) {
			if(!u.isDeleted()) 
				availableUsers.add(u);
		}
		return availableUsers;
	}
	
	
	public  Collection<Restaurant> findAll() {
		return restaurants.values();
	}
	
	public void loadRestaurants(String contextPath) {
		
				Gson gs = new Gson();
				String restaurantsJson = "";
				try {
					restaurantsJson = new String(Files.readAllBytes(Paths.get(getPath())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Restaurant>>() {}.getType();
				restaurants.clear();
				restaurants = gs.fromJson(restaurantsJson, type);
				
				//just to check it out 
				//for(Map.Entry<String, Restaurant> entry : restaurants.entrySet()) {
					//System.out.println(entry.getValue().getName());
			//	}
				
				//dodajParRestorana();
	}
		
		
//	private static void dodajParRestorana() {
//		Restaurant r1 = new Restaurant();
//		r1.setName("Ciao pizzeria");
//		r1.setLocation(new Location(0.0,0.0,new Address("Nikole Tesle6/22","Janja","76316")));
//		r1.setLogo("images/kfc2.jpg");
//		r1.setMenu(new ArrayList<Product>());
//		r1.setStatus(RestaurantStatus.OPEN);
//		r1.setTypeOfRestaurant("domaci");
//		r1.setId(generateNextId());
//		r1.setStatus(RestaurantStatus.OPEN);	
//		Restaurant r2 = new Restaurant();
//		r2.setName("Ciao pizzeria");
//		r2.setLocation(new Location(0.0,0.0,new Address("Nikole Tesle6/22","Janja","76316")));
//		r2.setLogo("images/kfc2.jpg");
//		r2.setMenu(new ArrayList<Product>());
//		r2.setStatus(RestaurantStatus.OPEN);
//		r2.setTypeOfRestaurant("domaci");
//		r2.setId(generateNextId());
//		r2.setStatus(RestaurantStatus.OPEN);
//		addNewRestaurant(r1);
//		addNewRestaurant(r2);
//	}
	
	public String generateNextId() {
		return Integer.toString(restaurants.size() + 1);
	}

	public  void saveRestaurantsJSON() {

		Map<String, Restaurant> allRestaurants = new HashMap<>();
		for (Restaurant r : findAll()) {
			allRestaurants.put(r.getId(),r);
		}
		Gson gs = new Gson();
		String json = gs.toJson(allRestaurants);
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
	
	
	public void addRestaurant(Restaurant restaurant) {
		if (!restaurants.containsValue(restaurant)) {
			restaurants.put(restaurant.getId(), restaurant);
		}
		
	}
	
	public String addNewRestaurant(RestaurantDTO restaurant) {
		String id = generateNextId();
		Restaurant newRestaurant = new Restaurant();
		newRestaurant.setName(restaurant.getName());
		newRestaurant.setId(id);
		newRestaurant.setMenu(restaurant.getMenu());
		newRestaurant.setLocation(restaurant.getLocation());
		newRestaurant.setStatus(RestaurantStatus.OPEN);
		newRestaurant.setTypeOfRestaurant(restaurant.getTypeOfRestaurant());
		newRestaurant.setAverageRating(0.0);	
		newRestaurant.setLogo(restaurant.getLogo());
		newRestaurant.setDeleted(false);
		addRestaurant(newRestaurant);
		saveRestaurantsJSON();
		return id;
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
	
	public boolean deleteRestaurant(String id) {
		for(Restaurant r : findAll()) {
			if(r.getId().equals(id)) {
				r.setDeleted(true);
				saveRestaurantsJSON();
				return true;
			}
		}
		
		return false;
		
	}

	public  void addNewProduct(String id, Product product) {
		// TODO Auto-generated method stub
		loadRestaurants("");
		for(Restaurant r : getAllAvailable()) {
			if(r.getId().equals(id)) {
				Product newProduct=new Product();
				newProduct.setName(product.getName());
				newProduct.setDeleted(false);
				newProduct.setDescription(product.getDescription());
				newProduct.setLogo(product.getLogo());
				newProduct.setPrice(product.getPrice());
				newProduct.setType(product.getType());
				newProduct.setRestaurantID(id);
				if(product.getQuantity() != 0) {
					
					newProduct.setQuantity(product.getQuantity());
				}else {
					newProduct.setQuantity(0);
				}
				r.getMenu().add(newProduct);
				saveRestaurantsJSON();
				return;
				
			}
		}
	}
	
	public Product getProductByName(String restID, String productName) {
		for(Product p : restaurants.get(restID).getMenu()) {
			if(p.getName().equals(productName))
				return p;
		}
		return null;
	}
	
	public void updateProduct(String restID, Product updatedProduct) {
		int index = 0;
		for(Restaurant r : restaurants.values()) {
			if(r.getId().equals(restID)) {
				for(Product p : r.getMenu()) {
					if(p.getName().equals(updatedProduct.getName())){
						index = r.getMenu().indexOf(p);
						System.out.println("Indeks proizvoda je: " + index);
						if(updatedProduct.getLogo() == null) updatedProduct.setLogo(p.getLogo());
					}
				}
				r.getMenu().set(index, updatedProduct);
				saveRestaurantsJSON();
				return;
			}
		}
	}
	
	
	public void updateRating(String id) {
		CommentDAO commentDAO = new CommentDAO();
		commentDAO.setBasePath(basePath);
		for(Restaurant r : restaurants.values()) {
			if(r.getId().equals(id)) {
				System.out.println("PRVA");
				r.setAverageRating(commentDAO.averageRatingForRestaurant(id));
				System.out.println("Srednja ocjena restorana je " + r.getAverageRating());
				saveRestaurantsJSON();
				return;
				
			}
		}
		
	}
	
	
	
	

	
}
	
	


