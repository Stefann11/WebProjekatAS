package beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = TypeOfApartmentDeserializer.class)
public enum TypeOfApartment{
	ROOM("Room"), SUITE("Suite");
	
	private String type;
	
	private TypeOfApartment(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
