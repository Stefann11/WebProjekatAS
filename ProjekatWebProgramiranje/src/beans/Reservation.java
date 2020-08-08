package beans;

import java.util.Date;

enum Status{
	CREATED, REJECTED, WHITHDRAWAL, ACCEPTED, COMPLETED
}

public class Reservation {
	protected Apartment apartment;
	protected Date startDate;
	protected int numberOfOvernights;
	protected double totalPrice;
	protected String message;
	protected User guest;
	protected Status status;
}
