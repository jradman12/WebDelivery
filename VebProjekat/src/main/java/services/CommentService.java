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
import java.util.List;
import java.util.ArrayList;
import beans.Comment;
import beans.Restaurant;
import beans.User;
import dao.CommentDAO;
import dao.ManagerDAO;
import dao.RestaurantDAO;
import enums.Role;
import enums.StatusOfComment;

@Path("/comments")
public class CommentService {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	
	public CommentService() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("commentDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("commentDAO", new CommentDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getAllComments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getAllComments(){
		Map<String, Comment> comments = new HashMap<>();
		//CommentDAO.saveCommentsJSON();
		CommentDAO.loadComments("");
		comments = CommentDAO.comments;
		System.out.println("get comments request returns following comments: ");
		for(Map.Entry<String, Comment> entry : comments.entrySet()) {
			System.out.println(entry.getValue());
		}
		
		return comments.values();
	}
	
	@GET
	@Path("/getAllCommentsForRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getAllCommentsForResaturant(){
		 
		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.MANAGER)) {
			return null;
		}
			
		String r = ManagerDAO.getRestaurantForManager(user.getUsername());
		return CommentDAO.getCommentsForRestaurant(r);
	}
	
	@GET
	@Path("/getCommentsForRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getCommentsForRestaurant(){
			
		Map<String, Comment> comments = new HashMap<>();
		List<Comment> commentsForRestaurant = new ArrayList<Comment>();
		CommentDAO.loadComments("");//dobavljamo sve komentare
		comments = CommentDAO.comments; 
		///
		RestaurantDAO rDAO = new RestaurantDAO(""); // this will set em
		String currentRestID = (String) ctx.getAttribute("currentRestID");
		///
		for(Comment c : comments.values()) {
			System.out.println(c);
			System.out.println("current rest id " + currentRestID);
			if(c.getRestaurantID().equals(currentRestID) && c.getStatus().equals(StatusOfComment.APPROVED)) {
				
				commentsForRestaurant.add(c);
			}
		}
		return commentsForRestaurant;
	}
	
	@PUT
	@Path("/approveComment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response approveComment(@PathParam("id") String id,Comment comment) {
		
		System.out.println("izmjena");
		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.MANAGER)) {
			return Response.status(403).entity("Ne mo�ete pristupiti resursu").build();
		}
		String r = ManagerDAO.getRestaurantForManager(user.getUsername());
		boolean success=CommentDAO.changeStatus(StatusOfComment.APPROVED, id);
		if(success) {
			return Response.status(202).entity(CommentDAO.getCommentsForRestaurant(r)).build();
		}else {
			return Response.status(400).entity("Neuspjeh").build();
		}
		
		
	}
	
	@PUT
	@Path("/rejectComment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response rejectComment(@PathParam("id") String id,Comment comment) {
		
		System.out.println("izmjena");
		User user = (User) request.getSession().getAttribute("loggedInUser");
		if(user == null || !user.getRole().equals(Role.MANAGER)) {
			return Response.status(403).entity("Ne mo�ete pristupiti resursu").build();
		}
		
		boolean success=CommentDAO.changeStatus(StatusOfComment.REJECTED, id);
		if(success) {
			return Response.status(202).entity(CommentDAO.comments.values()).build();
		}else {
			return Response.status(400).entity("Neuspjeh").build();
		}
		
		
	}
	
	
	
	

}
