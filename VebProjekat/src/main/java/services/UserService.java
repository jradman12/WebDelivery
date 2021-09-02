package services;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
	public User getLoggedUser(@Context HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null) {
			return null;
		}
		
		return user;
		
	}
	
	
	@POST
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateUserInformation(User user) {
		
		System.out.println("usao");
		if(user.getRole().equals(Role.ADMINISTRATOR)) {
			changeAdministratorInformation(user);
		}
		if(user.getRole().equals(Role.CUSTOMER)) {
			changeCustomerInformation(user);
		}
		if(user.getRole().equals(Role.DELIVERER)) {
			changeDelivererInformation(user);
		}
		if(user.getRole().equals(Role.MANAGER)) {
			changeManagerInformation(user);
		}
		
		if(success) {
			return Response.status(200).entity("Uspjesno izmijenjene info!").build();
		}else {
		return Response.status(400).entity("Neuspjesno izmijenjene info!").build();
		}
		
	}
	
	// blocking and unblocking user
	@POST
	@Path("/unblockUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response unblockUser(User userToUnblock){
		
		UserDAO userDAO = (UserDAO) ctx.getAttribute("usersDAO");
		userDAO.unblockUserById(userToUnblock.getUsername());
		
		return Response
				.status(Response.Status.ACCEPTED).entity("Uspjesno deblokiran korisnik!").entity(UserDAO.users.values()).build();
	}
	
	@POST
	@Path("/blockUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response blockUser(User userToBlock){
		
		UserDAO userDAO = (UserDAO) ctx.getAttribute("usersDAO");
		userDAO.blockUserById(userToBlock.getUsername());
		
		return Response
				.status(Response.Status.ACCEPTED).entity("Uspjesno blokiran korisnik!").entity(UserDAO.users.values()).build();
	}
	

	private void changeManagerInformation(User user) {
		success=ManagerDAO.changeManager(user);
	}

	private void changeDelivererInformation(User user) {
		success=DelivererDAO.changeDeliverer(user);
	}

	private void changeCustomerInformation(User user) {
		success=CustomerDAO.changeCustomer(user);
	}

	private void changeAdministratorInformation(User user) {
		
		success=AdministratorDAO.changeAdministrator(user);
		System.out.println(success);
	}
	
	

}
