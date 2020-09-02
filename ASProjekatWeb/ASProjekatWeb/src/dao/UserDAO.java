package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.User;

/***
 * <p>Klasa namenjena da uèita korisnike iz fajla i pruža operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u èistu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
public class UserDAO {
	private Map<String, User> users = new HashMap<>();
	
	
	public UserDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public UserDAO(String contextPath) {
		readUsers(contextPath);
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
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
		return printUsers(contextPath, us);
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
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
