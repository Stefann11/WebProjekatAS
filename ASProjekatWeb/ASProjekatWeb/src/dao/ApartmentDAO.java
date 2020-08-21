package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.Address;
import beans.Apartment;
import beans.Location;
import beans.TypeOfApartment;
import beans.User;

public class ApartmentDAO {
private Map<String, Apartment> apartments = new HashMap<>();
	
	
	public ApartmentDAO() {
		
	}
	
	public ApartmentDAO(String contextPath) {
		loadApartments(contextPath);
	}
	
	public Collection<Apartment> findAll() {
		return apartments.values();
	}
	
	public Apartment save(Apartment apartment) {
		apartments.put(Long.toString(apartment.getId()), apartment);
		return apartment;
	}
	
	public Apartment findApartment(long id) {	
		return apartments.get(Long.toString(id));
	}
	
	private void loadApartments(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/apartments.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String idString = st.nextToken().trim();
					String type = st.nextToken().trim();
					String numberOfRoomsString = st.nextToken().trim();
					String numberOfGuestsString = st.nextToken().trim();
					//String longitudeString = st.nextToken().trim();
					//String latitudeString = st.nextToken().trim();
					//String streetAndNumber = st.nextToken().trim();
					//String place = st.nextToken().trim();
					//String postcodeString = st.nextToken().trim();
					String priceForOneNightString = st.nextToken().trim();
					String statusString = st.nextToken().trim();
					
					long id = Long.parseLong(idString);
					int numberOfRooms = Integer.parseInt(numberOfRoomsString);
					int numberOfGuests = Integer.parseInt(numberOfGuestsString);
					//double longitude = Double.parseDouble(longitudeString);
					//double latitude = Double.parseDouble(latitudeString);
					//long postcode = Long.parseLong(postcodeString);
					double priceForOneNight = Double.parseDouble(priceForOneNightString);
					boolean status = Boolean.parseBoolean(statusString);
					
					//Address address = new Address(streetAndNumber, place, postcode);
					
					//Location location = new Location(longitude, latitude, address);
					
					apartments.put(idString, new Apartment(id, TypeOfApartment.ROOM, numberOfRooms, numberOfGuests, priceForOneNight, status));
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
}
