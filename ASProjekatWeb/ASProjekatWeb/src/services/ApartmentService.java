package services;

import java.util.Collection;

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

import beans.Apartment;
import beans.CommentForApartment;
import beans.SearchFields;
import beans.User;
import beans.newCommentHelp;
import dao.ApartmentDAO;

@Path("/apartments")
public class ApartmentService {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private static String path="";
	
	public ApartmentService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("apartmentDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	path = contextPath;
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getApartments(){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.findAll();
	}
	
	@GET
	@Path("/getAllActive")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getActiveApartments(){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.getAllActive();
	}
	
	@GET
	@Path("/getHostActive")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getHostActiveApartments(@Context HttpServletRequest request){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		User host = (User) request.getSession().getAttribute("user");
		return dao.getHostActive(host);
	}
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Apartment saveApartment(Apartment apartment, @Context HttpServletRequest request) {
		User host = (User) request.getSession().getAttribute("user");
		apartment.setHost(host);
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.printApartments(path, apartment);
		//return dao.save(apartment);
	}
	
	@GET
	@Path("/find/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Apartment findUser(@PathParam("id") long id) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.findApartment(id);
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Apartment editApartment(Apartment apartment) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.editApartment(path, apartment);
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean removeApartment(String idString) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.removeApartment(path, idString);
	}
	
	@PUT
	@Path("/editUserInApartment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void editUserInApartment(User user) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		dao.editUserInApartment(path, user);
	}
	
	@POST
	@Path("/searchApartments")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Apartment> searchApartments(SearchFields searchFields){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.searchApartments(searchFields);
	}
	
	@POST
	@Path("/searchActiveApartments")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Apartment> searchActiveApartments(SearchFields searchFields){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.searchActiveApartments(searchFields);
	}
	
	@POST
	@Path("/searchHostApartments")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Apartment> searchHostApartments(SearchFields searchFields){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		User host = (User) request.getSession().getAttribute("user");
		return dao.searchHostApartments(searchFields, host);
	}
	
	@PUT
	@Path("/editCommentInApartment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void editCommentInApartment(newCommentHelp comment) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		CommentForApartment com = new CommentForApartment();
		User user = (User) request.getSession().getAttribute("user");
		for(Apartment a: dao.findAll()) {
			if(Long.toString(a.getId()).equals(comment.getApartment())) {
				com = new CommentForApartment(comment.getId(),user, a, comment.getText(), comment.getGrade());
			}
		}
		dao.editCommentInApartment(path, com);
	}
}
