package services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Customer;
import beans.Deliverer;
import beans.User;
import dao.CustomerDAO;
import dao.DelivererDAO;
import dao.UserDAO;
import dto.UserDTO;
import enums.Role;

@Path("/deliverers")
public class DelivererService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public DelivererService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("delivererDAO") == null) {
			DelivererDAO delivererDAO = new DelivererDAO();
			delivererDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("delivererDAO",delivererDAO);
		}
		if (ctx.getAttribute("usersDAO") == null) {
			UserDAO usersDAO = new UserDAO();
			usersDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("usersDAO", usersDAO);	
		}
	}
	
	public String getDataDirPath() {
		return (ctx.getRealPath("") + File.separator + "data"+ File.separator);
	}
	
	@POST
	@Path("/registration")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(Deliverer newDeliverer) {
		System.out.println("Deliverer object Ive recieved is : " + newDeliverer);
		
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		UserDAO userDAO = (UserDAO) ctx.getAttribute("usersDAO");

		if (delivererDAO.getDelivererByUsername(newDeliverer.getUsername()) != null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("We already have deliverer with the same username. Please try another one").build();
		}
		newDeliverer.setRole(Role.DELIVERER);
		userDAO.addUser(newDeliverer);
		delivererDAO.addDeliverer(newDeliverer);
		
		return Response.status(Response.Status.ACCEPTED).entity("adminDashboard.html#/users").build(); 																							// accepted
	}
}
