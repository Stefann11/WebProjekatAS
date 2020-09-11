package beans;

public class Amenties {
	protected long id;
	protected String name;
	protected boolean deleted;
	
	public Amenties() {
		super();
		this.id = 0;
		this.name = "name";
		this.deleted = false;
	}
	
	public Amenties(long id, String name, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.deleted = deleted;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
