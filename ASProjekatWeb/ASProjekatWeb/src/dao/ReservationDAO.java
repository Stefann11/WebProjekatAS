package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Apartment;
import beans.Reservation;
import beans.User;


public class ReservationDAO {

	private Map<String, Reservation> reservations = new HashMap<>();
	
	public ReservationDAO() {
			
	}
	
	public ReservationDAO(String contextPath) {
		readReservations(contextPath);
	}
	
	public Reservation find(String id) {
		if (!reservations.containsKey(id)) {
			return null;
		}
		Reservation reservation = reservations.get(id);
		long idR = Long.parseLong(id);
		if (!(reservation.getId() == idR)) {
			return null;
		}
		return reservation;
	}
	
	public Collection<Reservation> findAll() {
		return reservations.values();
	}
	
	
	
	public Reservation editReservation(String contextPath, Reservation reservation) {
		reservations.remove(Long.toString(reservation.getId()));
		return printReservations(contextPath, reservation);
	}
	
	public Reservation findReservation(long id) {	
		return reservations.get(Long.toString(id));
	}
	
	public Reservation save(Reservation reservation) {
		
		reservations.put(Long.toString(reservation.getId()), reservation);
		return reservation;
	}
	
	public Collection<Reservation> getUserReservations(User user) {
		List<Reservation> reservationsToReturn = new ArrayList<Reservation>();
		for (Reservation reservation: reservations.values()) {
			if (reservation.getGuest()!=null) {
				if (reservation.getGuest().getUsername().equals(user.getUsername())) {
					reservationsToReturn.add(reservation);
				}
			}
		}
		
		return reservationsToReturn;
	}
	
	public Collection<Reservation> getHostReservations(User host) {
		List<Reservation> reservationsToReturn = new ArrayList<Reservation>();
//		if (host.getApartmentsForRent()!=null) {
//			for (Apartment apartment: host.getApartmentsForRent()) {
//				if (apartment.getReservations()!=null) {
//					for (Reservation res: apartment.getReservations()) {
//						reservationsToReturn.add(res);
//					}
//				}
//			}
//		}
		
		for (Reservation reservation: reservations.values()) {
			if (reservation.getApartment()!=null) {
				if (reservation.getApartment().getHost()!=null) {
					if (reservation.getApartment().getHost().getUsername().equals(host.getUsername())) {
						reservationsToReturn.add(reservation);
					}
				}
			}
		}
		
		return reservationsToReturn;
	}
	
	private void loadReservations(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/reservations.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String idRes = st.nextToken().trim();
					String nights = st.nextToken().trim();
					String price = st.nextToken().trim();
					String message = st.nextToken().trim();
					long idR = Long.parseLong(idRes);
					int night = Integer.parseInt(nights);
					double pric = Double.parseDouble(price);
					reservations.put(idRes, new Reservation(idR, night, pric, message));
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
	
	public Reservation printReservations(String contextPath, Reservation reservation) {
		ObjectMapper mapper = new ObjectMapper();
		String path = contextPath + "/reservations.json";
		reservations.put(Long.toString(reservation.getId()), reservation);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(Paths.get(path).toFile(), reservations);
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
		
		return reservation;
		
	}
	
	public void readReservations(String contextPath) {
		ObjectMapper mapper = new ObjectMapper();
		
		BufferedReader in = null;
		
		try {
			
			File file = new File(contextPath + "/reservations.json");
			in = new BufferedReader(new FileReader(file));
			
			Map<String, Reservation> reservationsMap = mapper.readValue(in, new TypeReference<Map<String, Reservation>>() {
            });
			
			reservations = reservationsMap;
			 

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
