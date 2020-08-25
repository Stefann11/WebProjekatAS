package beans;

import java.io.IOException;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class RoleDeserializer extends JsonDeserializer<Role> {
    @Override
    public Role deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String role = node.get("role").asText();
        return Stream.of(Role.values())
           .filter(enumValue -> enumValue.getRole().equals(role))
           .findFirst()
           .orElseThrow(() -> new IllegalArgumentException("gender "+role+" is not recognized"));
    }
}
