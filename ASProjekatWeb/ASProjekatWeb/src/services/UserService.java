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

import beans.Apartment;
import beans.FilterUser;
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
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveUser(User user, @Context HttpServletRequest request) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		for (User oneUser : dao.findAll()) {
			if (oneUser.getUsername().equals(user.getUsername())) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Mora biti jedinstveni username.").build();
			}
		}
		
		request.getSession().setAttribute("user", user);
		dao.printUsers(path, user);
		
		request.getSession().setAttribute("user", user);
		return Response.ok().entity("guestIndex.html").build();
		
	
		
		
		//return dao.save(user);
	}
	
	@POST
	@Path("/saveHost")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveHost(User user, @Context HttpServletRequest request) {
		User admin = (User) request.getSession().getAttribute("user");
		if (admin!=null) {
			if (admin.getRole()!=Role.ADMINISTRATOR) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti domacin.").build();
			} else {
				UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
				for (User oneUser : dao.findAll()) {
					if (oneUser.getUsername().equals(user.getUsername())) {
						return Response.status(Response.Status.BAD_REQUEST)
								.entity("Mora biti jedinstveni username.").build();
					}
				}
				
				dao.printUsers(path, user);
				
				return Response.ok().entity("adminIndex.html").build();
			}
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti administrator.").build();
		}
		
	
		
		
		//return dao.save(user);
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editUser(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		dao.edit(path, user);
		User user2 = dao.findUser(user.getUsername());
		if (user2.getRole().equals(Role.GUEST)) {
			request.getSession().setAttribute("user", user2);
			return Response.ok().entity("guestIndex.html").build();
		}
		if (user2.getRole().equals(Role.HOST)) {
			request.getSession().setAttribute("user", user2);
			return Response.ok().entity("hostIndex.html").build();
		}
		if (user2.getRole().equals(Role.ADMINISTRATOR)) {
			request.getSession().setAttribute("user", user2);
			return Response.ok().entity("adminIndex.html").build();
		}
		request.getSession().setAttribute("user", user2);
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
	@Path("/listUsersForHost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public  Collection<User> listUsersForHost(@Context HttpServletRequest request){
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		User host = (User) request.getSession().getAttribute("user");
		return dao.listUsersForHost(host);
	}
	
	@GET
	@Path("/loggedUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
	
	@PUT
	@Path("/editApartmentInUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void editApartmentInUser(Apartment apartment) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		dao.editApartmentInUser(path, apartment);
	}
	
	@POST
	@Path("/addApartmentToHost")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addApartmentToHost(Apartment apartment, @Context HttpServletRequest request) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		User host = (User) request.getSession().getAttribute("user");
		
		if (host!=null) {
		
			if (host.getRole()!=Role.HOST) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti domacin.").build();
			} else {
				dao.addApartmentToHost(apartment, host, path);
				return Response.ok().entity("allAmenities.html?idApartment=" + apartment.getId()).build();
			}
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti domacin.").build();
		}
	}
	
	@POST
	@Path("/searchUsers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<User> searchUsers(FilterUser filterUser) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.searchUsers(filterUser);
	}
	
	@POST
	@Path("/searchUsersForHost")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<User> searchUsersForHost(FilterUser filterUser) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		User host = (User) request.getSession().getAttribute("user");
		return dao.searchUsersForHost(host, filterUser);
	}
	
}
