package beans;

public class newCommentHelp {
	protected long id;
	protected String apartment;
	protected String text;
	protected int grade;
	public long getId() {
		return id;
	}
	public newCommentHelp() {
		super();
		this.id = 0;
		this.apartment = "0";
		this.text = "text";
		this.grade = 0;
	}
	public newCommentHelp(long id, String apartment, String text, int grade) {
		super();
		this.id = id;
		this.apartment = apartment;
		this.text = text;
		this.grade = grade;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getApartment() {
		return apartment;
	}
	public void setApartment(String apartment) {
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
