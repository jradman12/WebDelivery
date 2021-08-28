package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import beans.User;
import dao.UserDAO;
import enums.Role;

@Path("")
public class LoginService {
	
	@Context
	ServletContext ctx;
	
	public LoginService() {
		
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
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user, @Context HttpServletRequest request) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("usersDAO");
		User loggedUser = userDAO.find(user.getUsername(), user.getPassword());
		if (loggedUser == null) {
			return Response.status(400).entity("Invalid username and/or password").build();
		}
		request.getSession().setAttribute("user", loggedUser);
		System.out.println(loggedUser.getFistName() + " is currently logged in.");
		
		if (loggedUser.getRole().equals(Role.ADMINISTRATOR)) {
			return Response.status(Response.Status.ACCEPTED).entity("http://localhost:8080/VebProjekat/WebContent/adminDashboard.html").build();

		} else if (loggedUser.getRole().equals(Role.MANAGER)) {
			return Response.status(Response.Status.ACCEPTED).entity("http://localhost:8080/VebProjekat/WebContent/manager.html").build();

		} else if (loggedUser.getRole().equals(Role.DELIVERER)) {
			return Response.status(Response.Status.ACCEPTED).entity("http://localhost:8080/VebPojekat/WebContent/deliverer.html").build();

		}
		else if (loggedUser.getRole().equals(Role.CUSTOMER)) {
			return Response.status(Response.Status.ACCEPTED).entity("http://localhost:8080/VebPojekat/WebContent/index.html").build();

		}
		
		return Response.status(200).build();
	}
	
}

