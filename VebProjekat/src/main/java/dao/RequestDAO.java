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
import java.util.*;
import beans.DeliverRequest;

import enums.OrderStatus;
import enums.RequestStatus;


public class RequestDAO {

	
public  Map<String,DeliverRequest> requests = new HashMap<>();
public String path = "C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\requests.json";
public String basePath;

	
	public RequestDAO() {
		
	}
	
	
	public RequestDAO(String contextPath) {
		loadRequests(contextPath);
	}
	
	
	public void setBasePath(String path) {
		this.basePath = path;
		loadRequests("");
	}
	
	public String getPath() {
		return (this.basePath + "requests.json");
	}
	
	public Collection<DeliverRequest> findByRestaurant(String id) {
		List<DeliverRequest> requestsForRestaurant = new ArrayList<DeliverRequest>();
		loadRequests("");
		for(DeliverRequest dr : requests.values()) {
			if(dr.getRestaurantID().equals(id)) {
				requestsForRestaurant.add(dr);
			}
		}
		
		return requestsForRestaurant;
		
		
	}
	
	public Collection<DeliverRequest> findAll() {
		return requests.values();
	}
	

	public void loadRequests(String contextPath) {
		
				Gson gs = new Gson();
				String requestsJson = "";
				try {
					requestsJson = new String(Files.readAllBytes(Paths.get(getPath())));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, DeliverRequest>>() {}.getType();
				requests.clear();
				requests = gs.fromJson(requestsJson, type);
				
				//just to check it out 
//				for(Map.Entry<String, DeliverRequest> entry : requests.entrySet()) {
//					System.out.println(entry.getValue().getOrderID());
//				}
	}
	
	
	
	public  void saveRequestsJson() {
		
		Map<String, DeliverRequest> allRequests = new HashMap<>();
		for (DeliverRequest dr : findAll()) {
			allRequests.put(dr.getId(),dr);
		}

		Gson gs = new Gson();
		String json = gs.toJson(allRequests);
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
	
	

	public  void addDeliverRequest(DeliverRequest request) {
		
//		for(DeliverRequest d : requests.values()) {
//			if(!(d.getOrderID().equals(request.getOrderID()) && d.getDelivererID().equals(request.getDelivererID()))){
//				System.out.println("DODAJE SEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
//				requests.put(request.getId(), request);
//			}
//		}
//		

		
		if(!requests.containsValue(request)) {
			requests.put(request.getId(), request);
			
		}
	
	}
	

	public  void addNewRequest(DeliverRequest request) {

		DeliverRequest newRequest = new DeliverRequest();
		newRequest.setRestaurantID(request.getRestaurantID());
		newRequest.setOrderID(request.getOrderID());
		newRequest.setStatus(RequestStatus.WAITING);
		newRequest.setDelivererID(request.getDelivererID());
		newRequest.setId(generateNextId());
		addDeliverRequest(newRequest);
		saveRequestsJson();
	}
	
	
		
	
	public  String generateNextId() {
		return Integer.toString(requests.size() + 1);
	}

	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	

	
	public  Boolean setManager(String requestId,String manager) {

		for (DeliverRequest d : requests.values()) {
			if (d.getId().equals(requestId)) {
				d.setManagerID(manager);
				
				saveRequestsJson();

				return true;
			}
		}
		return false;
	}
	
	public  Boolean approve(String requestId,String managerId) {
		OrderDAO orderDAO = new OrderDAO();
		orderDAO.setBasePath(basePath);
		for (DeliverRequest d : requests.values()) {
			if (d.getId().equals(requestId)) {
				d.setStatus(RequestStatus.APPROVED);
				d.setManagerID(managerId);
				saveRequestsJson();
				orderDAO.changeStatus(OrderStatus.SHIPPING, d.getOrderID());

				return true;
			}
		}
		return false;
	}
	
	public  Boolean decline(String requestId) {
		for (DeliverRequest d : requests.values()) {
			if (d.getId().equals(requestId)) {
				d.setStatus(RequestStatus.REJECTED);
				
				saveRequestsJson();

				return true;
			}
		}
		return false;
	}
	
	public  Collection<DeliverRequest> requestsForRestaurantsOrder(String id){
		List<DeliverRequest> requestsD = new ArrayList<DeliverRequest>();
		for (DeliverRequest d : requests.values()) {
			if (d.getRestaurantID().equals(id) /*&& d.getStatus().equals(RequestStatus.WAITING)*/) {
				requestsD.add(d);
				
			}
		}
		
		return requestsD;
		
	}
	
	public  Collection<DeliverRequest> allDeliverersRequests(String id){
		List<DeliverRequest> requestsD = new ArrayList<DeliverRequest>();
		for (DeliverRequest d : requests.values()) {
			if (d.getDelivererID().equals(id) /*&& d.getStatus().equals(RequestStatus.WAITING)*/) {
				requestsD.add(d);
				
			}
		}
		
		return requestsD;
		
	}
	
	public  Collection<String> getIdsFromApprovedOrders(String usernameOfDeliverer){
		List<String> ordersId = new ArrayList<String>();
		for(DeliverRequest r : requests.values()) {
			if(r.getDelivererID().equals(usernameOfDeliverer) && r.getStatus().equals(RequestStatus.APPROVED)) {
				ordersId.add(r.getOrderID());
			}
		}
		
		return ordersId;
	}
	
	public  boolean existsRequest(String oId,String username) {
		for(DeliverRequest dr : requests.values()) {
			if(dr.getOrderID().equals(oId) && dr.getDelivererID().equals(username)) {
				return true;
			}
		}
		
		return false;
	}
	
	public  Collection<String> getIdsOfOrdersForDelivererWaitingRequests(String username){
		loadRequests("");
		List<String> myAAIds = new ArrayList<String>();
		for(DeliverRequest dr : requests.values())
		{
			if(dr.getDelivererID().equals(username) && dr.getStatus().equals(RequestStatus.WAITING)) {
				myAAIds.add(dr.getOrderID());
			}
		}
		
		return myAAIds;
		
	}
}

