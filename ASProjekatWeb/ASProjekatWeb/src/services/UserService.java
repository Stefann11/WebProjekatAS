package services;

import java.util.Collection;

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

import beans.Role;
import beans.User;
import dao.UserDAO;

@Path("/users")
public class UserService {
	
	private static String path="";
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
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
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editUser(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		dao.edit(path, user);
		if (user.getRole().equals(Role.GUEST)) {
			request.getSession().setAttribute("user", user);
			return Response.ok().entity("guestIndex.html").build();
		}
		if (user.getRole().equals(Role.HOST)) {
			request.getSession().setAttribute("user", user);
			return Response.ok().entity("hostIndex.html").build();
		}
		if (user.getRole().equals(Role.ADMINISTRATOR)) {
			request.getSession().setAttribute("user", user);
			return Response.ok().entity("adminIndex.html").build();
		}
		request.getSession().setAttribute("user", user);
		return Response.ok().entity("index.html").build();
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
	public Response loginUser(User sentUser,  @Context HttpServletRequest request) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		User user = dao.findUser(sentUser.getUsername());
		if (user == null || !user.getPassword().equals(sentUser.getPassword())) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Pogrešno ste uneli korisničko ime/lozinku. Pokušajte ponovo.").build();
		}
		if (user.getRole().equals(Role.GUEST)) {
			request.getSession().setAttribute("user", user);
			return Response.ok().entity("guestIndex.html").build();
		}
		if (user.getRole().equals(Role.HOST)) {
			request.getSession().setAttribute("user", user);
			return Response.ok().entity("hostIndex.html").build();
		}
		if (user.getRole().equals(Role.ADMINISTRATOR)) {
			request.getSession().setAttribute("user", user);
			return Response.ok().entity("adminIndex.html").build();
		}
		request.getSession().setAttribute("user", user);
		return Response.ok().entity("index.html").build();
	}
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/loggedUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
	
}
