package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
		readApartments(contextPath);
	}
	
	public Collection<Apartment> findAll() {
		return apartments.values();
	}
	
	public Collection<Apartment> getAllActive() {
		Collection<Apartment> toReturn = new ArrayList<Apartment>();
		
		for (Apartment apartment: apartments.values()) {
			if (apartment.isStatus()) {
				toReturn.add(apartment);
			}
		}
		
		return toReturn;
	}
	
	public Collection<Apartment> getHostActive(User host) {
		Collection<Apartment> toReturn = new ArrayList<Apartment>();
		for (Apartment apartment: apartments.values()) {
			if (apartment.getHost()!=null) {
				if (apartment.getHost().getUsername().equals(host.getUsername())) {
					toReturn.add(apartment);
				}		
			}
		}
		return toReturn;
		
		//SAMO ZA AKTIVNE
//		for (Apartment apartment: apartments.values()) {
//			if (apartment.getHost()!=null) {
//				if (apartment.isStatus() && apartment.getHost().getUsername().equals(host.getUsername())) {
//					toReturn.add(apartment);
//				}		
//			}
//		}
//		return toReturn;
	}
	
	public Apartment save(Apartment apartment) {
		apartments.put(Long.toString(apartment.getId()), apartment);
		return apartment;
	}
	
	public Apartment editApartment(String contextPath, Apartment apartment) {
		apartments.remove(Long.toString(apartment.getId()));
		return printApartments(contextPath, apartment);
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
	
	public Apartment printApartments(String contextPath, Apartment apartment) {
		ObjectMapper mapper = new ObjectMapper();
		String path = contextPath + "/apartments.json";
		apartments.put(Long.toString(apartment.getId()), apartment);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(Paths.get(path).toFile(), apartments);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return apartment;
		
	}
	
	public void readApartments(String contextPath) {
		ObjectMapper mapper = new ObjectMapper();
		
		//za datum
//		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
//		mapper.setDateFormat(df);
		
		BufferedReader in = null;
		
		try {
			
			File file = new File(contextPath + "/apartments.json");
			in = new BufferedReader(new FileReader(file));
			
			Map<String, Apartment> apartmentsMap = mapper.readValue(in, new TypeReference<Map<String, Apartment>>() {
            });
			
			apartments = apartmentsMap;
			 

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
