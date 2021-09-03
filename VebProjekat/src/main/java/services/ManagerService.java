package services;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Manager;
import beans.Restaurant;
import beans.User;
import dao.ManagerDAO;
import enums.Role;



@Path("/managers")
public class ManagerService {

	
	@Context
	ServletContext ctx;
	
	
	public ManagerService() {
		
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("managerDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("managerDAO", new ManagerDAO(contextPath));
		}
	}
	
	
	
	
	@GET
	@Path("/getRestaurantFromLoggedManager")
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getRestaurant(@Context HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.MANAGER)) {
			return null;
		}
		
		String username = user.getUsername();	
		return ManagerDAO.getRestaurantForManager(username);
		
	}
	
	
	@GET
	@Path("/getAllAvailableManagers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Manager> getAllAvailableManagers(@Context HttpServletRequest request){
		ManagerDAO.loadManagers("");
		List<Manager> availableManager = new ArrayList<Manager>();
		for(Manager m : ManagerDAO.findAll()) {
			if(m.getRestaurant() == null) {
				availableManager.add(m);
			}
		}
		return availableManager;
		
	}
	
}
