package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import beans.User;
import dao.UserDAO;
import enums.Role;


@Path("/ls")
public class LogoutService {
	
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public LogoutService() {
		
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
	@Path("/logout")
	@Produces(MediaType.TEXT_HTML)
	public Response logout() {
		
		if(isUserAdmin() || isUserManager() || isUserCustomer() || isUserDeliverer()) {
		
			HttpSession session = request.getSession();
			if(session != null && session.getAttribute("loggedInUser") != null) {
				session.invalidate();
				System.out.println("user logged out!");
			}
			return Response.status(Response.Status.ACCEPTED).entity("http://localhost:8080/VebProjekat/index.html").build();
					
		}
		return Response.status(403).type("text/plain")
				.entity("Ne mozete pristupiti ovoj funkcionalnosti!").build();
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
