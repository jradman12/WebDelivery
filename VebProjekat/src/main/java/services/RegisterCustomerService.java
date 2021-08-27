package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Customer;

import dao.CustomerDAO;


@Path("/registerService")
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
		if (ctx.getAttribute("customerDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("customerDAO", new CustomerDAO(contextPath));
		}
	}
	
	
	@POST
	@Path("/registration")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(Customer customer) {
		System.out.println("Customer object Ive recieved is : " + customer);
		CustomerDAO allCustomerDAO = getCustomers();

		if (allCustomerDAO.getCustomerByUsername(customer.getUsername()) != null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("We have alredy user with same username. Please try another one").build();
		}
		allCustomerDAO.addNewCustomer(customer);

		return Response.status(Response.Status.ACCEPTED).build(); 																						// accepted
	}
	
	private CustomerDAO getCustomers() {
		System.out.println("getCustomers");
		CustomerDAO customers = (CustomerDAO) ctx.getAttribute("customerDAO");
		
		if (customers == null) {
			String contextPath = ctx.getRealPath("");
			customers = new CustomerDAO(contextPath);
			ctx.setAttribute("customerDAO", customers);

		}

		return customers;
	}
	
	

}
