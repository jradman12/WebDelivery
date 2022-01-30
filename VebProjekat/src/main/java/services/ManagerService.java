package services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Customer;
import beans.Manager;
import beans.Restaurant;
import beans.User;
import dao.CustomerDAO;
import dao.ManagerDAO;
import dao.RestaurantDAO;
import dao.UserDAO;
import dto.UserDTO;
import enums.Role;



@Path("/managers")
public class ManagerService {

	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public ManagerService() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("managerDAO") == null) {
			ManagerDAO managerDAO = new ManagerDAO();
			managerDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("managerDAO", managerDAO);
		}
		if (ctx.getAttribute("usersDAO") == null) {
			UserDAO usersDAO = new UserDAO();
			usersDAO.setBasePath(getDataDirPath());
			ctx.setAttribute("usersDAO", usersDAO);	
		}
	}
	
	public String getDataDirPath() {
		return (ctx.getRealPath("") + File.separator + "data"+ File.separator);
	}
	
	@POST
	@Path("/registration")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(Manager newManager) {
		
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		UserDAO userDAO = (UserDAO) ctx.getAttribute("usersDAO");

		if (managerDAO.getManagerByUsername(newManager.getUsername()) != null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("We already have manager with the same username. Please try another one").build();
		}
		newManager.setRole(Role.MANAGER);
		userDAO.addUser(newManager);
		managerDAO.addManager(newManager);
		
		return Response.status(Response.Status.ACCEPTED).entity("adminDashboard.html#/users").build(); 		
	}
	
	
	@PUT
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Manager updateProduct(Restaurant newRest, @PathParam("username") String username) {
		ManagerDAO dao = (ManagerDAO) ctx.getAttribute("managerDAO");
		Manager manager = dao.getManagerByUsername(username);
		dao.updateManagersRest(manager, newRest);
		return manager;
	}
	
	
	// 9/6/21 update
	@GET
	@Path("/getRestaurantFromLoggedManager")
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getRestaurant(@Context HttpServletRequest request){
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		User user = (User) request.getSession().getAttribute("loggedInUser");
		Role role = user.getRole();
		System.out.println(user.getRole());
		if(user == null || !role.equals(Role.MANAGER)) {
			return null;
		}
		
		String username = user.getUsername();	
		String restID =  managerDAO.getRestaurantForManager(username);
		RestaurantDAO rDAO = new RestaurantDAO();
		rDAO.setBasePath(getDataDirPath());
		return rDAO.getRestaurantById(restID);
		
	}
//	
//	@POST
//	@Path("/addNewArticle")
//	@Produces(MediaType.TEXT_HTML)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response addArticle(Product product,@Context HttpServletRequest request) {
//		User user = (User) request.getSession().getAttribute("loggedInUser");
//		System.out.println("dosao");
//		if(user == null || !user.getRole().equals((Role.MANAGER))) {
//			return Response.status(403).entity("Ne možete pristupiti ovoj funkcionalnosti!").build();
//		}
//		
//		for(Product p : ManagerDAO.getProductsForRestaurant(user.getUsername())) {
//			if(p.getName().equals(product.getName())) {
//				return Response.status(403).entity("Ne mogu postojati dva proizvoda sa istim nazivom!").build();
//			}
//		}
//
//		boolean success=ManagerDAO.addNewProductToManagersRestaurant(user.getUsername(), product);	
//		if(success) {
//			return Response.status(Response.Status.ACCEPTED).entity("http://localhost:8080/VebProjekat/managerRestaurant.html").build();
//		}else {
//			return Response.status(400).entity("nije dodat novi artikl!").build();
//		}
//	}

	@GET
	@Path("/getAllAvailableManagers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Manager> getAllAvailableManagers(@Context HttpServletRequest request){
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		List<Manager> availableManager = new ArrayList<Manager>();
		for(Manager m : managerDAO.getAllAvailable()) {
			if(m.getRestaurantID() == null) {
				availableManager.add(m);
			}
		}
		return availableManager;
		
	}
	

}
