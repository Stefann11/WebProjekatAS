package beans;

public class Address {
	protected String streetAndNumber;
	protected String place;
	protected long postcode;
	
	public Address() {
		super();
		this.streetAndNumber = "street";
		this.place = "place";
		this.postcode = 0;
	}
	
	public Address(String streetAndNumber, String place, long postcode) {
		super();
		this.streetAndNumber = streetAndNumber;
		this.place = place;
		this.postcode = postcode;
	}

	public String getstreetAndNumber() {
		return streetAndNumber;
	}

	public void setstreetAndNumber(String streetAndNumber) {
		this.streetAndNumber = streetAndNumber;
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
