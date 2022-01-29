package dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Cart;
import beans.CartItem;

public class CartDAO {
	
	public Map<String, Cart> carts = new HashMap<>();
	
	public String path = "C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\carts.json";
	public String basePath;

	public String fileName = "cart.json";
	
	public CartDAO() {
	}
	
	public CartDAO(String contextPath) {
		loadCarts(contextPath);
	}
	
	public void setBasePath(String path) {
		this.basePath = path;
		loadCarts("");
	}
	
	public String getPath() {
		return (this.basePath + "carts.json");
	}
	
	public void addCartForNewUser(String customerUsername) {
		Cart newCart = new Cart(new ArrayList<CartItem>(), customerUsername, 0.0);
		carts.put(customerUsername, newCart);
		
		saveCartsJSON();
	}
	
	public void deleteCart(String username) {
		if(carts.containsKey(username)) 
			carts.remove(username);
	}
	
	public void loadCarts(String contextPath) {
		
		Gson gs = new Gson();
		String cartsJson = "";
		
		try {
			cartsJson = new String(Files.readAllBytes(Paths.get(getPath())));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Type type = new TypeToken<Map<String, Cart>>() {}.getType();
		carts.clear();
		carts = gs.fromJson(cartsJson, type);

}
	public void saveCartsJSON() {

		Map<String, Cart> allcarts = new HashMap<>();
		for (Cart c : carts.values()) {
			allcarts.put(c.getCustomerID(),c);
		}

		Gson gs = new Gson();
		String json = gs.toJson(allcarts);
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
	


}
