package services;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Customer;
import beans.User;
import dao.CustomerDAO;

@Path("/customers")
public class CustomerService {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public CustomerService() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("customerDAO") == null) {
			CustomerDAO customerDAO = new CustomerDAO();
			customerDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("customerDAO", customerDAO);
		}
	}
	
	public String getDataDirPath() {
		return (ctx.getRealPath("") + File.separator + "data"+ File.separator);
	}
	
	@GET
	@Path("/getCustomerType")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerType() {
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
				User user = (User) request.getSession().getAttribute("loggedInUser");
		
		Customer c = customerDAO.getCustomerByUsername(user.getUsername());
		System.out.println(c.getUsername());
		System.out.println(" c is not null goddamit");
		System.out.println(" c type " + c.getType().getTypeName());
		return Response.status(Response.Status.ACCEPTED).entity(c.getType()).build();
	}

}
