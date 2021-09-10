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

import beans.Order;
import enums.OrderStatus;


import java.util.*;

public class OrderDAO {
	
public  Map<String, Order> orders = new HashMap<>();
public String path = "C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\orders.json";
	
	public OrderDAO() {
		
	}
	
	public OrderDAO(String contextPath) {
		loadOrders(contextPath);
	}
	
	public Order findById(String id) {
		if (!orders.containsKey(id)) {
			return null;
		}
		Order order = orders.get(id);
		return order;
	}
	
	public Collection<Order> findAll() {
		return orders.values();
	}
	
	public Collection<Order> getExistingOrders() {
		 Collection<Order> os = new  ArrayList<Order>();
		 for(Order o : orders.values()) {
			 if(!o.isDeleted())
				 os.add(o);
		 }
		 return os;
	}
	
	
	public void loadOrders(String contextPath) {
		
			
				Gson gs = new Gson();
				String ordersJson = "";
				try {
					ordersJson = new String(Files.readAllBytes(Paths.get(path)));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Order>>() {}.getType();
				orders.clear();
				orders = gs.fromJson(ordersJson, type);
				
				//just to check it out 
//				for(Map.Entry<String, Order> entry : orders.entrySet()) {
//					System.out.println(entry.getValue().getId());
//				}
	}
	
	
	
	public void saveOrdersJSON() {

		Map<String, Order> allOrders = new HashMap<>();
		for (Order o : findAll()) {
			allOrders.put(o.getId(),o);
		}
		Gson gs = new Gson();
		String json = gs.toJson(allOrders);
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
	
	
	public void addOrder(Order order) {
		if (!orders.containsValue(order)) {
			orders.put(order.getId(), order);
		}
		
	}
	
	public void addNewOrder(Order order) {
		Order newOrder = new Order();
		newOrder.setId(Integer.toString(orders.size() + 1));
		newOrder.setRestaurant(order.getRestaurant());
		newOrder.setDateAndTime(order.getDateAndTime());
		newOrder.setCustomerID(order.getCustomerID());
		newOrder.setOrderedItems(order.getOrderedItems());
		newOrder.setPrice(order.getPrice());
		newOrder.setStatus(OrderStatus.PENDING);
		addOrder(newOrder);
		saveOrdersJSON();
	}
	
	
		
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public  Collection<Order> getOrdersForRestaurant(String idOfRestaurant) {
		List<Order> ordersForRestaurant = new ArrayList<Order>();
		for(Order o : orders.values()) {
			if(o.getRestaurant().equals(idOfRestaurant)) {
				ordersForRestaurant.add(o);
			}
			
		}
		
		return ordersForRestaurant;
	}
	
	public  boolean changeStatus(OrderStatus status,String id) {
		loadOrders("");
		for(Order o : orders.values()) {
			if(o.getId().equals(id)) {
				o.setStatus(status);
				System.out.println(o.getStatus());
				saveOrdersJSON();
				return true;
			}
		}
		
		return false;
	}
	
	public  Collection<Order> getOrdersWithStatusAD() {
		List<Order> ordersWithStatusAD=new ArrayList<Order>();
		for(Order o : orders.values()) {
			if(o.getStatus().equals(OrderStatus.AWAITING_DELIVERER)) {
				ordersWithStatusAD.add(o);
			}
			
		}
		
		return ordersWithStatusAD;
	}
	
	public  String getRestaurantForOrder(String orderID) {
		for(Order o : orders.values()) {
			if(o.getId().equals(orderID)) {
				return o.getRestaurant();
			}
		}
		
		return null;
	}
	
	public  Collection<Order> getApprovedOrdersForDeliver(String username) {
		List<Order> orderDel=new ArrayList<Order>();
		RequestDAO requestDAO = new RequestDAO("");
		for(String orderId : requestDAO.getIdsFromApprovedOrders(username)) {
			orderDel.add(findById(orderId));
		}
		
		
		return orderDel;
	}
	

}
