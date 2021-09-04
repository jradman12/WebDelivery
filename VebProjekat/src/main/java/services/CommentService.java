package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
import enums.Role;

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
			
		Map<String, Comment> comments = new HashMap<>();
		List<Comment> commentsForRestaurant = new ArrayList<Comment>();
		//CommentDAO.saveCommentsJSON();
		CommentDAO.loadComments("");//dobavljamo sve komentare
		comments = CommentDAO.comments; 
		for(Comment c : comments.values()) {
			System.out.println(c.getRestaurant().getName());
		}
		Restaurant r = ManagerDAO.getRestaurantForManager(user.getUsername());
		for(Comment c : comments.values()) {
			if(c.getRestaurant().getId().equals(r.getId())) {
				commentsForRestaurant.add(c);
				
			}
			
		}
		
		return commentsForRestaurant;
	}
	
	

}
