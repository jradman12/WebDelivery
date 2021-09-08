package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
		if (ctx.getAttribute("cartDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("cartDAO", new CartDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getCart")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCart() {
		CartDAO cartDAO = (CartDAO) ctx.getAttribute("cartDAO");
		User user = (User) request.getSession().getAttribute("loggedInUser");
		for(Cart cart : cartDAO.carts.values()) {
			if(cart.getCustomerID().equals(user.getUsername())) {
				return Response.status(Response.Status.ACCEPTED).entity(cart).build();
			}
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("Either cart doesnt exist, or u arent logged in.").build();
	}
	
	// update item amount 
	
	// remove from cart
	
	@POST
	@Path("/addCartItem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCartItem(CartItem newItem) {
		CartDAO cartDAO = (CartDAO) ctx.getAttribute("cartDAO");
    	//System.out.println("in addCartItem, here attribute cartDAO is null? " + (ctx.getAttribute("cartDAO") == null));

		User user = (User) request.getSession().getAttribute("loggedInUser");
		
    	//System.out.println("in addCartItem, user thats logged in is " + user.getFistName());

		for(Cart cart : cartDAO.carts.values()) {
			//System.out.println("in for, cart>> customerID is " + cart.getCustomerID() );
			if(cart.getCustomerID().equals(user.getUsername())) { 
				
				//when we find the cart, we gotta check if this product is already there
				if(!cart.ciAlreadyExists(newItem.getProduct().getName())){
					cart.getItems().add(newItem);  // NOT: just add the new one
				}else {
					for(CartItem ci : cart.getItems()) { // IS THERE: find it and change only its amount
						if(ci.getProduct().getName().equals(newItem.getProduct().getName())) {
							ci.setAmount(ci.getAmount() + newItem.getAmount()); 
						}
					}
				}
				cart.setPrice(cart.getPrice() + newItem.getProduct().getPrice()); // TODO: check if this works 
				cartDAO.saveCartsJSON();
				return Response.status(Response.Status.ACCEPTED).entity(newItem).build();

			}
		}
		
		return Response.status(Response.Status.NOT_FOUND).entity("We are sorry, but this action cant be done!").build();
	}
	
	@DELETE
	@Path("/removeCartItem/{productName}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeCartItem(@PathParam("productName") String productName) {
		System.out.println("in removeCart, here product name we got is " + productName);
		CartDAO cartDAO = (CartDAO) ctx.getAttribute("cartDAO");
		User user = (User) request.getSession().getAttribute("loggedInUser");
		for(Cart cart : cartDAO.carts.values()) {
			if(cart.getCustomerID().equals(user.getUsername())) {
				CartItem ciToRemove = null;
				for(CartItem ci : cart.getItems()) {
					if(ci.getProduct().getName().equals(productName)) {
						ciToRemove = ci;
					}
				}
				if(ciToRemove != null) {
					cart.setPrice(cart.getPrice() - ciToRemove.getProduct().getPrice() * ciToRemove.getAmount());
					cart.getItems().remove(ciToRemove);
					cartDAO.saveCartsJSON();
				}
			}
		}
	}
	
	@PUT
	@Path("/updateCartItem/{productName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCartItem(CartItem passedCartItem, @PathParam("productName") String productName) {
		System.out.println("in updateCartItem, here product name we got is " + productName);
		CartDAO cartDAO = (CartDAO) ctx.getAttribute("cartDAO");
		User user = (User) request.getSession().getAttribute("loggedInUser");
		for(Cart cart : cartDAO.carts.values()) {
			if(cart.getCustomerID().equals(user.getUsername())) {
				CartItem ciToUpdate = null;
				for(CartItem ci : cart.getItems()) {
					if(ci.getProduct().getName().equals(productName)) {
						ciToUpdate = ci;
					}
				}
				if(ciToUpdate != null) {
					cart.setPrice(cart.getPrice() - ciToUpdate.getProduct().getPrice() * ciToUpdate.getAmount());
					cart.getItems().get(cart.getItems().indexOf(ciToUpdate)).setAmount(passedCartItem.getAmount());
					cart.setPrice(cart.getPrice() + ciToUpdate.getProduct().getPrice() * ciToUpdate.getAmount());

					cartDAO.saveCartsJSON();
					//cart.getItempassedCartItem.getAmount()s().set(cart.getItems().indexOf(ciToUpdate), passedCartItem);
					return Response.status(Response.Status.ACCEPTED).entity(passedCartItem).build();
				}
				return Response.status(Response.Status.BAD_REQUEST).entity("this cart item doesnt seem to exist here..").build();
			}
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("couldnt do the update...").build();

	}
	
	
}
