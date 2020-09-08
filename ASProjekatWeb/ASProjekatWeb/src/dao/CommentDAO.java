package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.CommentForApartment;
import beans.User;

public class CommentDAO {
	private Map<String, CommentForApartment> comments = new HashMap<>();

	public CommentDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public CommentDAO(String contextPath) {
		readComments(contextPath);
	}
	
	
	public CommentForApartment find(String id) {
		if (!comments.containsKey(id)) {
			return null;
		}
		CommentForApartment comm = comments.get(id);
		long idR = Long.parseLong(id);
		if (!(comm.getId() == idR)) {
			return null;
		}
		return comm;
	}
	
	public Collection<CommentForApartment> findAll() {
		return comments.values();
	}
	
	
	public CommentForApartment editComment(String contextPath, CommentForApartment comm, User user) {
		comments.remove(Long.toString(comm.getId()));
		return printComment(contextPath, comm, user);
	}
	
	public CommentForApartment findComment(long id) {	
		return comments.get(Long.toString(id));
	}
	
	public CommentForApartment save(CommentForApartment comm) {
		
		comments.put(Long.toString(comm.getId()), comm);
		return comm;
	}
	
	
	public Collection<CommentForApartment> getCommentsForHost(User host) {
		Collection<CommentForApartment> toReturn = new ArrayList<CommentForApartment>();
		for (CommentForApartment c: comments.values()) {
			if (c.getApartment().getHost()!=null) {
				if (c.getApartment().getHost().getUsername().equals(host.getUsername())) {
					toReturn.add(c);
				}		
			}
		}
		return toReturn;
	}
	
	public CommentForApartment printComment(String contextPath, CommentForApartment comm, User user) {
		ObjectMapper mapper = new ObjectMapper();
		String path = contextPath + "/comments.json";
		comm.setGuest(user);
		comments.put(Long.toString(comm.getId()), comm);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(Paths.get(path).toFile(), comments);
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
		
		return comm;
		
	}
	
	
	public void readComments(String contextPath) {
		ObjectMapper mapper = new ObjectMapper();
		
		BufferedReader in = null;
		
		
		try {
			
			File file = new File(contextPath + "/comments.json");
			in = new BufferedReader(new FileReader(file));
			
			Map<String, CommentForApartment> commentsMap = mapper.readValue(in, new TypeReference<Map<String, CommentForApartment>>() {
            });
			
			comments = commentsMap;
			
			 

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
