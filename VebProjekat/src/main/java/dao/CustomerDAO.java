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

import beans.Customer;
import beans.User;
import enums.Role;

public class CustomerDAO {

private static Map<String, Customer> customers = new HashMap<>();


	
	
	public CustomerDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public CustomerDAO(String contextPath) {
		loadCustomers(contextPath);
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	public Customer find(String username, String password) {
		if (!customers.containsKey(username)) {
			return null;
		}
		Customer customer = customers.get(username);
		if (!customer.getPassword().equals(password)) {
			return null;
		}
		return customer;
	}
	
	public Collection<Customer> findAll() {
		return customers.values();
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadCustomers(String contextPath) {
		
			
				Gson gs = new Gson();

				
				String customersJson = "";
				try {
					customersJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json")));
					//customersJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json")));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Customer>>() {}.getType();
				customers.clear();
				customers = gs.fromJson(customersJson, type);
				
				//just to check it out 
				for(Map.Entry<String, Customer> entry : customers.entrySet()) {
					System.out.println(entry.getValue().getFistName());
				}
		
		/*ObjectMapper objectMapper = new ObjectMapper();
		//String path="C:\\Users\\hp\\Desktop\\web-proj\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json";
		String path = "C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json";
		

		Map<String, Customer> customersMap = new HashMap<>();
		try {

			customersMap = (Map<String, Customer>) objectMapper.readValue(path, new TypeReference<Map<String,Customer>>() {});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\n\n ucitavam preko object mapera \n\n");
		for (Customer c : customersMap.values()) {
			System.out.println("ime: " + c.getUsername());
			customersMap.put(c.getUsername(), c);
		}
		System.out.println("\n\n");*/

	}
	
	
	
	public void saveCustomersJSON() {

		//String path="C:\\Users\\hp\\Desktop\\web-proj\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json";
		String path="C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json";
		// Get all users
		/*List<Customer> allCustomers = new ArrayList<Customer>();
		for (Customer c : getValues()) {
			allCustomers.add(c);
		}*/
		
		Map<String, Customer> allCustomers = new HashMap<>();
		for (Customer c : findAll()) {
			allCustomers.put(c.getUsername(),c);
		}

//		ObjectMapper objectMapper = new ObjectMapper();
//		//Gson gson=new GsonBuilder().setPrettyPrinting().create();
//		try {
//			// Write them to the file
//			objectMapper.writeValue(new FileOutputStream(path), allCustomers);
//			/*FileWriter writer = new FileWriter(path);
//			gson.toJson(allCustomers,writer);
//			writer.close();*/
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		Gson gs = new Gson();
		String json = gs.toJson(allCustomers);
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
	
	
	public void addCustomer(Customer customer) {
		if (!customers.containsValue(customer)) {
			customers.put(customer.getUsername(), customer);
		}
		
	}
	
	public void addNewCustomer(Customer customer) {
		Customer newCustomer = new Customer();
		User newUser = new User(customer.getUsername(),customer.getPassword(),customer.getFistName(),customer.getLastName(),customer.getGender(),customer.getDateOfBirth(),customer.getRole(),false,false);
		newCustomer.setFistName(customer.getFistName());
		newCustomer.setLastName(customer.getLastName());
		newCustomer.setUsername(customer.getUsername());
		newCustomer.setPassword(customer.getPassword());
		newCustomer.setDateOfBirth(customer.getDateOfBirth());
		newCustomer.setGender(customer.getGender());
		newCustomer.setRole(Role.CUSTOMER);
		newCustomer.setDeleted(false);
		newCustomer.setBlocked(false);
		addCustomer(newCustomer);
		UserDAO.addNewUser(newUser);
		saveCustomersJSON();
	}
	
	
		
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public Customer getCustomerByUsername(String username) {
		if (customers.containsKey(username)) {
			return customers.get(username);
		}

		return null;
	}
	
}
