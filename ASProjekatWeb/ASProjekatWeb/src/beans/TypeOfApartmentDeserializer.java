package beans;

import java.io.IOException;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class TypeOfApartmentDeserializer extends JsonDeserializer<TypeOfApartment> {
    @Override
    public TypeOfApartment deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String type = node.get("type").asText();
        return Stream.of(TypeOfApartment.values())
           .filter(enumValue -> enumValue.getType().equals(type))
           .findFirst()
           .orElseThrow(() -> new IllegalArgumentException("type "+type+" is not recognized"));
    }
}