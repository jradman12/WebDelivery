package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
<<<<<<< HEAD
import dao.RequestDAO;

import java.util.*;
=======
import dao.RestaurantDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import beans.Cart;
import beans.CartItem;
import beans.Customer;
import dao.CartDAO;
import dao.CustomerDAO;




>>>>>>> customer



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
	
	@GET
	@Path("/getAllOrdersWithStatusAD")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getAllOrdersWithStatusAD(){
		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.DELIVERER)) {
			return null;
		}
		
		return OrderDAO.getOrdersWithStatusAD();
		
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
	@Path("/getAllApprovedOrders")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getAllApprovedOrders(){
		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.DELIVERER)) {
			return null;
		}
		
		return OrderDAO.getApprovedOrdersForDeliver(user.getUsername());
		
	}
	
	
	@PUT
	@Path("/deliverOrder/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deliverOrder(@PathParam("id") String id) {
		
		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.DELIVERER)) {
			return null;
		}
		
		boolean success=OrderDAO.changeStatus(OrderStatus.DELIVERED,id);
		if(success) {
			return Response.status(200).entity(OrderDAO.getApprovedOrdersForDeliver(user.getUsername())).build();
		}else {
			return Response.status(400).entity("Izmjena nije uspjela!").build();
		}
		
	}
	
	
	
	@POST
	@Path("/createNewOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewOrder(Order newOrder) {
		
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");	
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		Customer customer = customerDAO.getCustomerByUsername(newOrder.getCustomerID());
		
		double orderPrice = 0;
		for(CartItem ci : newOrder.getOrderedItems())
			orderPrice += ci.getProduct().getPrice() * ci.getAmount();
		newOrder.setPrice(orderPrice);
		newOrder.setDateAndTime(new Date());
		orderDAO.addNewOrder(newOrder);		
		
		int gainedPoints = (int) (newOrder.getPrice() / 1000 * 133);
		// update customer's points
		customer.addPoints(gainedPoints);
		customerDAO.customers.remove(customer.getUsername());
		customerDAO.customers.put(customer.getUsername(), customer);
		customerDAO.saveCustomersJSON();
		
		// gotta empty the cart
		CartDAO cartDAO = (CartDAO) ctx.getAttribute("cartDAO");
		for(Cart cart : cartDAO.carts.values()) {
			if(cart.getCustomerID().equals(newOrder.getCustomerID())) {
				cartDAO.carts.remove(cart);
			}
		}
		cartDAO.saveCartsJSON();
		return Response.status(Response.Status.ACCEPTED).entity("Uspjesno kreirana porudzbina!").build();

	}
	
	
	@GET
	@Path("/getCustomersOrders")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomersOrders(){
		List<Order> customersOrders = new ArrayList<Order>();
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");	
		User user = (User) request.getSession().getAttribute("loggedInUser");
		for(Order o : orderDAO.getExistingOrders()) {
			System.out.println(o);
			if(o.getCustomerID().equals(user.getUsername())) 
				customersOrders.add(o);
		}
		if(customersOrders.size() > 0) {
			return Response.status(Response.Status.ACCEPTED).entity(customersOrders).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("No orders available for this customer.").build();
	}
	
	@DELETE
	@Path("/cancelOrder/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeCartItem(@PathParam("id") String id) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");	
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		//User user = (User) request.getSession().getAttribute("loggedInUser");
		for(Order o : orderDAO.getExistingOrders()) {
			if(o.getId().equals(id)) {
				o.setDeleted(true);
				for(Customer c : customerDAO.customers.values()) {
					if(c.getUsername().equals(o.getCustomerID())) {
						c.addPoints((int) (o.getPrice() / 1000 * 133 * 4) * (-1));
					}
				}
			}
		}
		orderDAO.saveOrdersJSON();
		customerDAO.saveCustomersJSON();
	}
	
	@GET
	@Path("/orderFromRestaurantDeliveredToCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean orderFromRestaurantDeliveredToCustomer() {
		System.out.println("ušao u orderFromRestaurantDeliveredToCustomer ");
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
					&& o.getCustomerID().equals(user.getUsername())) {
				
				return true;
			}
		}
		return false;
	}
	
	
	
}

