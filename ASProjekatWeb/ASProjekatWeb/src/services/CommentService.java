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

import beans.Apartment;
import beans.CommentForApartment;
import beans.User;
import beans.newCommentHelp;
import dao.ApartmentDAO;
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
	
	private ApartmentDAO getApartmani() { 
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		if (apartmentDAO == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(contextPath));
		}
		return apartmentDAO;
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
	public CommentForApartment newComment(newCommentHelp comment){
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		
		ApartmentDAO dao2 =  (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		Apartment apartment = new Apartment();
		User user = (User) request.getSession().getAttribute("user");
		Collection<Apartment> apartments = dao2.findAll();
		for(Apartment ap : apartments) {
			if(Long.toString(ap.getId()).equals(comment.getApartment())) {
				apartment = ap;
				break;
			}
		}
		CommentForApartment com = new CommentForApartment(comment.getId(), user, apartment, comment.getText(), comment.getGrade());		
		return dao.printComment(path, com, user);		
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
	
	@GET
	@Path("/commentsForHost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<CommentForApartment> getCommentsForHost(@Context HttpServletRequest request){
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		User host = (User) request.getSession().getAttribute("user");
		return dao.getCommentsForHost(host);
	}
	
	@PUT
	@Path("/editUserInComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void editUserInComment(User user) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		dao.editUserInComment(path, user);
	}
	
	@PUT
	@Path("/editApartmentInComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void editApartmentInComment(Apartment apartment) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		dao.editApartmentInComment(path, apartment);
	}
	
	@PUT
	@Path("/approve")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean approveComment(String idString) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.approveComment(path, idString);
	}
	
	@GET
	@Path("/allApproved")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<CommentForApartment> getApprovedComments(){
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.findAllApproved();
	}
	
}
