package beans;

public class CommentForApartment {
	protected User guest;
	protected Apartment apartment;
	protected String text;
	protected int grade;
	
	public CommentForApartment() {
		super();
		this.guest = new User();
		this.apartment = new Apartment();
		this.text = "text";
		this.grade = 0;
	}
	
	public CommentForApartment(User guest, Apartment apartment, String text, int grade) {
		super();
		this.guest = guest;
		this.apartment = apartment;
		this.text = text;
		this.grade = grade;
	}
	
	
}
