package beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = RoleDeserializer.class)
public enum Role implements Serializable{
	ADMINISTRATOR("Administrator"), HOST("Host"), GUEST("Guest");
	
	private String role;
	
	private Role(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	
}
