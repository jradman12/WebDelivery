package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
import javax.ws.rs.core.Response.Status;


import beans.DeliverRequest;
import beans.User;
import dao.ManagerDAO;
import dao.RequestDAO;
import enums.Role;


@Path("/requests")
public class RequestService {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	
	public RequestService() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("requestDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("requestDAO", new RequestDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getAllRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<DeliverRequest> getAllRequests(){
		Map<String, DeliverRequest> requests = new HashMap<>();
		//CommentDAO.saveCommentsJSON();
		RequestDAO.loadRequests("");
		requests = RequestDAO.requests;
		for(Map.Entry<String, DeliverRequest> entry : requests.entrySet()) {
			System.out.println(entry.getValue());
		}
		
		return requests.values();
	}
	
	@GET
	@Path("/getAllRequestsForRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<DeliverRequest> getAllCommentsForResaturant(){
		 
		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.MANAGER)) {
			return null;
		}
			
		String r = ManagerDAO.getRestaurantForManager(user.getUsername());
		return RequestDAO.requestsForRestaurantsOrder(r);
	}
	
	@PUT
	@Path("/approveRequest/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response approveRequest(@PathParam("id") String id) {
		
		
		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.MANAGER)) {
			return Response.status(403).entity("Ne mozete pristupiti resursu").build();
		}
		
		boolean success = RequestDAO.approve(id,user.getUsername());
		if(success) {
			return Response.status(200).entity(RequestDAO.requestsForRestaurantsOrder(id)).build();
		}
		else {
			return Response.status(Status.BAD_REQUEST).entity("Nije moguce odobriti zahtjev!").build();
		}
		
		
	}
	
	@PUT
	@Path("/rejectRequest/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response rejectRequest(@PathParam("id") String id) {
		
		

		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.MANAGER)) {
			return Response.status(403).entity("Ne mozete pristupiti resursu").build();
		}
		
		boolean success = RequestDAO.decline(id);
		if(success) {
			return Response.status(200).entity(RequestDAO.requestsForRestaurantsOrder(id)).build();
		}
		else {
			return Response.status(Status.BAD_REQUEST).entity("Nije moguce odobriti zahtjev!").build();
		}
		
		
		
	}
	
	
	
	

}
