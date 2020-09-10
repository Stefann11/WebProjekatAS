package beans;

import java.util.Date;

public class Reservation{
	/**
	 * 
	 */
	protected long id;
	protected Apartment apartment;
	protected Date startDate;
	protected int numberOfOvernights;
	protected double totalPrice;
	protected String message;
	protected User guest;
	protected Status status;
	
	public Reservation() {
		super();
		this.id = 0;
		this.apartment = new Apartment();
		this.startDate = new Date();
		this.numberOfOvernights = 0;
		this.totalPrice = 0;
		this.message = "message";
		this.guest = new User();
		this.status = Status.CREATED;
	}
	
	public Reservation(long id, Apartment apartment, Date startDate, int numberOfOvernights, double totalPrice, String message,
			User guest, Status status) {
		super();
		this.id = id;
		this.apartment = apartment;
		this.startDate = startDate;
		this.numberOfOvernights = numberOfOvernights;
		this.totalPrice = totalPrice;
		this.message = message;
		this.guest = guest;
		this.status = status;
	}
	
	public Reservation(long id, int numberOfOvernights, double totalPrice, String message) {
		super();
		this.id = id;		
		this.numberOfOvernights = numberOfOvernights;
		this.totalPrice = totalPrice;
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", apartment=" + apartment + ", startDate=" + startDate
				+ ", numberOfOvernights=" + numberOfOvernights + ", totalPrice=" + totalPrice + ", message=" + message
				+ ", guest=" + guest + ", status=" + status + "]";
	}

}
