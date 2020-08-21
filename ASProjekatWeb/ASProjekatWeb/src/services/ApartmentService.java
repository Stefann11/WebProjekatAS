package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Apartment;
import beans.User;
import dao.ApartmentDAO;
import dao.UserDAO;

@Path("/apartments")
public class ApartmentService {
	
	@Context
	ServletContext ctx;
	
	public ApartmentService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("apartmentDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getProducts(){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Apartment saveUser(Apartment apartment) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.save(apartment);
	}
	
	@GET
	@Path("/find/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Apartment findUser(@PathParam("id") long id) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.findApartment(id);
	}
	
}
