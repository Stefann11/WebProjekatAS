package beans;

public class Location{
	/**
	 * 
	 */
	protected double longitude;
	protected double latitude;
	protected Address address;
	
	public Location() {
		super();
		this.longitude = 0;
		this.latitude = 0;
		this.address = new Address();
	}
	
	public Location(double longitude, double latitude, Address address) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
	}
	
	

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Location [longitude=" + longitude + ", latitude=" + latitude + ", address=" + address + "]";
	}
	
	
}
