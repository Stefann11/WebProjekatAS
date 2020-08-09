package beans;

public class Address {
	protected String street;
	protected int number;
	protected String place;
	protected long postcode;
	
	public Address() {
		super();
		this.street = "street";
		this.number = 0;
		this.place = "place";
		this.postcode = 0;
	}
	
	public Address(String street, int number, String place, long postcode) {
		super();
		this.street = street;
		this.number = number;
		this.place = place;
		this.postcode = postcode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public long getPostcode() {
		return postcode;
	}

	public void setPostcode(long postcode) {
		this.postcode = postcode;
	}
	
	
}
