package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import beans.Restaurant;
import dao.RestaurantDAO;
@Path("/restaurants")
public class RestaurantService {
	
	@Context
	ServletContext ctx;
	
	public RestaurantService() {
		
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("restaurantDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("restaurantDAO", new RestaurantDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getAllRestaurants")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Restaurant> getAllRestaurants(){
		Map<String, Restaurant> restaurants = new HashMap<>();
		RestaurantDAO.loadRestaurants("");
		restaurants = RestaurantDAO.restaurants;
		System.out.println("get users request returns following users: ");
		for(Map.Entry<String, Restaurant> entry : restaurants.entrySet()) {
			System.out.println(entry.getValue());
		}
		
		
		
		return restaurants.values();
	}

	
	@POST
	@Path("/addNewRestaurant")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(Restaurant restaurant) {
		System.out.println("Customer object Ive recieved is : " + restaurant);
		RestaurantDAO allRestaurantsDAO = getRestaurants();

		if (allRestaurantsDAO.getRestaurantById(restaurant.getId()) != null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("We have alredy restaurant with same id. Please try another one").build();
		}
		RestaurantDAO.addNewRestaurant(restaurant);

		return Response.status(Response.Status.ACCEPTED).build(); 																						// accepted
	}
	
	private RestaurantDAO getRestaurants() {
		System.out.println("getCustomers");
		RestaurantDAO restaurants = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		
		if (restaurants == null) {
			String contextPath = ctx.getRealPath("");
			restaurants = new RestaurantDAO(contextPath);
			ctx.setAttribute("restaurantDAO", restaurants);

		}

		return restaurants;
	}

}
