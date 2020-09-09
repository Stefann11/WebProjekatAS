package beans;

public class CommentForApartment{
	/**
	 * 
	 */

	protected long id;
	protected User guest;
	protected Apartment apartment;
	protected String text;
	protected int grade;
	protected boolean approved;
	
	public CommentForApartment() {
		super();
		this.id = 0;
		this.guest = new User();
		this.apartment = new Apartment();
		this.text = "text";
		this.grade = 0;
		this.approved = false;
	}
	
	public CommentForApartment(long id, User guest, Apartment apartment, String text, int grade) {
		super();
		this.id = id;
		this.guest = guest;
		this.apartment = apartment;
		this.text = text;
		this.grade = grade;
		this.approved = false;
	}
	
	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getGuest() {
		return guest;
	}

	public void setGuest(User guest) {
		this.guest = guest;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
