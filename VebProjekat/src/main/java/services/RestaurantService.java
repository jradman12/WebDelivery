package services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import beans.Product;
import beans.Restaurant;
import beans.User;
import dao.ManagerDAO;
import dao.RestaurantDAO;
import dto.ProductForCartDTO;

@Path("/restaurants")
public class RestaurantService {

	@Context
	ServletContext ctx;

	@Context
	HttpServletRequest request;

	public RestaurantService() {

	}

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora
	// (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("restaurantDAO") == null) {
			RestaurantDAO restaurantDAO = new RestaurantDAO();
			restaurantDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("restaurantDAO", restaurantDAO);
		}
	}
	public String getDataDirPath() {
		return (ctx.getRealPath("") + File.separator + "data"+ File.separator);
	}

	@GET
	@Path("/getAllRestaurants")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Restaurant> getAllRestaurants() {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");

		Map<String, Restaurant> restaurants = new HashMap<>();

		return restaurantDAO.getAllAvailable();
	}

	@POST
	@Path("/registerNewRestaurant")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(Restaurant restaurant) {
		RestaurantDAO restaurantDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");

		/*
		 * if (allRestaurantsDAO.getRestaurantById(restaurant.getId()) != null) { return
		 * Response.status(Response.Status.BAD_REQUEST)
		 * .entity("We have alredy restaurant with same id. Please try another one").
		 * build(); }
		 */
		restaurantDAO.addNewRestaurant(restaurant);

		return Response.status(Response.Status.ACCEPTED).build(); // accepted
	}

	private RestaurantDAO getRestaurants() {
		RestaurantDAO restaurants = (RestaurantDAO) ctx.getAttribute("restaurantDAO");

		return restaurants;
	}

	@DELETE
	@Path("delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRestaurant(@PathParam("id") String id) {

		RestaurantDAO restaurantsDAO = getRestaurants();
		boolean deleted = restaurantsDAO.deleteRestaurant(id);
		if (deleted) {
			return Response.status(Response.Status.OK)
					.entity("Logičko brisanje za restoran sa idijem" + id + "je uspješno izvršeno!").build();
		} else {
			return Response.status(404).entity("Logičko brisanje nije uspjelo!").build();
		}
	}

	@GET
	@Path("getCurrentRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getCurrentRestaurant() {
		RestaurantDAO rDAO = getRestaurants(); // this will set em
		String currentRestID = (String) ctx.getAttribute("currentRestID");
		System.out.println("tryna get the currentRestID attribute, rn it is " + currentRestID);
		return rDAO.getRestaurantById(currentRestID);
	}

	@POST
	@Path("setCurrentRestaurant")
	@Consumes(MediaType.APPLICATION_JSON)
	public void setCurrentRestaurant(Restaurant rest) {
		System.out.println("setting attribute currentRestID on " + rest.getId());
		ctx.setAttribute("currentRestID", rest.getId());
		System.out.println("currentRestID attribute now is " + (String) ctx.getAttribute("currentRestID"));
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	/// 9/6/21 added after some changes of product / manager / rest chain

	@GET
	@Path("/getManagersRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getManagersRestaurant(String username) {
		RestaurantDAO rDAO = getRestaurants();
		ManagerDAO mDAO = new ManagerDAO();
		mDAO.setBasePath(getDataDirPath());
		////////////
		
		System.out.println("Restorani: ");
		for(Restaurant r : rDAO.restaurants.values()){
			System.out.println(r.getName());
		}
		
		//////////
		User manager = (User) request.getSession().getAttribute("loggedInUser");
		
		
		for (Restaurant rest : rDAO.restaurants.values()) {
			System.out.println("in for loop");
			
			mDAO.setBasePath(getDataDirPath());
			if (rest.getId().equals(mDAO.getRestaurantForManager(manager.getUsername()))) {
				System.out.println("found an equal one - " + rest.getId() + ", " + rest.getName());
				return Response.status(Response.Status.ACCEPTED).entity(rest).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).entity("No restaurants found.").build();
	}

	@POST
	@Path("/addNewProduct")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewProduct(Product product) {
		RestaurantDAO rDAO = getRestaurants();
		ManagerDAO mDAO = new ManagerDAO();
		mDAO.setBasePath(getDataDirPath());
		Restaurant r = null;
		User manager = (User) request.getSession().getAttribute("loggedInUser");
		for (Restaurant rest : rDAO.restaurants.values()) {
			if (rest.getId().equals(mDAO.getRestaurantForManager(manager.getUsername()))){
					r = rest;
					for (Product prod : rest.getMenu()) {
						if (prod.getName().equals(product.getName()))
							return Response.status(Response.Status.BAD_REQUEST)
									.entity("There already is a product with that name. Please, try another one.").build();
					}
				}
			System.out.println("get returns: " + rDAO.restaurants.get(rest.getId()));
		}
		
			rDAO.addNewProduct(r.getId(), product);
			
			rDAO.saveRestaurantsJSON();

		
		return Response.status(Response.Status.ACCEPTED).entity("New product has been added.").build();

	}
	
	@PUT
	@Path("/updateProduct/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProduct(Product updatedProduct, @PathParam("id") String id) {
		RestaurantDAO rDAO = getRestaurants();
		rDAO.updateProduct(id, updatedProduct);
		return Response.status(Response.Status.ACCEPTED).entity("Product successfully updated.").build();
	}
	
	@GET
	@Path("/getProductsOfCurrentRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductsOfCurrentRestaurant() {
		
		System.out.println("hit me");
		
		
		RestaurantDAO restDAO = (RestaurantDAO) ctx.getAttribute("restaurantDAO");
		String currentRestID = (String) ctx.getAttribute("currentRestID");
		
		System.out.println("current rest: " + currentRestID);
		
		for(Restaurant r : restDAO.restaurants.values()) {
			System.out.println("in for, rest name is " + r.getName());
			if(r.getId().equals(currentRestID)) {
				
				List<ProductForCartDTO> retProducts = new ArrayList<ProductForCartDTO>();
				System.out.println("found equal, made retprods");
					
				
				for(Product p : r.getMenu()) {
					retProducts.add(new ProductForCartDTO(p));
				}
				
				for(ProductForCartDTO xx : retProducts)
					System.out.println("pfc dto is " + xx);
				
				return Response.status(Response.Status.ACCEPTED).entity(retProducts).build();
			}
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("Sth aint right!").build();
	}
	
}
