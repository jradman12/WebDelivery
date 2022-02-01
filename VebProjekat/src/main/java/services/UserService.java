package services;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Customer;
import beans.Order;
import beans.User;
import dao.AdministratorDAO;
import dao.CartDAO;
import dao.CustomerDAO;
import dao.DelivererDAO;
import dao.ManagerDAO;
import dao.OrderDAO;
import dao.UserDAO;
import dto.UserDTO;
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
	public void init() {
		if (ctx.getAttribute("usersDAO") == null) {
	    	UserDAO userDAO = new UserDAO();
			userDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("usersDAO", userDAO);
			
		}
		if(ctx.getAttribute("customerDAO") == null) {
			CustomerDAO customerDAO = new CustomerDAO();
			customerDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("customerDAO", customerDAO);
		}
		if (ctx.getAttribute("cаrtDAO") == null) {
			CartDAO cаrtDAO = new CartDAO();
			cаrtDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("cаrtDAO", cаrtDAO);	
		}
		if(ctx.getAttribute("orderDAO") == null) {
			OrderDAO orderDAO = new OrderDAO();
			orderDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("orderDAO", orderDAO);
		}
		if(ctx.getAttribute("delivererDAO") == null) {
			DelivererDAO delivererDAO = new DelivererDAO();
			delivererDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("delivererDAO", delivererDAO);
		}
		if(ctx.getAttribute("managerDAO") == null) {
			ManagerDAO managerDAO = new ManagerDAO();
			managerDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("managerDAO", managerDAO);
		}
	}
	
	public String getDataDirPath() {
		return (ctx.getRealPath("") + File.separator + "data"+ File.separator);
	}
	
//	@GET
//	@Path("/getAllUsers")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Collection<User> getAllUsers(){
//		Map<String, User> users = new HashMap<>();
//		UserDAO.loadUsers("");
//		users = UserDAO.users;
//		System.out.println("get users request returns following users: ");
//		for(Map.Entry<String, User> entry : users.entrySet()) {
//			System.out.println(entry.getValue());
//		}
//		
//		
//		
//		return users.values();
//	}
	
	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UserDTO> getAllUsers(){
		UserDAO userDAO = (UserDAO) ctx.getAttribute("usersDAO");
		User user = (User) request.getSession().getAttribute("loggedInUser");
		List<UserDTO> dto = new ArrayList<UserDTO>(); 
		
		for(User u : userDAO.getAllAvailable()) {
			if(u.getRole() != Role.CUSTOMER && !(u.getUsername().equals(user.getUsername()))) 
				dto.add(new UserDTO(u));
		}
		
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		for(Customer c : customerDAO.getAllAvailable()) {
			dto.add(new UserDTO(c));
		}
		
		return dto;
	}

	
	@GET
	@Path("/getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAllCustomers(){
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.setBasePath(getDataDirPath());
		return  customerDAO.getAllAvailable();
	}
	
	@GET
	@Path("/getLoggedUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLoggedUser(){
		if(isUserAdmin() || isUserManager() || isUserCustomer() || isUserDeliverer()) {
			
			User user = (User) request.getSession().getAttribute("loggedInUser");		

			return Response
					.status(Response.Status.ACCEPTED)
					.entity(user)
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
	
	// blocking and unblocking user
	@PUT
	@Path("/unblockUser/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response unblockUser(@PathParam("username") String username) {
		
		UserDAO userDAO = (UserDAO) ctx.getAttribute("usersDAO");
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		
		userDAO.unblockUserById(username);
		customerDAO.unblockUser(username);
		
		return Response
				.status(Response.Status.ACCEPTED).entity("Uspjesno deblokiran korisnik!").entity(userDAO.getAllAvailable()).build();
	}
	
	
	@PUT
	@Path("/deleteUser/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("username") String username) {
		
		UserDAO userDAO = (UserDAO) ctx.getAttribute("usersDAO");
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		CartDAO cаrtDAO = (CartDAO) ctx.getAttribute("cаrtDAO");
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");

		userDAO.deleteUser(username);
		
		User userToDelete = userDAO.users.get(username);
		if (userToDelete.getRole().equals(Role.CUSTOMER)) {
			customerDAO.deleteCustomer(username);
			cаrtDAO.deleteCart(username);
			orderDAO.deleteOrdersForUser(username);
		}
		else if (userToDelete.getRole().equals(Role.DELIVERER))
			for (Order or : delivererDAO.getDeliverersOrders(username)) {
				orderDAO.changeDeliverersOrderStatus(or.getId());
			}
			//delivererDAO.deleteDeliverer(username);
		return Response
				.status(Response.Status.ACCEPTED).entity("Uspjesno obrisan korisnik!").entity(userDAO.getAllAvailable()).build();
	}
	

	@PUT
	@Path("/blockUser/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response blockUser(@PathParam("username") String username) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("usersDAO");
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		
		userDAO.blockUserById(username);
		customerDAO.blockUser(username);
		delivererDAO.blockUser(username);
		managerDAO.blockUser(username);
		
		return Response
				.status(Response.Status.ACCEPTED).entity("Uspjesno blokiran korisnik!").entity(userDAO.getAllAvailable()).build();
	}
	

	private void changeManagerInformation(String username,User user) {
		ManagerDAO managerDAO = new ManagerDAO();
		managerDAO.setBasePath(getDataDirPath());
		success = managerDAO.changeManager(username,user);
	}

	private void changeDelivererInformation(String username,User user) {
		DelivererDAO delivererDAO = new DelivererDAO();
		delivererDAO.setBasePath(getDataDirPath());
		success = delivererDAO.changeDeliverer(username,user);
	}

	private void changeCustomerInformation(String username,User user) {
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.setBasePath(getDataDirPath());
		success = customerDAO.changeCustomer(username,user);
	}

	private void changeAdministratorInformation(String username,User user) {
		AdministratorDAO administratorDAO = new AdministratorDAO();
		administratorDAO.setBasePath(getDataDirPath());
		success = administratorDAO.changeAdministrator(username,user);
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
