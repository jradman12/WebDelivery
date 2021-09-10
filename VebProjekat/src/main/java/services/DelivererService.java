package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Deliverer;
import dao.DelivererDAO;

@Path("/deliverers")
public class DelivererService {

	@Context
	ServletContext ctx;
	
	public DelivererService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("delivererDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("delivererDAO", new DelivererDAO(contextPath));
		}
	}
	
	@POST
	@Path("/registration")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(Deliverer newDeliverer) {
		System.out.println("Manager object Ive recieved is : " + newDeliverer);
		DelivererDAO delivererDAO = (DelivererDAO) ctx.getAttribute("delivererDAO");
		if (delivererDAO.getDelivererByUsername(newDeliverer.getUsername()) != null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("We already have deliverer with the same username. Please try another one").build();
		}
		delivererDAO.addNewDeliverer(newDeliverer);
		
		return Response.status(Response.Status.ACCEPTED).entity("http://localhost:8080/VebProjekat/adminDashboard.html").build(); 																						// accepted
	}
}
