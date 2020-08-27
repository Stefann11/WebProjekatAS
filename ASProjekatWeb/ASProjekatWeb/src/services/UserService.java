package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
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

import beans.Role;
import beans.User;
import dao.UserDAO;

@Path("/users")
public class UserService {
	
	private static String path="";
	
	@Context
	ServletContext ctx;
	
	public UserService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("userDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	path = contextPath;
			ctx.setAttribute("userDAO", new UserDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getUsers(){
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		//dao.findAll();
		return dao.findAll();
	}
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User saveUser(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		for (User oneUser : dao.findAll()) {
			if (oneUser.getUsername().equals(user.getUsername())) {
				Response.status(Response.Status.BAD_REQUEST)
						.entity("Mora biti jedinstveni username.").build();
				return null;
			}
		}
		return dao.printUsers(path, user);
		//return dao.save(user);
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User editUser(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.edit(path, user);
	}
	
	@GET
	@Path("/find/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findUser(@PathParam("username") String username) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.findUser(username);
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginUser(User sentUser) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		User user = dao.findUser(sentUser.getUsername());
		if (user == null || !user.getPassword().equals(sentUser.getPassword())) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Pogrešno ste uneli korisničko ime/lozinku. Pokušajte ponovo.").build();
		}
		if (user.getRole().equals(Role.GUEST)) {
			return Response.ok().entity("guestIndex.html").build();
		}
		return Response.ok().entity("index.html").build();
	}
	
	
}
