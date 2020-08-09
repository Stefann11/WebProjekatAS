package beans;

public class Location {
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
	
	
}
