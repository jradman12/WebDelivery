package services;

import java.util.Collection;

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

import beans.Product;
import beans.Restaurant;
import beans.User;
import dao.ManagerDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.RestaurantDAO;
import enums.Role;

@Path("/products")
public class ProductService {

	@Context
	ServletContext ctx;

	@Context
	HttpServletRequest request;

	public ProductService() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora
	// (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("productDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("productDAO", new ProductDAO(contextPath));
		}
	}

	@GET
	@Path("/getRestaurantMenu")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Product> getRestaurantMenu() {
		ProductDAO products = (ProductDAO) ctx.getAttribute("productDAO");

		if (products == null) {
			String contextPath = ctx.getRealPath("");
			products = new ProductDAO(contextPath);
			ctx.setAttribute("productDAO", products);

		}
		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if (user == null || !role.equals(Role.MANAGER)) {
			return null;
		}

		String username = user.getUsername();
		String restID = ManagerDAO.getRestaurantForManager(username);
		System.out.println("man rest's id is " + restID);
		return products.getRestaurantMenu(restID);

	}

	@POST
	@Path("/addNewProduct")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewProduct(Product product) {

		ProductDAO products = (ProductDAO) ctx.getAttribute("productDAO");

		if (products == null) {
			String contextPath = ctx.getRealPath("");
			products = new ProductDAO(contextPath);
			ctx.setAttribute("productDAO", products);

		}
		boolean r = products.addNewProduct(product);
		if (r)
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity("We already have a product with the same name!")
					.build(); // accepted
	}

}
