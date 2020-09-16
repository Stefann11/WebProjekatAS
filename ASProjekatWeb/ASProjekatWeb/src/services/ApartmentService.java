package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import beans.CommentForApartment;
import beans.Reservation;
import beans.Role;
import beans.SearchFields;
import beans.User;
import beans.newCommentHelp;
import dao.AmentiesDAO;
import dao.ApartmentDAO;
import dao.ReservationDAO;

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
	
	private ReservationDAO getReservations() { 
		ReservationDAO reservationDAO = (ReservationDAO) ctx.getAttribute("reservationDAO");
		if (reservationDAO == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("reservationDAO", new ReservationDAO(contextPath));
		}
		return reservationDAO;
	}
	
	private AmentiesDAO getAmenties() { 
		AmentiesDAO amentiesDAO = (AmentiesDAO) ctx.getAttribute("amentiesDAO");
		if (amentiesDAO == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("amentiesDAO", new AmentiesDAO(contextPath));
		}
		return amentiesDAO;
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
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveApartment(Apartment apartment, @Context HttpServletRequest request) {
		User host = (User) request.getSession().getAttribute("user");
		if (host!=null) {
		
			if (host.getRole()!=Role.HOST) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti domacin.").build();
			} else {
				apartment.setHost(host);
				ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
				
				for (Apartment oneApartment: dao.findAll()) {
					if (oneApartment.getId() == apartment.getId()) {
						return Response.status(Response.Status.BAD_REQUEST)
								.entity("Mora biti jedinstveni id.").build();
					}
				}
				
				dao.printApartments(path, apartment);
				return Response.ok().entity("allAmenities.html?idApartment=" + apartment.getId()).build();
			}
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti domacin.").build();
		}
		//return dao.save(apartment);
	}
	
	@GET
	@Path("/find/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Apartment findUser(@PathParam("id") long id) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.findApartment(id);
	}
	
	@POST
	@Path("/findOne")
	@Produces(MediaType.APPLICATION_JSON)
	public Apartment findUser(Apartment apartment) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.findOne(apartment);
	}
	
	@POST
	@Path("/edit")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editApartment(Apartment apartment) {
		User host = (User) request.getSession().getAttribute("user");
		if (host!=null) {
			if (host.getRole()==Role.GUEST) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti domacin ili amdinistrator.").build();
			} else {
				ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
				dao.editApartment(path, apartment);
				return Response.ok().entity("editAmenitiesInApartment.html?idApartment=" + apartment.getId()).build();
			}
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti domacin ili amdinistrator.").build();
		}
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeApartment(String idString) {
		User host = (User) request.getSession().getAttribute("user");
		if (host!=null) {
			if (host.getRole()==Role.GUEST) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti domacin ili amdinistrator.").build();
			} else {			
				ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
				dao.removeApartment(path, idString);
				if (host.getRole()==Role.HOST) {
					return Response.ok().entity("hostApartments.html").build();
				} else {
					return Response.ok().entity("allApartments.html").build();
				}
			}
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti domacin ili amdinistrator.").build();
		}
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
	
	@POST
	@Path("/saveToApartment")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveToApartment(AmentiesHelp amenitiesHelp){	
		User host = (User) request.getSession().getAttribute("user");
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");	
		if (host!=null) {
			if (host.getRole()==Role.GUEST) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti domacin ili amdinistrator.").build();
			} else {			
				Long idApartment = Long.parseLong(amenitiesHelp.getIdApartment());
				Apartment apartment = dao.findApartment(idApartment);
				dao.saveAmenitiesToApartment(apartment, amenitiesHelp, path);
				if (host.getRole()==Role.HOST) {
					return Response.ok().entity("hostIndex.html").build();
				} else {
					return Response.ok().entity("adminIndex.html").build();
				}
			}
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti domacin ili amdinistrator.").build();
		}
		
	}
	
	@POST
	@Path("/addDatesToApartment")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDatesToApartment(Reservation reservation){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		User host = (User) request.getSession().getAttribute("user");
		if (host!=null) {
			if (host.getRole()!=Role.GUEST) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti gost.").build();
			} else {
				Apartment apartment = dao.findApartment(reservation.getApartment().getId());
				
				List<Date> pickedDates = addDays(reservation.getStartDate(), reservation.getNumberOfOvernights());
				
				int flag = 0;
				
				if (apartment.getReleaseDates()!=null) {
					for (Date oneDate: pickedDates) {
						for (Date releaseDate: apartment.getReleaseDates()) {
							if (oneDate.equals(releaseDate)) {
								flag = 1;
								break;
							}
						}
					}
				}
				
				if (flag == 0) {
				
					dao.addDatesToApartment(path, reservation);
					return Response.ok().entity("allActiveApartments.html").build();
				} else {
					return Response.serverError().entity("Odabrani datumi za rezervaciju su vec zauzeti").build();
				}
			}
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti gost.").build();
		}
	}
	
	private List<Date> addDays(Date startDate, int days) {
		
		List<Date> datesToReturn = new ArrayList<Date>();
		
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = DATE_FORMAT.format(startDate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		
	    try {
	    	
			c.setTime(sdf.parse(dateString));
			
		} catch (java.text.ParseException e) {

			e.printStackTrace();
		}
	    
	    c.add(Calendar.DAY_OF_MONTH, 0);
    	
    	String newDate2 = sdf.format(c.getTime());
    	
    	Date returnDate2 = new Date();
		try {
			returnDate2 = DATE_FORMAT.parse(newDate2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		datesToReturn.add(returnDate2);
	    
		
	    for (int i = 0; i<days-1;i++) {
	    	c.add(Calendar.DAY_OF_MONTH, 1);
	    	
	    	String newDate = sdf.format(c.getTime());
	    	
	    	Date returnDate = new Date();
			try {
				returnDate = DATE_FORMAT.parse(newDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			datesToReturn.add(returnDate);
			
	    }
	    
		
		return datesToReturn;
	}
	
	@POST
	@Path("/getAllDatesForApartment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<String> getAllDatesForApartment(Apartment apartment){
		if (apartment!=null) {
			ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
			return dao.getAllDatesForApartment(apartment);
		} else {
			return new ArrayList<String>();
		}
	}
	
	@POST
	@Path("/whithdrawal")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean whithdrawalReservation(Reservation reservation){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		ReservationDAO reservationDAO = getReservations();
		
		Reservation foundReservation = reservationDAO.find(Long.toString(reservation.getId()));
		
		return dao.whithdrawalReservation(path, foundReservation);
	}
	
	@POST
	@Path("/reject")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean rejectReservation(Reservation reservation){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		ReservationDAO reservationDAO = getReservations();
		
		Reservation foundReservation = reservationDAO.find(Long.toString(reservation.getId()));
		
		return dao.rejectReservation(path, foundReservation);
	}
	
	@POST
	@Path("/deleteAmenitie")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAmenitie(Amenties amenitie){
		
		User host = (User) request.getSession().getAttribute("user");
		if (host!=null) {
			if (host.getRole()!=Role.ADMINISTRATOR) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti amdinistrator.").build();
			} else {
				ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
				
				AmentiesDAO amenitieDAO = getAmenties();
				Amenties foundAmenitie = new Amenties();
				Collection<Amenties> foundAmenities = amenitieDAO.findAll();
				for (Amenties oneAmenitie: foundAmenities) {
					if (oneAmenitie.getId()==amenitie.getId()) {
						foundAmenitie = oneAmenitie;
						break;
					}
				}
				
				dao.deleteAmenitie(path, foundAmenitie);
				return Response.ok().entity("tableAmenities.html").build();
			}
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti amdinistrator.").build();
		}
		
	}
	
	@POST
	@Path("/editAmenitie")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editAmenitie(Amenties amenitie){
		
		User host = (User) request.getSession().getAttribute("user");
		if (host!=null) {
			if (host.getRole()!=Role.ADMINISTRATOR) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Može samo pristupiti amdinistrator.").build();
			} else {
				ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
				
				AmentiesDAO amenitieDAO = getAmenties();
				Amenties oldAmenitie = new Amenties();
				Collection<Amenties> foundAmenities = amenitieDAO.findAll();
				for (Amenties oneAmenitie: foundAmenities) {
					if (oneAmenitie.getId()==amenitie.getId()) {
						oldAmenitie = oneAmenitie;
						break;
					}
				}
				
				dao.editAmenitie(path, oldAmenitie, amenitie);
				return Response.ok().entity("tableAmenities.html").build();
			}
		}else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Može samo pristupiti amdinistrator.").build();
			}
	}
	
	@POST
	@Path("/filterActiveApartmentsType")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Apartment> filterActiveApartmentsType(Apartment apartment){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.filterActiveApartmentsType(apartment);
	}
	
	@POST
	@Path("/filterApartmentsType")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Apartment> filterApartmentsType(Apartment apartment){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.filterApartmentsType(apartment);
	}
	
	@POST
	@Path("/filterApartmentsStatus")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Apartment> filterApartmentsStatus(Apartment apartment){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		return dao.filterApartmentsStatus(apartment);
	}
	
	@POST
	@Path("/filterHostApartmentsStatus")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Apartment> filterHostApartmentsStatus(Apartment apartment){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		User host = (User) request.getSession().getAttribute("user");
		return dao.filterHostApartmentsStatus(apartment, host);
	}
	
	@POST
	@Path("/filterHostApartmentsType")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Apartment> filterHostApartmentsType(Apartment apartment){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		User host = (User) request.getSession().getAttribute("user");
		return dao.filterHostApartmentsType(apartment, host);
	}
	
	
	
}
