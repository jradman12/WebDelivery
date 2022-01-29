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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Order;
import beans.Restaurant;
import dto.OrderDTO;
import enums.OrderStatus;

public class OrderDAO {
	
public  Map<String, Order> orders = new HashMap<>();
public String path = "C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\orders.json";
public String basePath;

	public OrderDAO() {
		
	}
	
	public OrderDAO(String contextPath) {
		loadOrders(contextPath);
	}
	
	public void setBasePath(String path) {
		this.basePath = path;
		loadOrders("");
	}
	
	public String getPath() {
		return (this.basePath + "orders.json");
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
	
	public void deleteOrdersForUser(String username) {
		for(Order o : findAll()) {
			if (o.getCustomerID().equals(username)) 
				o.setDeleted(true);
		}
		saveOrdersJSON();
	}
	
	public void changeDeliverersOrderStatus(String orderId) {
		if (orders.containsKey(orderId)) {
			orders.get(orderId).setStatus(OrderStatus.AWAITING_DELIVERER);
			saveOrdersJSON();
		}
	}
	
	public void loadOrders(String contextPath) {
		
			
				Gson gs = new Gson();
				String ordersJson = "";
				try {
					ordersJson = new String(Files.readAllBytes(Paths.get(getPath())));
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
	
	
	public void addOrder(Order order) {
		if ( orders == null) {
			orders = new HashMap<>();
			orders.put(order.getId(), order);
		}
		else if (!orders.containsValue(order)) {
			orders.put(order.getId(), order);
		}
		
	}
	
	public void addNewOrder(Order order) {
		Order newOrder = new Order();
		if (orders == null )
			newOrder.setId(Integer.toString(1));
		else
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
		System.out.println("mijenjanje statusa porudzbine");
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
		RequestDAO requestDAO = new RequestDAO();
		requestDAO.setBasePath(basePath);
		for(String orderId : requestDAO.getIdsFromApprovedOrders(username)) {
			orderDel.add(findById(orderId));
		}
		
		
		return orderDel;
	}

	public Collection<OrderDTO> getOrdersWithRestDetails() {
		RestaurantDAO rDAO = new RestaurantDAO();
		rDAO.setBasePath(basePath);
		Collection<OrderDTO> ret = new ArrayList<OrderDTO>();
		for(Order o : orders.values()) {
			for(Restaurant r : rDAO.getAllAvailable()) {
				if(r.getId().equals(o.getRestaurant())) {
					ret.add(new OrderDTO(o, r.getName(), r.getTypeOfRestaurant()));
				}
			}
		}
		
		return ret;
	}
	
	
	public Collection<OrderDTO> getOrdersWithRestDetailsDeliverer(String username) {
		RestaurantDAO rDAO = new RestaurantDAO("");
		Collection<OrderDTO> ret = new ArrayList<OrderDTO>();
		for(Order o : getOrdersModifiedForDeliverer(username)) {
			for(Restaurant r : rDAO.getAllAvailable()) {
				if(r.getId().equals(o.getRestaurant())) {
					ret.add(new OrderDTO(o, r.getName(), r.getTypeOfRestaurant()));
				}
			}
		}
		
		return ret;
	}
	
	
	
	

	
	public  Collection<Order> delivererOrdersAA(String username){
		List<Order> myOrdersAA = new ArrayList<Order>();
		RequestDAO dao=new RequestDAO();
		dao.setBasePath(basePath);
		
		loadOrders("");
		for(Order o : orders.values()) {
			for(String s : dao.getIdsOfOrdersForDelivererWaitingRequests(username)) {
				if(o.getId().equals(s)) {
					myOrdersAA.add(o);
				}
			}
		}
		
		
		return myOrdersAA;
	}
	
	public  Collection<Order> getOrdersWithStatusAA() {
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
	
	
	public  Collection<Order> AADD(String username){
	
		List<Order> povratnaLista = new ArrayList<Order>();
		@SuppressWarnings("unused")
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
			
		
		for(Order or : delivererOrdersAA(username)) {
			
			povratnaLista.add(or);
		}
		
		for(Order o : razlika) {
			povratnaLista.add(o);
		}
		
	
		return povratnaLista;
		
		
	}
	
	public  Collection<Order> getOrdersModifiedForDeliverer(String username){
		List<Order> all = new ArrayList<Order>(); 
		
		for(Order o : getOrdersWithStatusAD()) {
			all.add(o);
		}
		
		for(Order or : AADD(username)) {
			all.add(or);
		}
		
		return all;
		
	}
	
	
	
	
	
	
	
	
	


}
