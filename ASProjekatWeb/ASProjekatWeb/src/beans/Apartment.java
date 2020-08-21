package beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	public Apartment() {
		super();
		this.type = TypeOfApartment.ROOM;
		this.numberOfRooms = 0;
		this.numberOfGuests = 0;
		this.location = new Location();
		this.releaseDates = new ArrayList<Date>();
		this.availableDates = new ArrayList<Date>();
		this.host = new User();
		this.comments =new ArrayList<CommentForApartment>();
		this.images = new ArrayList<String>();
		this.priceForOneNight = 0;
		this.checkInTime = LocalDateTime.of(2017, 1, 14, 10, 34);
		this.checkOutTime = LocalDateTime.of(2017, 1, 14, 10, 34);;
		this.status = false;
		this.listOfAmenities = new ArrayList<Amenties>();
		this.reservations = new ArrayList<Reservation>();
	}
	
	public Apartment(TypeOfApartment type, int numberOfRooms, int numberOfGuests, Location location,
			List<Date> releaseDates, List<Date> availableDates, User host, List<CommentForApartment> comments,
			List<String> images, double priceForOneNight, LocalDateTime checkInTime, LocalDateTime checkOutTime,
			boolean status, List<Amenties> listOfAmenities, List<Reservation> reservations) {
		super();
		this.type = type;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.location = location;
		this.releaseDates = releaseDates;
		this.availableDates = availableDates;
		this.host = host;
		this.comments = comments;
		this.images = images;
		this.priceForOneNight = priceForOneNight;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.status = status;
		this.listOfAmenities = listOfAmenities;
		this.reservations = reservations;
	}

	public TypeOfApartment getType() {
		return type;
	}

	public void setType(TypeOfApartment type) {
		this.type = type;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Date> getReleaseDates() {
		return releaseDates;
	}

	public void setReleaseDates(List<Date> releaseDates) {
		this.releaseDates = releaseDates;
	}

	public List<Date> getAvailableDates() {
		return availableDates;
	}

	public void setAvailableDates(List<Date> availableDates) {
		this.availableDates = availableDates;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public List<CommentForApartment> getComments() {
		return comments;
	}

	public void setComments(List<CommentForApartment> comments) {
		this.comments = comments;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public double getPriceForOneNight() {
		return priceForOneNight;
	}

	public void setPriceForOneNight(double priceForOneNight) {
		this.priceForOneNight = priceForOneNight;
	}

	public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalDateTime getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(LocalDateTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Amenties> getListOfAmenities() {
		return listOfAmenities;
	}

	public void setListOfAmenities(List<Amenties> listOfAmenities) {
		this.listOfAmenities = listOfAmenities;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}
