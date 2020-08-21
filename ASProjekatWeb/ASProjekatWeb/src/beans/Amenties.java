package beans;

public class Amenties {
	protected int id;
	protected String name;
	
	public Amenties() {
		super();
		this.id = 0;
		this.name = "name";
	}
	
	public Amenties(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
