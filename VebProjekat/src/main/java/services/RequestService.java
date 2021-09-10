package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
		RequestDAO requestDAO = (RequestDAO) ctx.getAttribute("requestDAO");
		return requestDAO.findAll();
	}
	
	@GET
	@Path("/getAllRequestsForRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<DeliverRequest> getAllCommentsForResaturant(){
		RequestDAO requestDAO = (RequestDAO) ctx.getAttribute("requestDAO");
		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.MANAGER)) {
			return null;
		}
			
		String r = new ManagerDAO().getRestaurantForManager(user.getUsername());
		return requestDAO.requestsForRestaurantsOrder(r);
	}
	
	@PUT
	@Path("/approveRequest/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response approveRequest(@PathParam("id") String id) {
		RequestDAO requestDAO = (RequestDAO) ctx.getAttribute("requestDAO");

		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.MANAGER)) {
			return Response.status(403).entity("Ne mozete pristupiti resursu").build();
		}
		
		boolean success = requestDAO.approve(id,user.getUsername());
		if(success) {
			return Response.status(200).entity(requestDAO.requestsForRestaurantsOrder(id)).build();
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
		RequestDAO requestDAO = (RequestDAO) ctx.getAttribute("requestDAO");

		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.MANAGER)) {
			return Response.status(403).entity("Ne mozete pristupiti resursu").build();
		}
		
		boolean success = requestDAO.decline(id);
		if(success) {
			return Response.status(200).entity(requestDAO.requestsForRestaurantsOrder(id)).build();
		}
		else {
			return Response.status(Status.BAD_REQUEST).entity("Nije moguce odobriti zahtjev!").build();
		}
		
		
		
	}
	
	
	@GET
	@Path("/getAllRequestsForDeliverer")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<DeliverRequest> getAllRequestsForDeliverer(){
		RequestDAO requestDAO = (RequestDAO) ctx.getAttribute("requestDAO");

		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.DELIVERER)) {
			return null;
		}
		
		return requestDAO.allDeliverersRequests(user.getUsername());
	}
	
	@POST
	@Path("/sendRequest")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delivererAddRequest(DeliverRequest dr){
		RequestDAO requestDAO = (RequestDAO) ctx.getAttribute("requestDAO");

		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.DELIVERER)) {
			return null;
		}
		
		
		dr.setDelivererID(user.getUsername());
		requestDAO.addNewRequest(dr);
		return Response.status(Status.ACCEPTED).entity("Zahtjev je poslat!").build();
	}
	
	
	@GET
	@Path("/existsRequest/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response exists(@PathParam("id") String id){
		RequestDAO requestDAO = (RequestDAO) ctx.getAttribute("requestDAO");

		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.DELIVERER)) {
			return null;
		}
		
		if(requestDAO.existsRequest(id, user.getUsername())) {
			return Response.status(200).entity(true).build();
		}else {
			return Response.status(403).entity(false).build();
		}
	}
	
	
	

}
