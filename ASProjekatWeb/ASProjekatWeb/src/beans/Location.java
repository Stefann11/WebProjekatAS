package beans;

import java.io.Serializable;

public class Location implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2120182138853617449L;
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

	@Override
	public String toString() {
		return "Location [longitude=" + longitude + ", latitude=" + latitude + ", address=" + address + "]";
	}
	
	
}
