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
import beans.Deliverer;
import enums.OrderStatus;
import enums.RequestStatus;


public class RequestDAO {

	
public static Map<String,DeliverRequest> requests = new HashMap<>();


	
	
	public RequestDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	public RequestDAO(String contextPath) {
		loadRequests(contextPath);
	}
	
	/**
	 * Vra�a korisnika za prosle�eno korisni�ko ime i �ifru. Vra�a null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
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
	
	public static Collection<DeliverRequest> findAll() {
		return requests.values();
	}
	
	/**
	 * U�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Klju� je korisni�ko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public static void loadRequests(String contextPath) {
		
				Gson gs = new Gson();
				String requestsJson = "";
				try {
					requestsJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\requests.json")));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, DeliverRequest>>() {}.getType();
				requests.clear();
				requests = gs.fromJson(requestsJson, type);
				
				//just to check it out 
				for(Map.Entry<String, DeliverRequest> entry : requests.entrySet()) {
					System.out.println(entry.getValue().getOrderID());
				}
	}
	
	
	
	public static void saveRequestsJson() {
		
		String path="C:\\Users\\hp\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\requests.json";
		Map<String, DeliverRequest> allRequests = new HashMap<>();
		for (DeliverRequest dr : findAll()) {
			allRequests.put(dr.getId(),dr);
		}

		Gson gs = new Gson();
		String json = gs.toJson(allRequests);
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
	
	
	public static void addDeliverRequest(DeliverRequest dr) {
		if (!requests.containsValue(dr)) {
			requests.put(dr.getId(), dr);
		}
		
	}
	
	public static void addNewRequest(DeliverRequest request) {
		DeliverRequest newRequest = new DeliverRequest();
		newRequest.setRestaurantID(request.getRestaurantID());
		newRequest.setOrderID(request.getOrderID());
		newRequest.setStatus(RequestStatus.WAITING);
		newRequest.setDelivererID(request.getDelivererID());
		newRequest.setId(generateNextId());
		addDeliverRequest(newRequest);
		saveRequestsJson();
	}
	
	
		
	
	public static String generateNextId() {
		return Integer.toString(requests.size() + 1);
	}

	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	

	
	public static Boolean setManager(String requestId,String manager) {

		loadRequests("");
		for (DeliverRequest d : requests.values()) {
			if (d.getId().equals(requestId)) {
				d.setManagerID(manager);
				
				saveRequestsJson();

				return true;
			}
		}
		return false;
	}
	
	public static Boolean approve(String requestId,String managerId) {
		loadRequests("");
		for (DeliverRequest d : requests.values()) {
			if (d.getId().equals(requestId)) {
				d.setStatus(RequestStatus.APPROVED);
				d.setManagerID(managerId);
				saveRequestsJson();
				OrderDAO.changeStatus(OrderStatus.SHIPPING, d.getOrderID());

				return true;
			}
		}
		return false;
	}
	
	public static Boolean decline(String requestId) {
		loadRequests("");
		for (DeliverRequest d : requests.values()) {
			if (d.getId().equals(requestId)) {
				d.setStatus(RequestStatus.REJECTED);
				
				saveRequestsJson();

				return true;
			}
		}
		return false;
	}
	
	public static Collection<DeliverRequest> requestsForRestaurantsOrder(String id){
		loadRequests("");
		List<DeliverRequest> requestsD = new ArrayList<DeliverRequest>();
		for (DeliverRequest d : requests.values()) {
			if (d.getRestaurantID().equals(id) /*&& d.getStatus().equals(RequestStatus.WAITING)*/) {
				requestsD.add(d);
				
			}
		}
		
		return requestsD;
		
	}
	
	public static Collection<DeliverRequest> allDeliverersRequests(String id){
		loadRequests("");
		List<DeliverRequest> requestsD = new ArrayList<DeliverRequest>();
		for (DeliverRequest d : requests.values()) {
			if (d.getDelivererID().equals(id) /*&& d.getStatus().equals(RequestStatus.WAITING)*/) {
				requestsD.add(d);
				
			}
		}
		
		return requestsD;
		
	}
	
	public static Collection<String> getIdsFromApprovedOrders(String usernameOfDeliverer){
		List<String> ordersId = new ArrayList<String>();
		loadRequests("");
		for(DeliverRequest r : requests.values()) {
			if(r.getDelivererID().equals(usernameOfDeliverer) && r.getStatus().equals(RequestStatus.APPROVED)) {
				ordersId.add(r.getOrderID());
			}
		}
		
		return ordersId;
	}
	
	public static boolean existsRequest(String oId,String username) {
		loadRequests("");
		for(DeliverRequest dr : requests.values()) {
			if(dr.getOrderID().equals(oId) && dr.getDelivererID().equals(username)) {
				return true;
			}
		}
		
		return false;
	}
}

