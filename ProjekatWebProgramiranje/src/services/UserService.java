package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.User;

@Path("/users")
public class UserService {
	
	@GET
	@Path("/getalljson")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		List<User>users = new ArrayList<User>();
		return users;
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_PLAIN)
	public void RegisterUser(User user) {
		System.out.println("saving user: " + user.getName());
	}
}
