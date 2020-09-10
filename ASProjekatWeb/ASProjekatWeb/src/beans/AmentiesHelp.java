package beans;

public class AmentiesHelp {
	String idApartment;
	String[] amenities;
	
	public AmentiesHelp() {
		super();
		this.idApartment = "";
		this.amenities = new String[] {};
	}
	
	public AmentiesHelp(String idApartment, String[] amenities) {
		super();
		this.idApartment = idApartment;
		this.amenities = amenities;
	}
	
	public String getIdApartment() {
		return idApartment;
	}
	public void setIdApartment(String idApartment) {
		this.idApartment = idApartment;
	}
	public String[] getAmenities() {
		return amenities;
	}
	public void setAmenities(String[] amenities) {
		this.amenities = amenities;
	}
	
	
}
