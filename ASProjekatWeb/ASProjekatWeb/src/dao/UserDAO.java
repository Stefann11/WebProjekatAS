package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Apartment;
import beans.FilterUser;
import beans.Gender;
import beans.Reservation;
import beans.Role;
import beans.User;

/***
 * <p>Klasa namenjena da u�ita korisnike iz fajla i pru�a operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u �istu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
public class UserDAO {
	private Map<String, User> users = new HashMap<>();
	
	
	public UserDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	public UserDAO(String contextPath) {
		readUsers(contextPath);
	}
	
	/**
	 * Vra�a korisnika za prosle�eno korisni�ko ime i �ifru. Vra�a null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	public User find(String username, String password) {
		if (!users.containsKey(username)) {
			return null;
		}
		User user = users.get(username);
		if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}
	
	public Collection<User> findAll() {
		return users.values();
	}
	
	public User save(User user) {	
		users.put(user.getUsername(), user);
		return user;
	}
	
	public User findUser(String username) {	
		return users.get(username);
	}
	
	public User edit(String contextPath, User user) {
		User us = users.get(user.getUsername());
		
		us.setName(user.getName());
		us.setPassword(user.getPassword());
		
		us.setSurname(user.getSurname());
		
		us.setGender(user.getGender());
		users.remove(user.getUsername());
		return printUsers(contextPath, us);
	}
	
	public Collection<User> searchUsers(FilterUser filterUser){
		List<User> usersToReturn = new ArrayList<User>();
		int flag=0;
		int max = 3;
		
		for (User user: users.values()) {
			flag = 0;
			max = 3;
			if (filterUser.getRole().equals("")) {
				max--;
			} else {
				if (filterUser.getRole().equals("GUEST")) {
					if (user.getRole().equals(Role.GUEST)) {
						flag++;
					}
				} else if (filterUser.getRole().equals("HOST")) {
					if (user.getRole().equals(Role.HOST)) {
						flag++;
					}
				} else if (filterUser.getRole().equals("ADMINISTRATOR")) {
					if (user.getRole().equals(Role.ADMINISTRATOR)) {
						flag++;
					}
				}
			}
			
			if (filterUser.getGender().equals("")) {
				max--;
			} else {
				if (filterUser.getGender().equals("MALE")) {
					if (user.getGender().equals(Gender.MALE)) {
						flag++;
					}
				} else if (filterUser.getGender().equals("FEMALE")) {
					if (user.getGender().equals(Gender.FEMALE)) {
						flag++;
					}
				}
			}
			
			if (filterUser.getUsername().equals("")) {
				max--;
			} else {
				if (filterUser.getUsername().equals(user.getUsername())) {
					flag++;
				}
			}
			
			if (flag==max) {
				usersToReturn.add(user);
			}
			
		}
		
		return usersToReturn;
	}
	
	public void editApartmentInUser(String contextPath, Apartment apartment) {
		List<String> usernamesToDelete = new ArrayList<String>();
		List<User> usersToAdd = new ArrayList<User>();
		int flag = 0;
		
		for (User user: users.values()) {
			if (user.getApartmentsForRent()!=null) {
				for (Apartment rentApartment: user.getApartmentsForRent()) {
					if (rentApartment.getId()==apartment.getId()) {
						flag = 1;
						usernamesToDelete.add(user.getUsername());
						rentApartment.setHost(user);
					}
				}
			}
				
				if (user.getRentedApartments()!=null) {
					for (Apartment rentApartment: user.getRentedApartments()) {
						if (rentApartment.getId()==apartment.getId()) {
							rentApartment.setHost(user);
							if (flag == 0) {
								usernamesToDelete.add(user.getUsername());
								flag = 1;
							}
						}
					}
				}
				
				if (flag == 1) {
					usersToAdd.add(user);
				}
		}
		
		for (String username: usernamesToDelete) {
			users.remove(username);
		}
		
		for (User user: usersToAdd) {
			printUsers(contextPath, user);
		}
		
	}
	
	public Collection<User> listUsersForHost(User host){
		List<User> usersToReturn = new ArrayList<User>();
		for (User user : users.values()) {
			if (host.getApartmentsForRent()!=null) {
				for(Apartment apartment : host.getApartmentsForRent()) {
					if (apartment.getReservations()!=null) {
						for (Reservation reservation : apartment.getReservations()) {
							if (reservation.getGuest()!=null) {
								if (reservation.getGuest().getUsername().equals(user.getUsername())) {
									usersToReturn.add(user);
								}
							}
						}
					}
				}
			}
		}
		
		return usersToReturn;
	}
	
	public Collection<User> searchUsersForHost(User host, FilterUser filterUser){
		List<User> usersToReturn = new ArrayList<User>();
		int flag = 0;
		int max = 3;
		for (User user : users.values()) {
			flag = 0;
			max = 3;
			if (host.getApartmentsForRent()!=null) {
				for(Apartment apartment : host.getApartmentsForRent()) {
					if (apartment.getReservations()!=null) {
						for (Reservation reservation : apartment.getReservations()) {
							if (reservation.getGuest()!=null) {
								if (reservation.getGuest().getUsername().equals(user.getUsername())) {
									if (filterUser.getRole().equals("")) {
										max--;
									} else {
										if (filterUser.getRole().equals("GUEST")) {
											if (user.getRole().equals(Role.GUEST)) {
												flag++;
											}
										} else if (filterUser.getRole().equals("HOST")) {
											if (user.getRole().equals(Role.HOST)) {
												flag++;
											}
										} else if (filterUser.getRole().equals("ADMINISTRATOR")) {
											if (user.getRole().equals(Role.ADMINISTRATOR)) {
												flag++;
											}
										}
									}
									
									if (filterUser.getGender().equals("")) {
										max--;
									} else {
										if (filterUser.getGender().equals("MALE")) {
											if (user.getGender().equals(Gender.MALE)) {
												flag++;
											}
										} else if (filterUser.getGender().equals("FEMALE")) {
											if (user.getGender().equals(Gender.FEMALE)) {
												flag++;
											}
										}
									}
									
									if (filterUser.getUsername().equals("")) {
										max--;
									} else {
										if (filterUser.getUsername().equals(user.getUsername())) {
											flag++;
										}
									}
									
									if (flag==max) {
										usersToReturn.add(user);
									}
									
								}
							}
						}
					}
				}
			}
		}
		
		return usersToReturn;
	}
	
	public Apartment addApartmentToHost(Apartment apartment, User host, String contextPath) {
		host.getApartmentsForRent().add(apartment);
		
		users.remove(host.getUsername());
		
		printUsers(contextPath, host);
		
		return apartment;
	}
	
	/**
	 * U�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Klju� je korisni�ko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadUsers(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/users.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String firstName = st.nextToken().trim();
					String lastName = st.nextToken().trim();
					String username = st.nextToken().trim();
					String password = st.nextToken().trim();
					users.put(username, new User(username, password, firstName, lastName));
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
	
	public void printOneUser(String contextPath, User user) {
		
		ObjectMapper obj = new ObjectMapper();
		
		try {		
			File file = new File(contextPath + "/writeusers.json");
			FileWriter out = new FileWriter(file);
			System.out.println(file.getPath());
			String jsonStr = obj.writeValueAsString(user);
			System.out.println(jsonStr); 	
			for(User oneUser : users.values()) {
				System.out.println(oneUser);
			}
			out.write(jsonStr);
			out.close();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public User printUsers(String contextPath, User user) {
		ObjectMapper mapper = new ObjectMapper();
		String path = contextPath + "/writeusers.json";
		users.put(user.getUsername(), user);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(Paths.get(path).toFile(), users);
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
		
		return user;
	}
	
	public void readUsers(String contextPath) {
		ObjectMapper mapper = new ObjectMapper();
		
		BufferedReader in = null;
		
		
		try {
			
			File file = new File(contextPath + "/writeusers.json");
			in = new BufferedReader(new FileReader(file));
			
			Map<String, User> usersMap = mapper.readValue(in, new TypeReference<Map<String, User>>() {
            });
			
			users = usersMap;
			
			 

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
