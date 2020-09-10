package beans;

public class Amenties {
	protected long id;
	protected String name;
	
	public Amenties() {
		super();
		this.id = 0;
		this.name = "name";
	}
	
	public Amenties(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
