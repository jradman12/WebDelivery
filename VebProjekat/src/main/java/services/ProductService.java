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

import beans.Product;
import beans.Restaurant;
import beans.User;
import dao.ManagerDAO;
import dao.RestaurantDAO;
import dto.ProductDTO;

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
	public void init() {
		
//		if (ctx.getAttribute("restaurantDAO") == null) {
//			String contextPath = ctx.getRealPath("");
//			ctx.setAttribute("restaurantDAO", new RestaurantDAO(contextPath));
//		}
	}

	@GET
	@Path("getCurrentProduct")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductDTO getCurrentProduct() {
		RestaurantDAO rDAO = new RestaurantDAO(""); 
		ManagerDAO mDAO = new ManagerDAO("");
		
		String currentProduct = (String) ctx.getAttribute("currentProduct");
		System.out.println("tryna get the currentProduct attribute, rn it is " + currentProduct);

		User manager = (User) request.getSession().getAttribute("loggedInUser");
		Restaurant r = null;
		for (Restaurant rest : rDAO.restaurants.values()) {
			if (rest.getId().equals(mDAO.getRestaurantForManager(manager.getUsername()))) {
				r = rest;
			}
		}
		return new ProductDTO(rDAO.getProductByName(r.getId(), currentProduct), r.getId());
	}

	@POST
	@Path("setCurrentProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	public void setCurrentProduct(ProductDTO productDTO) {
		System.out.println("setting attribute currentProduct on " + productDTO.product.getName());
		ctx.setAttribute("currentProduct", productDTO.product.getName());
		System.out.println("currentRestID attribute now is " + (String) ctx.getAttribute("currentProduct"));
	}

	
	
}
