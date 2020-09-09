package beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = MyEnumDeserializer.class)
public enum Gender{
	MALE("Male"), FEMALE("Female");
	
	private String gender;
	
	private Gender() {
		
	}
	
	private Gender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}



