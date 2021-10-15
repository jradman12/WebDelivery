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
	}
	
	public String getDataDirPath() {
		return (ctx.getRealPath("") + File.separator + "data"+ File.separator);
	}
	
	@POST
	@Path("/registration")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(Deliverer newDeliverer) {
		System.out.println("Manager object Ive recieved is : " + newDeliverer);
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		if (delivererDAO.getDelivererByUsername(newDeliverer.getUsername()) != null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("We already have deliverer with the same username. Please try another one").build();
		}
		delivererDAO.addNewDeliverer(newDeliverer);
		UserDAO userDAO = new UserDAO(); //odavdje
		User user = (User) request.getSession().getAttribute("loggedInUser");
		userDAO.setBasePath(getDataDirPath());
		System.out.println("ne znam zasto ne radi");
		List<UserDTO> dto = new ArrayList<UserDTO>(); 
		
		for(User u : userDAO.getAllAvailable()) {
			if(u.getRole() != Role.CUSTOMER && !(u.getUsername().equals(user.getUsername()))) 
				dto.add(new UserDTO(u));
			
		}
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.setBasePath(getDataDirPath());
		
		for(Customer c : customerDAO.getAllAvailable()) {
			dto.add(new UserDTO(c));
		}
		
		for(UserDTO ud : dto) {
			System.out.println(ud.getUsername());
		}
		request.getSession().setAttribute("usersDAO",dto); //zakljucno sa ovom linijom je dodato
		return Response.status(Response.Status.ACCEPTED).entity("adminDashboard.html#/users").build(); 																							// accepted
	}
}
