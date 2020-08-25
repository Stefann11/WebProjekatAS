package beans;

import java.io.Serializable;

public class CommentForApartment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9159234366497138095L;
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
