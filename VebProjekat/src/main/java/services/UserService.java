package services;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import beans.User;
import dao.AdministratorDAO;
import dao.CustomerDAO;
import dao.DelivererDAO;
import dao.ManagerDAO;
import dao.UserDAO;
import enums.Role;

@Path("/users")
public class UserService {
	
	@Context
	ServletContext ctx;
	private boolean success=false;;
	@Context
	HttpServletRequest request;
	public UserService() {
		
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("usersDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("usersDAO", new UserDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers(){
		Map<String, User> users = new HashMap<>();
		UserDAO.loadUsers("");
		users = UserDAO.users;
		System.out.println("get users request returns following users: ");
		for(Map.Entry<String, User> entry : users.entrySet()) {
			System.out.println(entry.getValue());
		}
		
		
		
		return users.values();
	}
	
	@GET
	@Path("/getLoggedUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLoggedUser(){
		if(isUserAdmin() || isUserManager() || isUserCustomer() || isUserDeliverer()) {
			
			User user = (User) request.getSession().getAttribute("loggedInUser");		

			return Response
					.status(Response.Status.ACCEPTED)
					.entity( user)
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("Ne možete pristupiti ovom resursu!").build();
	}
	
	
	@PUT
	@Path("/updateUser/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserInformation(@PathParam("username") String username,User user) {
		
		System.out.println("izmjena");
		if(user.getRole().equals(Role.ADMINISTRATOR)) {
			changeAdministratorInformation(username,user);
		}
		if(user.getRole().equals(Role.CUSTOMER)) {
			changeCustomerInformation(username,user);
		}
		if(user.getRole().equals(Role.DELIVERER)) {
			changeDelivererInformation(username,user);
		}
		if(user.getRole().equals(Role.MANAGER)) {
			changeManagerInformation(username,user);
		}
		
		if(success) {
			request.getSession().setAttribute("loggedInUser", user);
			return Response.status(200).entity("Uspjesno izmijenjene info!").build();
		}else {
		return Response.status(400).entity("Neuspjesno izmijenjene info!").build();
		}
		
	}

	private void changeManagerInformation(String username,User user) {
		success=ManagerDAO.changeManager(username,user);
	}

	private void changeDelivererInformation(String username,User user) {
		success=DelivererDAO.changeDeliverer(username,user);
	}

	private void changeCustomerInformation(String username,User user) {
		success=CustomerDAO.changeCustomer(username,user);
	}

	private void changeAdministratorInformation(String username,User user) {
		
		success=AdministratorDAO.changeAdministrator(username,user);
		System.out.println(success);
	}
	
	private boolean isUserAdmin() {
		User user = (User) request.getSession().getAttribute("loggedInUser");
		
		if(user!= null) {
			if(user.getRole().equals(Role.ADMINISTRATOR)) {	
				return true;
			}
		}	
		return false;
	}
	
	private boolean isUserDeliverer() {
		User user = (User) request.getSession().getAttribute("loggedInUser");
		
		if(user!= null) {
			if(user.getRole().equals(Role.DELIVERER)) {
				return true;
			}
		}	
		return false;
	}
	
	private boolean isUserCustomer() {
		User user = (User) request.getSession().getAttribute("loggedInUser");
		
		if(user!= null) {
			if(user.getRole().equals(Role.CUSTOMER)) {
				return true;
			}
		}	
		return false;
	}
	
	private boolean isUserManager() {
		User user = (User) request.getSession().getAttribute("loggedInUser");
		
		if(user!= null) {
			if(user.getRole().equals(Role.MANAGER)) {
				return true;
			}
		}	
		return false;
	}


}
