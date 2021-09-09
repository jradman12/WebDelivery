package services;

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

import enums.OrderStatus;
import enums.Role;
import enums.StatusOfComment;
import beans.Comment;
import beans.Order;
import beans.User;
import dao.CommentDAO;
import dao.ManagerDAO;
import dao.OrderDAO;
import dao.RestaurantDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Path("/orders")
public class OrderService {

	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	
	public OrderService() {
		
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("orderDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("orderDAO", new OrderDAO(contextPath));
		}
	}
	
	
	
	@GET
	@Path("/getAllOrdersForRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getOrdersForRestaurant(){
		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.MANAGER)) {
			return null;
		}
		
		String username = user.getUsername();	
		String idOfRestaurant = ManagerDAO.getRestaurantForManager(username);
		return OrderDAO.getOrdersForRestaurant(idOfRestaurant);
		
	}
	
	
	@PUT
	@Path("/updateOrderStatusIP/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrderStatusIP(@PathParam("id") String id) {
		
		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.MANAGER)) {
			return null;
		}
		
		String restaurantId = ManagerDAO.getRestaurantForManager(user.getUsername());
		boolean success=OrderDAO.changeStatus(OrderStatus.IN_PREPARATION,id);
		if(success) {
			return Response.status(200).entity(OrderDAO.getOrdersForRestaurant(restaurantId)).build();
		}else {
			return Response.status(400).entity("Izmjena nije uspjela!").build();
		}
		
	}
	
	@PUT
	@Path("/updateOrderStatusAD/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrderStatusWD(@PathParam("id") String id) {
		
		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.MANAGER)) {
			return null;
		}
		String restaurantId = ManagerDAO.getRestaurantForManager(user.getUsername());
		boolean success=OrderDAO.changeStatus(OrderStatus.AWAITING_DELIVERER,id);
		if(success) {
			return Response.status(200).entity(OrderDAO.getOrdersForRestaurant(restaurantId)).build();
		}else {
			return Response.status(400).entity("Izmjena nije uspjela!").build();
		}
		
	}
	
	
	@GET
	@Path("/orderFromRestaurantDeliveredToCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean orderFromRestaurantDeliveredToCustomer() {
		
		RestaurantDAO rDAO = new RestaurantDAO(""); // this will set em
		String currentRestID = (String) ctx.getAttribute("currentRestID");
		
		User user = (User) request.getSession().getAttribute("loggedInUser");
		
		Map<String, Order> orders = new HashMap<>();
		OrderDAO.loadOrders("");//dobavljamo sve komentare
		orders = OrderDAO.orders; 
		///
		
		///
		for(Order o : orders.values()) {
			if(o.getRestaurant().equals(currentRestID) && o.getStatus().equals(OrderStatus.DELIVERED) 
					&& o.getCustomer().equals(user.getUsername())) {
				
				return true;
			}
		}
		return false;
	}
	
	
	
}

