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
import javax.ws.rs.core.Response;

import beans.Amenties;
import beans.AmentiesHelp;
import beans.Apartment;
import beans.Role;
import beans.User;
import dao.AmentiesDAO;
import dao.ApartmentDAO;


@Path("/amenties")
public class AmentiesService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
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
		User host = (User) request.getSession().getAttribute("user");
		if (host!=null) {
			if (host.getRole()!=Role.ADMINISTRATOR) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti amdinistrator.").build();
			} else {
				AmentiesDAO dao = (AmentiesDAO) ctx.getAttribute("amentiesDAO");
				
				
				for (Amenties amenitie: dao.findAll()) {
					if (amenitie.getId()==amen.getId()){
						return Response.status(Response.Status.BAD_REQUEST)
								.entity("Mora biti jedinstveni id.").build();
					}
				}
				
				dao.printAmenties(path, amen);
				
				return Response.ok().entity("adminIndex.html").build();
			}
		}else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti amdinistrator.").build();
		}
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
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editAmenties(Amenties amen) {
		User host = (User) request.getSession().getAttribute("user");
		if (host!=null) {
			if (host.getRole()!=Role.ADMINISTRATOR) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti amdinistrator.").build();
			} else {
				AmentiesDAO dao = (AmentiesDAO) ctx.getAttribute("amentiesDAO");
				dao.editAmenties(path, amen);
				return Response.ok().entity("tableAmenities.html").build();
			}
		}else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti amdinistrator.").build();
		}
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeAmenities(Amenties amenities) {
		User host = (User) request.getSession().getAttribute("user");
		if (host!=null) {
			if (host.getRole()!=Role.ADMINISTRATOR) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti amdinistrator.").build();
			} else {
				AmentiesDAO dao = (AmentiesDAO) ctx.getAttribute("amentiesDAO");
				dao.delete(path, amenities);
				return Response.ok().entity("tableAmenities.html").build();
			}
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti amdinistrator.").build();
		}
					
	}
	
}
