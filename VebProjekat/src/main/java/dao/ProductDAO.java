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
import beans.Product;


public class ProductDAO {
	
	/*private static Map<String, Product> products = new HashMap<>();


	
	
	public ProductDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	/*public ProductDAO(String contextPath) {
		loadProducts(contextPath);
	}
		
	public Collection<Product> findAll() {
		return products.values();
	}
	
	public void loadProducts(String contextPath) {
		
			
				Gson gs = new Gson();
				String productsJson = "";
				try {
					productsJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\products.json")));	
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Product>>() {}.getType();
				products.clear();
				products = gs.fromJson(productsJson, type);
			
	}
	
	
	
	public void saveProductsJSON() {

		String path="C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\products.json";
		Map<String, Product> allProducts = new HashMap<>();
		for (Product p : findAll()) {
			allProducts.put(p.getId(),p);
		}
		Gson gs = new Gson();
		String json = gs.toJson(allProducts);
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
	
	
	public void addProduct(Product product) {
		if (!products.containsValue(product)) {
			products.put(product.getId(), product);
		}
		
	}
	
	public void addNewProduct(Product product) {
		Product newProduct = new Product();
		newProduct.setDeleted(false);
		newProduct.setDescription(product.getDescription());
		newProduct.setId(product.getId());
		newProduct.setName(product.getId());
		newProduct.setPrice(product.getPrice());
		newProduct.setQuantity(product.getQuantity());
		newProduct.setRestaurant(product.getRestaurant());
		newProduct.setType(product.getType());
		addProduct(newProduct);
		saveProductsJSON();
	}
	
	
		
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }*/
	
	

}
