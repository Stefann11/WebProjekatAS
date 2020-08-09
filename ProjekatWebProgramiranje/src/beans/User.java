package beans;

import java.util.ArrayList;
import java.util.List;

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
	
	public User() {
		super();
		this.username = "username";
		this.password = "password";
		this.name = "name";
		this.surname = "surname";
		this.gender = Gender.MALE;
		this.role = Role.GUEST;
		this.apartmentsForRent = new ArrayList<Apartment>();
		this.rentedApartments = new ArrayList<Apartment>();
		this.listOfReservations = new ArrayList<Reservation>();
	}
	
	public User(String username, String password, String name, String surname, Gender gender, Role role,
			List<Apartment> apartmentsForRent, List<Apartment> rentedApartments, List<Reservation> listOfReservations) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.role = role;
		this.apartmentsForRent = apartmentsForRent;
		this.rentedApartments = rentedApartments;
		this.listOfReservations = listOfReservations;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Apartment> getApartmentsForRent() {
		return apartmentsForRent;
	}

	public void setApartmentsForRent(List<Apartment> apartmentsForRent) {
		this.apartmentsForRent = apartmentsForRent;
	}

	public List<Apartment> getRentedApartments() {
		return rentedApartments;
	}

	public void setRentedApartments(List<Apartment> rentedApartments) {
		this.rentedApartments = rentedApartments;
	}

	public List<Reservation> getListOfReservations() {
		return listOfReservations;
	}

	public void setListOfReservations(List<Reservation> listOfReservations) {
		this.listOfReservations = listOfReservations;
	}
	
	
	
}
