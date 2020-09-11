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
		this.gender = Gender.MALE; this.role = Role.GUEST; this.apartmentsForRent =
		new ArrayList<Apartment>(); this.rentedApartments = new
		ArrayList<Apartment>(); this.listOfReservations = new
		ArrayList<Reservation>();
		 
	}
	
	
	public User(String username, String password, String name, String surname,
			Gender gender, Role role, List<Apartment> apartmentsForRent, List<Apartment>rentedApartments, 
			List<Reservation> listOfReservations) { 
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
	 
	
	public User(String username, String password, String name, String surname) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
	}
	
	public User(String username, String password, String name, String surname, Gender gender) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
	}
	public User(String username, String password, String name, String surname, Gender gender, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.role = role;
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	 

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [firstName=" + name + ", lastName=" + surname +  ", username=" + username
				+ ", password=" + password + "]";
	}

	private static final long serialVersionUID = 6640936480584723344L;

}
