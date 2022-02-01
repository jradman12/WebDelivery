package services;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Customer;
import dao.CartDAO;
import dao.CustomerDAO;
import dao.UserDAO;
import enums.Role;


@Path("")
public class RegisterCustomerService {
	
	@Context
	ServletContext ctx;
	
	public RegisterCustomerService() {
		
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("usersDAO") == null) {
			UserDAO usersDAO = new UserDAO();
			usersDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("usersDAO", usersDAO);	
		}
		if (ctx.getAttribute("customerDAO") == null) {
			CustomerDAO customerDAO = new CustomerDAO();
			customerDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("customerDAO", customerDAO);	
		}
		if (ctx.getAttribute("cartDAO") == null) {
			CartDAO cartDAO = new CartDAO();
	    	cartDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("cartDAO", cartDAO);	
		}
		
	}
	
	public String getDataDirPath() {
		return (ctx.getRealPath("") + File.separator + "data"+ File.separator);
	}
	
	
	@POST
	@Path("/registration")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(Customer customer) {
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		UserDAO userDAO = (UserDAO) ctx.getAttribute("usersDAO");
		CartDAO cartDAO = (CartDAO) ctx.getAttribute("cartDAO");

		if (customerDAO.getCustomerByUsername(customer.getUsername()) != null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("We have alredy user with same username. Please try another one!").build();
		}
		customer.setRole(Role.CUSTOMER);
		
		userDAO.addUser(customer);
		customerDAO.addCustomer(customer);
		cartDAO.addCartForNewUser(customer.getUsername());

		return Response.status(Response.Status.OK).build(); 																						// accepted
	}
	
	
	
	

}
