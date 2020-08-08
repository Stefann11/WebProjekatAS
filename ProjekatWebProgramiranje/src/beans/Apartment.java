package beans;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

enum TypeOfApartment{
	ROOM, SUITE
}


public class Apartment {
	protected TypeOfApartment type;
	protected int numberOfRooms;
	protected int numberOfGuests;
	protected Location location;
	protected List<Date> releaseDates;
	protected List<Date> availableDates;
	protected User host;
	protected List<CommentForApartment> comments;
	protected List<String> images;
	protected double priceForOneNight;
	protected LocalDateTime checkInTime;
	protected LocalDateTime checkOutTime;
	protected boolean status;
	protected List<Amenties> listOfAmenities;
	protected List<Reservation> reservations;
}
