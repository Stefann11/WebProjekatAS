package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
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
import javax.ws.rs.core.Response;

import beans.Amenties;
import beans.AmentiesHelp;
import beans.Apartment;
import dao.AmentiesDAO;
import dao.ApartmentDAO;


@Path("/amenties")
public class AmentiesService {

	@Context
	ServletContext ctx;
	
	private static String path="";
	
	public AmentiesService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("amentiesDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	path = contextPath;
			ctx.setAttribute("amentiesDAO", new AmentiesDAO(contextPath));
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
	public Collection<Amenties> getAmenties(){
		AmentiesDAO dao = (AmentiesDAO) ctx.getAttribute("amentiesDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/save")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newAmenties(Amenties amen){
		AmentiesDAO dao = (AmentiesDAO) ctx.getAttribute("amentiesDAO");
		
		
		for (Amenties amenitie: dao.findAll()) {
			if (amenitie.getId()==amen.getId()){
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Mora biti jedinstveni id.").build();
			}
		}
		
		dao.printAmenties(path, amen);
		
		return Response.ok().entity("adminIndex.html").build();
		//return dao.save(reservation);
	}
	
	@GET
	@Path("/find/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Amenties findAmenties(@PathParam("id") long id) {
		AmentiesDAO dao = (AmentiesDAO) ctx.getAttribute("amentiesDAO");
		return dao.findAmenties(id);
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Amenties editAmenties(Amenties amen) {
		AmentiesDAO dao = (AmentiesDAO) ctx.getAttribute("amentiesDAO");
		return dao.editAmenties(path, amen);
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean removeAmenities(Amenties amenities) {
		AmentiesDAO dao = (AmentiesDAO) ctx.getAttribute("amentiesDAO");
		return dao.delete(path, amenities);
	}
	
}
