package beans;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateHandler extends StdDeserializer<Date>{

		/**
	 * 
	 */
	private static final long serialVersionUID = 8974144973495178650L;

		public DateHandler() {
			this(null);
		}
		
		public DateHandler(Class<?> clazz) {
			super(clazz);
		}

		@Override
		public Date deserialize(JsonParser jsonParser, DeserializationContext context)
				throws IOException, JsonProcessingException {
			String date = jsonParser.getText();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				return sdf.parse(date);
			} catch (Exception e) {
				return null;
			}
		}
}
