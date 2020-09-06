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

import beans.CommentForApartment;
import beans.User;
import dao.CommentDAO;

@Path("/comments")
public class CommentService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private static String path="";
	
	
	public CommentService() {
	}
	

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("commentDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	path = contextPath;
			ctx.setAttribute("commentDAO", new CommentDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<CommentForApartment> getComments(){
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CommentForApartment newComment(CommentForApartment comm){
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		User user = (User) request.getSession().getAttribute("user");
		return dao.printComment(path, comm, user);
		//return dao.save(reservation);
	}
	
	@GET
	@Path("/find/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CommentForApartment findComment(@PathParam("id") long id) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.findComment(id);
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CommentForApartment editComment(CommentForApartment comm) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		User user = (User) request.getSession().getAttribute("user");
		return dao.editComment(path, comm, user);
	}
	
}
