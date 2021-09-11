package services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import beans.Cart;
import beans.CartItem;
import beans.Customer;
import beans.Order;
import beans.User;
import dao.CartDAO;
import dao.CustomerDAO;
import dao.ManagerDAO;
import dao.OrderDAO;
import dao.RestaurantDAO;
import dto.OrderDTO;
import enums.OrderStatus;
import enums.Role;

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
			OrderDAO orderDAO = new OrderDAO();
			orderDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("orderDAO", orderDAO);
		}
	}
	
	public String getDataDirPath() {
		return (ctx.getRealPath("") + File.separator + "data"+ File.separator);
	}
	
	@GET
	@Path("/getAllOrdersForRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getOrdersForRestaurant(){
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		User user = (User) request.getSession().getAttribute("loggedInUser");
		ManagerDAO mdao = new ManagerDAO();
		mdao.setBasePath(getDataDirPath());
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.MANAGER)) {
			return null;
		}
		
		String username = user.getUsername();	
		String idOfRestaurant = mdao.getRestaurantForManager(username);
		return orderDAO.getOrdersForRestaurant(idOfRestaurant);
		
	}
	
	@GET
	@Path("/getAllOrdersWithStatusADAA")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getAllOrdersWithStatusAD(){
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");

		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.DELIVERER)) {
			return null;
		}
		

		
		return orderDAO.getOrdersModifiedForDeliverer(user.getUsername());

		
	}
	
	@GET
	@Path("/getAllOrderDTOs")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<OrderDTO> getAllOrderDTOs(){
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");
		
		return orderDAO.getOrdersWithRestDetails();
		
	}
	
	
	@PUT
	@Path("/updateOrderStatusIP/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrderStatusIP(@PathParam("id") String id) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");

		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.MANAGER)) {
			return null;
		}
		ManagerDAO mdao = new ManagerDAO();
		mdao.setBasePath(getDataDirPath());
		String restaurantId = mdao.getRestaurantForManager(user.getUsername());
		boolean success=orderDAO.changeStatus(OrderStatus.IN_PREPARATION,id);
		if(success) {
			return Response.status(200).entity(orderDAO.getOrdersForRestaurant(restaurantId)).build();
		}else {
			return Response.status(400).entity("Izmjena nije uspjela!").build();
		}
		
	}
	
	@PUT
	@Path("/updateOrderStatusAD/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrderStatusWD(@PathParam("id") String id) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");

		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.MANAGER)) {
			return null;
		}
		ManagerDAO mdao = new ManagerDAO();
		mdao.setBasePath(getDataDirPath());
		String restaurantId = mdao.getRestaurantForManager(user.getUsername());
		boolean success=orderDAO.changeStatus(OrderStatus.AWAITING_DELIVERER,id);
		if(success) {
			return Response.status(200).entity(orderDAO.getOrdersForRestaurant(restaurantId)).build();
		}else {
			return Response.status(400).entity("Izmjena nije uspjela!").build();
		}
		
	}
	
	@GET
	@Path("/getAllApprovedOrders")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getAllApprovedOrders(){
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");

		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.DELIVERER)) {
			return null;
		}
		
		return orderDAO.getApprovedOrdersForDeliver(user.getUsername());
		
	}
	
	
	@PUT
	@Path("/deliverOrder/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deliverOrder(@PathParam("id") String id) {
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");

		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.DELIVERER)) {
			return null;
		}
		
		boolean success=orderDAO.changeStatus(OrderStatus.DELIVERED,id);
		if(success) {
			return Response.status(200).entity(orderDAO.getApprovedOrdersForDeliver(user.getUsername())).build();
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
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.setBasePath(getDataDirPath());
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
		CartDAO cartDAO = new CartDAO();
		cartDAO.setBasePath(getDataDirPath());
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
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.setBasePath(getDataDirPath());		//User user = (User) request.getSession().getAttribute("loggedInUser");
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
		OrderDAO orderDAO = (OrderDAO) ctx.getAttribute("orderDAO");

		System.out.println("ušao u orderFromRestaurantDeliveredToCustomer ");
		RestaurantDAO rDAO = new RestaurantDAO();
		rDAO.setBasePath(getDataDirPath()); 
		
		String currentRestID = (String) ctx.getAttribute("currentRestID");
		
		User user = (User) request.getSession().getAttribute("loggedInUser");
		
		Map<String, Order> orders = new HashMap<>();
		orders = orderDAO.orders; 
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

