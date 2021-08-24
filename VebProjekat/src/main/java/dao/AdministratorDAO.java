package dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Administrator;
import enums.Gender;
import enums.Role;

public class AdministratorDAO {

	private Gson gs = new Gson();
	private HashMap<String, Administrator> admins = new HashMap<String, Administrator>(); // delete the initialization after creating the file!
	private String path = "C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\admins.json"; 

	public AdministratorDAO(String contextPath) {
		loadAdmins(contextPath);
	}
	
	public Administrator find(String username) {
		return admins.containsKey(username) ? admins.get(username) : null;
	}
	
	public void loadAdmins(String contextPath) {
		
		// maybe we should add predefined values for boolean attributes, so we don't do this in constructors :(
		admins.put("admin", new Administrator("admin", "admin", "Adam", "Martinez", Gender.MALE, new Date(), Role.ADMINISTRATOR, false, false));
		System.out.println("henlo");
		String json = gs.toJson(admins);
		byte[] strInBytes = json.getBytes();

		// writing in file for the first time; should be removed after the file is created
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			fos.write(strInBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//----------------------------------------
		
		String adminsJson = "";
		try {
			adminsJson = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Type type = new TypeToken<Map<String, Administrator>>() {}.getType();
		admins.clear();
		admins = gs.fromJson(adminsJson, type);
		
		//just to check it out 
		for(Map.Entry<String, Administrator> entry : admins.entrySet()) {
			System.out.println(entry.getValue().getUsername());
		}
	}
}

