package beans;

public class FilterUser {
	private String gender;
	private String role;
	private String username;
	
	public FilterUser() {
		super();
		this.gender = "";
		this.role = "";
		this.username = "";
	}
	
	public FilterUser(String gender, String role, String username) {
		super();
		this.gender = gender;
		this.role = role;
		this.username = username;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "FilterUser [gender=" + gender + ", role=" + role + ", username=" + username + "]";
	}
	
	
	
	

}
