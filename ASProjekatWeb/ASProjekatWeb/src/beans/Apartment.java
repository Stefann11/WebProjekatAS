package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Apartment{
	/**
	 * 
	 */

	protected long id;
	protected TypeOfApartment type;
	protected int numberOfRooms;
	protected int numberOfGuests;
	protected Location location;
	@JsonDeserialize(contentUsing = DateHandler.class)
	protected List<Date> releaseDates;
	@JsonDeserialize(contentUsing = DateHandler.class)
	protected List<Date> availableDates;
	protected User host;
	protected List<CommentForApartment> comments;
	protected List<String> images;
	protected double priceForOneNight;
	protected int checkInTime;
	protected int checkOutTime;
	protected boolean status;
	protected List<Amenties> listOfAmenities;
	protected List<Reservation> reservations;
	
	public Apartment() {
		super();
		this.id = 0;
		this.type = TypeOfApartment.ROOM;
		this.numberOfRooms = 0;
		this.numberOfGuests = 0;
		this.location = new Location();
		this.releaseDates = new ArrayList<Date>();
		this.availableDates = new ArrayList<Date>();
		this.host = new User();
		this.comments = new ArrayList<CommentForApartment>();
		this.images = new ArrayList<String>();
		this.priceForOneNight = 0;
		this.checkInTime = 14;
		this.checkOutTime = 10;
		this.status = false;
		this.listOfAmenities = new ArrayList<Amenties>();
		this.reservations = new ArrayList<Reservation>();
	}
	
	public Apartment(Long id, TypeOfApartment type, int numberOfRooms, int numberOfGuests, Location location,
			List<Date> releaseDates, List<Date> availableDates, User host, List<CommentForApartment> comments,
			List<String> images, double priceForOneNight, int checkInTime, int checkOutTime,
			boolean status, List<Amenties> listOfAmenities, List<Reservation> reservations) {
		super();
		this.id = id;
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
	
	public Apartment(Long id, TypeOfApartment type, int numberOfRooms, int numberOfGuests,
			double priceForOneNight, boolean status) {
		super();
		this.id = id;
		this.type = type;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		//this.location = location;
		this.priceForOneNight = priceForOneNight;
		this.status = status;
	}
	
	public Apartment(Long id, int numberOfRooms, int numberOfGuests,
			double priceForOneNight, boolean status) {
		super();
		this.id = id;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.priceForOneNight = priceForOneNight;
		this.status = status;
	}
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	@JsonDeserialize(contentUsing = DateHandler.class)
	public List<Date> getReleaseDates() {
		return releaseDates;
	}

	public void setReleaseDates(List<Date> releaseDates) {
		this.releaseDates = releaseDates;
	}

	@JsonDeserialize(contentUsing = DateHandler.class)
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

	public int getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(int checkInTime) {
		this.checkInTime = checkInTime;
	}

	public int getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(int checkOutTime) {
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

	@Override
	public String toString() {
		return "Apartment [id=" + id + ", type=" + type + ", numberOfRooms=" + numberOfRooms + ", numberOfGuests="
				+ numberOfGuests + ", location=" + location + ", releaseDates=" + releaseDates + ", availableDates="
				+ availableDates + ", host=" + host + ", comments=" + comments + ", images=" + images
				+ ", priceForOneNight=" + priceForOneNight + ", checkInTime=" + checkInTime + ", checkOutTime="
				+ checkOutTime + ", status=" + status + ", listOfAmenities=" + listOfAmenities + ", reservations="
				+ reservations + "]";
	}
	
	
	
}
