package services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Administrator;
import dao.AdministratorDAO;

@Path("")
public class LoginService {
	
	ServletContext ctx;
	
	public LoginService() {
		// TODO Auto-generated constructor stub
	}

	public void init() {
		if(ctx.getAttribute("adminDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("adminDAO", new AdministratorDAO(contextPath));
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Administrator admin, HttpServletRequest request) {
		System.out.println("HIT ME");
		AdministratorDAO adminDAO = (AdministratorDAO) ctx.getAttribute("adminDAO");
		Administrator loggedAdmin = adminDAO.find(admin.getUsername());
		if (loggedAdmin == null) {
			return Response.status(400).entity("Invalid username and/or password").build();
		}
		request.getSession().setAttribute("admin", loggedAdmin);
		return Response.status(200).build();
	}
}

