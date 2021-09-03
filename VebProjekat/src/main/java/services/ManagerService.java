package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Customer;
import beans.Product;
import beans.Restaurant;
import beans.User;
import dao.CustomerDAO;
import dao.ManagerDAO;
import enums.Role;



@Path("/manager")
public class ManagerService {

	
	@Context
	ServletContext ctx;
	
	
	public ManagerService() {
		
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("managerDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("managerDAO", new ManagerDAO(contextPath));
		}
	}
	
	
	
	
	@GET
	@Path("/getRestaurantFromLoggedManager")
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getRestaurant(@Context HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.MANAGER)) {
			return null;
		}
		
		String username = user.getUsername();	
		return ManagerDAO.getRestaurantForManager(username);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@POST
	@Path("/addNewArticle")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addArticle(Product product,@Context HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loggedInUser");
		System.out.println("dosao");
		if(user == null || !user.getRole().equals((Role.MANAGER))) {
			return Response.status(403).entity("Ne možete pristupiti ovoj funkcionalnosti!").build();
		}
		
		for(Product p : ManagerDAO.getProductsForRestaurant(user.getUsername())) {
			if(p.getName().equals(product.getName())) {
				return Response.status(403).entity("Ne mogu postojati dva proizvoda sa istim nazivom!").build();
			}
		}

		boolean success=ManagerDAO.addNewProductToManagersRestaurant(user.getUsername(), product);	
		if(success) {
			return Response.status(Response.Status.ACCEPTED).entity("http://localhost:8080/VebProjekat/managerRestaurant.html").build();
		}else {
			return Response.status(400).entity("nije dodat novi artikl!").build();
		}
	}
}
