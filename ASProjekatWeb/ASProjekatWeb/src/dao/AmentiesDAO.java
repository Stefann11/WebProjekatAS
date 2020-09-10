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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Amenties;
import beans.AmentiesHelp;
import beans.Apartment;

public class AmentiesDAO {

	private Map<String, Amenties> amenties = new HashMap<>();

	public AmentiesDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public AmentiesDAO(String contextPath) {
		readAmenties(contextPath);
	}
	
	
	public Amenties find(String id) {
		if (!amenties.containsKey(id)) {
			return null;
		}
		Amenties amen = amenties.get(id);
		long idR = Long.parseLong(id);
		if (!(amen.getId() == idR)) {
			return null;
		}
		return amen;
	}
	
	
	public Collection<Amenties> findAll() {
		return amenties.values();
	}
	
	public Collection<Amenties> saveAmenitiesToApartment(Apartment apartment, AmentiesHelp amenitiesHelp){
		List<Amenties> amenitiesToReturn = new ArrayList<Amenties>();
		
		
		
		return amenitiesToReturn;
	}
	
	
	public Amenties editAmenties(String contextPath, Amenties amen) {
		amenties.remove(Long.toString(amen.getId()));
		return printAmenties(contextPath, amen);
	}
	
	public Amenties findAmenties(long id) {	
		return amenties.get(Long.toString(id));
	}
	
	public Amenties save(Amenties amen) {
		
		amenties.put(Long.toString(amen.getId()), amen);
		return amen;
	}
	
	public Amenties printAmenties(String contextPath, Amenties amen) {
		ObjectMapper mapper = new ObjectMapper();
		String path = contextPath + "/amenties.json";
		amenties.put(Long.toString(amen.getId()), amen);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(Paths.get(path).toFile(), amenties);
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
		
		return amen;
		
	}
	
	
	public void readAmenties(String contextPath) {
		ObjectMapper mapper = new ObjectMapper();
		
		BufferedReader in = null;
		
		
		try {
			
			File file = new File(contextPath + "/amenties.json");
			in = new BufferedReader(new FileReader(file));
			
			Map<String, Amenties> amentiesMap = mapper.readValue(in, new TypeReference<Map<String, Amenties>>() {
            });
			
			amenties = amentiesMap;
			
			 

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
