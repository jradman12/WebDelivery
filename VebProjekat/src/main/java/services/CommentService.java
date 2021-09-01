package services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import beans.Comment;
import beans.Restaurant;
import dao.CommentDAO;
import dao.RestaurantDAO;

@Path("/comments")
public class CommentService {
	
	@Context
	ServletContext ctx;
	
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
	
	

}
