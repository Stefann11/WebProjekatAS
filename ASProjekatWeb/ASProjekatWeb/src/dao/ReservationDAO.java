package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.Reservation;


public class ReservationDAO {

	private Map<String, Reservation> reservations = new HashMap<>();
	
	public ReservationDAO() {
			
	}
	
	public ReservationDAO(String contextPath) {
		loadReservations(contextPath);
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
	
	public Reservation save(Reservation reservation) {
		
		reservations.put(Long.toString(reservation.getId()), reservation);
		return reservation;
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
}
