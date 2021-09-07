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

import beans.Comment;
import beans.Order;
import enums.OrderStatus;
import enums.StatusOfComment;

import java.util.*;

public class OrderDAO {
	
public static Map<String, Order> orders = new HashMap<>();


	
	
	public OrderDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	public OrderDAO(String contextPath) {
		loadOrders(contextPath);
	}
	
	public static Order findById(String id) {
		if (!orders.containsKey(id)) {
			return null;
		}
		Order order = orders.get(id);
		return order;
	}
	
	public static Collection<Order> findAll() {
		return orders.values();
	}
	
	public static void loadOrders(String contextPath) {
		
			
				Gson gs = new Gson();
				String ordersJson = "";
				try {
					ordersJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\orders.json")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Order>>() {}.getType();
				orders.clear();
				orders = gs.fromJson(ordersJson, type);
				
				//just to check it out 
				for(Map.Entry<String, Order> entry : orders.entrySet()) {
					System.out.println(entry.getValue().getId());
				}
	}
	
	
	
	public static void saveOrdersJSON() {

		String path="C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\orders.json";
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
		newOrder.setId(order.getId());
		newOrder.setRestaurant(order.getRestaurant());
		newOrder.setDateAndTime(order.getDateAndTime());
		newOrder.setCustomer(order.getCustomer());
		newOrder.setOrderedItems(order.getOrderedItems());
		newOrder.setPrice(order.getPrice());
		newOrder.setStatus(OrderStatus.PENDING);
		addNewOrder(newOrder);
		saveOrdersJSON();
	}
	
	
		
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public static Collection<Order> getOrdersForRestaurant(String idOfRestaurant) {
		loadOrders("");
		List<Order> ordersForRestaurant=new ArrayList<Order>();
		for(Order o : orders.values()) {
			if(o.getRestaurant().equals(idOfRestaurant)) {
				ordersForRestaurant.add(o);
			}
			
		}
		
		return ordersForRestaurant;
	}
	
	public static boolean changeStatus(OrderStatus status,String id) {
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
	
	public static Collection<Order> getOrdersWithStatusAD() {
		loadOrders("");
		List<Order> ordersWithStatusAD=new ArrayList<Order>();
		for(Order o : orders.values()) {
			if(o.getStatus().equals(OrderStatus.AWAITING_DELIVERER)) {
				ordersWithStatusAD.add(o);
			}
			
		}
		
		return ordersWithStatusAD;
	}
	
	public static String getRestaurantForOrder(String orderID) {
		loadOrders("");
		for(Order o : orders.values()) {
			if(o.getId().equals(orderID)) {
				return o.getRestaurant();
			}
		}
		
		return null;
	}
	
	public static Collection<Order> getApprovedOrdersForDeliver(String username) {
		loadOrders("");
		List<Order> orderDel=new ArrayList<Order>();
		for(String orderId : RequestDAO.getIdsFromApprovedOrders(username)) {
			orderDel.add(findById(orderId));
		}
		
		
		return orderDel;
	}
	
	
	
	
	
	

}
