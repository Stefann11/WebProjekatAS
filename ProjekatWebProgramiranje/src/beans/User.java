package beans;

import java.util.List;

enum Role{
	ADMINISTRATOR, HOST, GUEST
}

enum Gender{
	MALE, FEMALE
}

public class User {
	protected String username;
	protected String password;
	protected String name;
	protected String surname;
	protected Gender gender;
	protected Role role;
	protected List<Apartment> apartmentsForRent;
	protected List<Apartment> rentedApartments;
	protected List<Reservation> listOfReservations;
}
