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
import beans.Reservation;
import beans.User;
import dao.ApartmentDAO;
import dao.ReservationDAO;

@Path("/reservations")
public class ReservationService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
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
	public Collection<Reservation> getReservations(){
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/save")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveReservation(Reservation reservation, @Context HttpServletRequest request){
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		User user = (User) request.getSession().getAttribute("user");
		reservation.setGuest(user);
		ApartmentDAO apartmentDAO = getApartmani();
		Apartment apartment = apartmentDAO.findApartment(reservation.getApartment().getId());
		reservation.setApartment(apartment);
		double totalPrice = 0;
		if (apartment!=null) {
			totalPrice = apartment.getPriceForOneNight()*reservation.getNumberOfOvernights();
		}
		reservation.setTotalPrice(totalPrice);
		dao.printReservations(path, reservation);
		return Response.ok().entity("allActiveApartments.html").build();
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
	
	@GET
	@Path("/userReservations")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Reservation> getUserReservations(@Context HttpServletRequest request){
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		User user = (User) request.getSession().getAttribute("user");
		return dao.getUserReservations(user);
	}
	
	@GET
	@Path("/hostReservations")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Reservation> getHostReservations(@Context HttpServletRequest request){
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		User host = (User) request.getSession().getAttribute("user");
		return dao.getHostReservations(host);
	}
	
	@POST
	@Path("/whithdrawal")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean whithdrawalReservation(Reservation reservation){
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		return dao.whithdrawalReservation(path, reservation);
	}
	
	@POST
	@Path("/accept")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean acceptReservation(Reservation reservation){
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		return dao.acceptReservation(path, reservation);
	}
	
	@POST
	@Path("/reject")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean rejectReservation(Reservation reservation){
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		return dao.rejectReservation(path, reservation);
	}
	
	@POST
	@Path("/complete")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean completeReservation(Reservation reservation){
		ReservationDAO dao = (ReservationDAO) ctx.getAttribute("reservationDAO");
		return dao.completeReservation(path, reservation);
	}
}
