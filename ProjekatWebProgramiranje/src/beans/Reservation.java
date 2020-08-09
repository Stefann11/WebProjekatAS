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
	
	
}
