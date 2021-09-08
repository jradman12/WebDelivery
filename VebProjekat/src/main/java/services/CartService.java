package services;

import java.io.File;

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

import beans.Cart;
import beans.CartItem;
import beans.User;
import dao.CartDAO;

@Path("/cart")
public class CartService {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public CartService() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
    	System.out.println("in cartService init, real path is " + ctx.getRealPath(""));

		if (ctx.getAttribute("cartDAO") == null) {
	    	String contextPath = ctx.getRealPath("") + "WEB-INF" + File.separator + "classes" + File.separator + "data"
					+ File.separator + "carts.json";
			ctx.setAttribute("cartDAO", new CartDAO(contextPath));
		}
	}
	
	@POST
	@Path("/addCartItem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCartItem(CartItem newItem) {
		CartDAO cartDAO = (CartDAO) ctx.getAttribute("cartDAO");
    	System.out.println("in addCartItem, here attribute cartDAO is null? " + (ctx.getAttribute("cartDAO") == null));

		User user = (User) request.getSession().getAttribute("loggedInUser");
		
    	System.out.println("in addCartItem, user thats logged in is " + user.getFistName());

		for(Cart cart : cartDAO.carts.values()) {
			System.out.println("in for, cart>> customerID is " + cart.getCustomerID() );
			if(cart.getCustomerID().equals(user.getUsername())) {
				cart.getItems().add(newItem);
				cartDAO.saveCartsJSON();
				return Response.status(Response.Status.ACCEPTED).entity(newItem).build();

			}
		}
		
		return Response.status(Response.Status.NOT_FOUND).entity("We are sorry, but this action cant be done!").build();
	}
	
	
}
