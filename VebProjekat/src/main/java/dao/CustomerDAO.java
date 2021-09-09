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

public static Map<String, Customer> customers = new HashMap<>();


	
	
	public CustomerDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	public CustomerDAO(String contextPath) {
		loadCustomers(contextPath);
	}
	
	/**
	 * Vra�a korisnika za prosle�eno korisni�ko ime i �ifru. Vra�a null ako korisnik ne postoji
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
	
	public static Collection<Customer> findAll() {
		return customers.values();
	}
	
	/**
	 * U�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Klju� je korisni�ko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public static void loadCustomers(String contextPath) {
		
				Gson gs = new Gson();
				String customersJson = "";
				try {
					customersJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json")));
					
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

	}
	
	
	
	public static void saveCustomersJSON() {

			System.out.println("saving customers... these:" );
			for(Map.Entry<String, Customer> entry : customers.entrySet()) {
				System.out.println(entry.getValue().getUsername());
			}
		
		String path="C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json";
		Map<String, Customer> allCustomers = new HashMap<>();
		for (Customer c : findAll()) {
			allCustomers.put(c.getUsername(),c);
		}

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
		User newUser = new User(customer.getUsername(),customer.getPassword(),customer.getFistName(),customer.getLastName(),customer.getGender(),customer.getDateOfBirth(),Role.CUSTOMER,false,false);
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
	
	public static Boolean changeCustomer(String username,User user) {

		// Find user with that name, and change his data.
		loadCustomers("");
		for (Customer c : customers.values()) {
			if (c.getUsername().equals(user.getUsername())) {
				c.setFistName(user.getFistName());
				c.setLastName(user.getLastName());
				c.setPassword(user.getPassword());
				UserDAO.changeUser(username,user);
				saveCustomersJSON();

				return true;
			}
		}
		return false;
	}
	
}
