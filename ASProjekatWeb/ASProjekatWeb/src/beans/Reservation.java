package beans;

import java.util.Date;

public class Reservation {
	protected Apartment apartment;
	protected Date startDate;
	protected int numberOfOvernights;
	protected double totalPrice;
	protected String message;
	protected User guest;
	protected Status status;
	
	public Reservation() {
		super();
		this.apartment = new Apartment();
		this.startDate = new Date();
		this.numberOfOvernights = 0;
		this.totalPrice = 0;
		this.message = "message";
		this.guest = new User();
		this.status = Status.REJECTED;
	}
	
	public Reservation(Apartment apartment, Date startDate, int numberOfOvernights, double totalPrice, String message,
			User guest, Status status) {
		super();
		this.apartment = apartment;
		this.startDate = startDate;
		this.numberOfOvernights = numberOfOvernights;
		this.totalPrice = totalPrice;
		this.message = message;
		this.guest = guest;
		this.status = status;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getNumberOfOvernights() {
		return numberOfOvernights;
	}

	public void setNumberOfOvernights(int numberOfOvernights) {
		this.numberOfOvernights = numberOfOvernights;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getGuest() {
		return guest;
	}

	public void setGuest(User guest) {
		this.guest = guest;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
