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

import beans.Administrator;
import beans.User;


public class AdministratorDAO {

	private static Map<String, Administrator> admins = new HashMap<>();
	
	
	public AdministratorDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public AdministratorDAO(String contextPath) {
		loadAdmins(contextPath);
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	public Administrator find(String username, String password) {
		if (!admins.containsKey(username)) {
			return null;
		}
		Administrator admin = admins.get(username);
		if (!admin.getPassword().equals(password)) {
			return null;
		}
		return admin;
	}
	
	public static Collection<Administrator> findAll() {
		return admins.values();
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private static void loadAdmins(String contextPath) {
		
		Gson gs = new Gson();
		// maybe we should add predefined values for boolean attributes, so we don't do this in constructors :(
//				admins.put("adam", new Administrator("adam", "admin", "Adam", "Martinez", Gender.MALE, parseDate("24.09.1992."), Role.ADMINISTRATOR, false, false));
//				admins.put("ella", new Administrator("ella", "admin", "Ella", "Williams", Gender.FEMALE, parseDate("02.03.1989."), Role.ADMINISTRATOR, false, false));
//				System.out.println("henlo");
//				String json = gs.toJson(admins);
//				byte[] strInBytes = json.getBytes();
//
//				// writing in file for the first time; should be removed after the file is created
//				FileOutputStream fos = null;
//				try {
//					fos = new FileOutputStream("C:\\Users\\hp\\Desktop\\web-proj\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\admins.json");
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
//				try {
//					fos.write(strInBytes);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				try {
//					fos.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				//----------------------------------------
				
				String adminsJson = "";
				try {
					adminsJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\admins.json")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Administrator>>() {}.getType();
				admins.clear();
				admins = gs.fromJson(adminsJson, type);
				
				//just to check it out 
				for(Map.Entry<String, Administrator> entry : admins.entrySet()) {
					System.out.println(entry.getValue().getFistName());
				}
		
		
		
		
//		BufferedReader in = null;
//		try {
//			File file = new File(contextPath + "/data/users.txt");
//			in = new BufferedReader(new FileReader(file));
//			String line;
//			StringTokenizer st;
//			while ((line = in.readLine()) != null) {
//				line = line.trim();
//				if (line.equals("") || line.indexOf('#') == 0)
//					continue;
//				st = new StringTokenizer(line, ";");
//				while (st.hasMoreTokens()) {
//					String username = st.nextToken().trim();
//					String password = st.nextToken().trim();
//					String firstName = st.nextToken().trim();
//					String lastName = st.nextToken().trim();
//					String genderStr = st.nextToken().trim();
//					String dateStr = st.nextToken().trim();
//					String roleStr = st.nextToken().trim();
//					String isDeletedStr = st.nextToken().trim();
//					String isBlockedStr = st.nextToken().trim();
//					Gender gender;
//					if(genderStr.equals("MALE")) {
//						gender=Gender.MALE;
//					}else if(genderStr.equals("FEMALE")) {
//						gender=Gender.FEMALE;
//					}else {
//						gender=Gender.OTHER;
//					}
//					Role role;
//					if(roleStr.equals("ADMINISTRATOR")) {
//						role=Role.ADMINISTRATOR;
//					}else if(roleStr.equals("CUSTOMER")) {
//						role=Role.CUSTOMER;
//					}else if(roleStr.equals("DELIVERER")) {
//						role=Role.DELIVERER;
//					}else {
//						role = Role.MANAGER;
//					}
//					
//					Boolean isDeleted;
//					if(isDeletedStr.equals("false")) {
//						isDeleted=false;
//					}else {
//						isDeleted=true;
//					}
//					
//					Boolean isBlocked;
//					if(isBlockedStr.equals("false")) {
//						isBlocked=false;
//					}else {
//						isBlocked=true;
//					}
//					 
//				    Date dateOfBirth=new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);  
//					admins.put(username, new Administrator(username, password, firstName, lastName,gender, dateOfBirth,role,isDeleted,isBlocked));
//					
//				}
//				
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				}
//				catch (Exception e) { }
//			}
//		}
	}
	
	public static void saveAdministratorsJSON() {
		String path="C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\admins.json";
		

		Map<String, Administrator> allAdmins = new HashMap<>();
		
		for (Administrator a : findAll()) {
			allAdmins.put(a.getUsername(),a);
		}

		Gson gs = new Gson();
		String json = gs.toJson(allAdmins);
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
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public static boolean changeAdministrator(User user) {
		loadAdmins("");
		System.out.println(user.getLastName());
		for (Administrator a : admins.values()) {
			if (a.getUsername().equals(user.getUsername())) {
				a.setFistName(user.getFistName());
				a.setLastName(user.getLastName());
				a.setPassword(user.getPassword());
				UserDAO.changeUser(user);
				saveAdministratorsJSON();
				return true;
				
			}
		}
		
		return false;
		
	}
	
}

