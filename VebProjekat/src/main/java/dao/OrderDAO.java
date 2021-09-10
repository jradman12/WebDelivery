package dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Order;
import enums.OrderStatus;


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
		System.out.println("Ispisujemo ordere AD");
		for(Order o  : ordersWithStatusAD) {
			
			System.out.println(o.getId() + " " + o.getCustomer());
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
	
	
	public static Collection<Order> delivererOrdersAA(String username){
		List<Order> myOrdersAA = new ArrayList<Order>();
		loadOrders("");
		for(Order o : orders.values()) {
			for(String s : RequestDAO.getIdsOfOrdersForDelivererWaitingRequests(username)) {
				if(o.getId().equals(s)) {
					myOrdersAA.add(o);
				}
			}
		}
		
		System.out.println("Ispisujemo ordere AA za deliverera");
		for(Order o  : myOrdersAA) {
			
			System.out.println(o.getId() + " " + o.getCustomer());
		}
		
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA-deliverer");
		
		
		return myOrdersAA;
	}
	
	public static Collection<Order> getOrdersWithStatusAA() {
		loadOrders("");
		List<Order> ordersWithStatusAA=new ArrayList<Order>();
		for(Order o : orders.values()) {
			if(o.getStatus().equals(OrderStatus.AWAITING_APPROVING)) {
				ordersWithStatusAA.add(o);
			}
			
		}
		
		System.out.println("Ispisujemo sve ordere sa  AA");
		for(Order o  : ordersWithStatusAA) {
			
			System.out.println(o.toString());
		}
		
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA-----sve");
		
		return ordersWithStatusAA;
	}
	
	
	public static Collection<Order> AADD(String username){
	
		List<Order> povratnaLista = new ArrayList<Order>();
		List<Order> promijenjenaLista = new ArrayList<Order>();
		List<Order> razlika = new ArrayList<Order>();
		
		
		List<String> idSvihAA = new ArrayList<String>();
		List<String> idPorudzbinaZaKojeJeDostavljacPoslaoZahtjev = new ArrayList<String>(); 
		for(Order o :getOrdersWithStatusAA()) {
			idSvihAA.add(o.getId());
		}
		
		for(Order o : delivererOrdersAA(username)) {
			idPorudzbinaZaKojeJeDostavljacPoslaoZahtjev.add(o.getId());
		}
		
		Collections.sort(idSvihAA);
		Collections.sort(idPorudzbinaZaKojeJeDostavljacPoslaoZahtjev);
		idSvihAA.removeAll(idPorudzbinaZaKojeJeDostavljacPoslaoZahtjev);
		
		for(Order o : getOrdersWithStatusAA()) {
			for(String s : idSvihAA) {
			if(o.getId().equals(s)) {
				o.setStatus(OrderStatus.AWAITING_DELIVERER);
				razlika.add(o);
				}
			}
		}
			
		System.out.println("DOAA");
		for(Order or : delivererOrdersAA(username)) {
			System.out.println(or.toString());
			povratnaLista.add(or);
		}
		
		for(Order o : razlika) {
			povratnaLista.add(o);
		}
		System.out.println("DOAA - zavrsen");
		
		System.out.println("Ispisujemo listu sa AA->AD + AA za deliverera");
		for(Order o  : povratnaLista) {
			
			System.out.println(o.getId() + " " + o.getCustomer());
		}
		
		System.out.println("AD i AA za deliverera");
		return povratnaLista;
		
		
	}
	
	public static Collection<Order> getOrdersModifiedForDeliverer(String username){
		List<Order> all = new ArrayList<Order>(); 
		
		for(Order o : getOrdersWithStatusAD()) {
			all.add(o);
		}
		
		for(Order or : AADD(username)) {
			all.add(or);
		}
		
		System.out.println("Ispisujemo glavna povratna ");
		for(Order o  : all) {
			
			System.out.println(o.getId() + " " + o.getCustomer());
		}
		
		System.out.println("KONACNA LISTA");
		return all;
		
	}
	
	
	
	
	
	
	
	
	
	

}
