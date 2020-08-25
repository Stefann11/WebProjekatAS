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

import beans.Apartment;
import beans.Reservation;
import dao.ApartmentDAO;
import dao.ReservationDAO;

@Path("/reservations")
public class ReservationService {

	@Context
	ServletContext ctx;
	
	private static String path="";
	
	public ReservationService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("reservationDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	path = contextPath;
			ctx.setAttribute("reservationDAO", new ReservationDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Reservation> getReservations(){
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Reservation newReservation(Reservation reservation){
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		return dao.printReservations(path, reservation);
		//return dao.save(reservation);
	}
	
	@GET
	@Path("/find/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Reservation findReservation(@PathParam("id") long id) {
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		return dao.findReservation(id);
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Reservation editReservation(Reservation reservation) {
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		return dao.editReservation(path, reservation);
	}
}
