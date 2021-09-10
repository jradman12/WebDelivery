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

import beans.Customer;
import beans.User;
import enums.Role;

public class CustomerDAO {

public Map<String, Customer> customers = new HashMap<>();
public String path = "C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\customers.json";
private UserDAO userDAO = new UserDAO();


	public CustomerDAO() {
		
	}
	
	
	public CustomerDAO(String contextPath) {
		loadCustomers(contextPath);
	}
	
	
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
	
	public Collection<Customer> getAllAvailable(){
		Collection<Customer> availableCustomers = new ArrayList<Customer>();
		for(Customer u : customers.values()) {
			if(!u.isDeleted()) 
				availableCustomers.add(u);
		}
		return availableCustomers;
	}
	
	public void deleteCustomer(String userID) {
		customers.get(userID).setDeleted(true);
	}
	
	public void loadCustomers(String contextPath) {
		
				Gson gs = new Gson();
				String customersJson = "";
				try {
					customersJson = new String(Files.readAllBytes(Paths.get(path)));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Customer>>() {}.getType();
				customers.clear();
				customers = gs.fromJson(customersJson, type);
				
				//just to check it out 
//				for(Map.Entry<String, Customer> entry : customers.entrySet()) {
//					System.out.println(entry.getValue().getFistName());
//				}

	}
	
	
	
	public  void saveCustomersJSON() {
		
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
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
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
		userDAO.addNewUser(newUser);
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
	
	public  boolean changeCustomer(String username,User user) {

		for (Customer c : customers.values()) {
			if (c.getUsername().equals(user.getUsername())) {
				c.setFistName(user.getFistName());
				c.setLastName(user.getLastName());
				c.setPassword(user.getPassword());
				userDAO.changeUser(username,user);
				saveCustomersJSON();
				return true;
			}
		}
		return false;
	}
	
}
