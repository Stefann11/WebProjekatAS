/*
 * package beans;
 * 
 * import java.io.IOException; import java.util.stream.Stream;
 * 
 * import com.fasterxml.jackson.core.JsonParser; import
 * com.fasterxml.jackson.core.JsonProcessingException; import
 * com.fasterxml.jackson.databind.DeserializationContext; import
 * com.fasterxml.jackson.databind.JsonDeserializer; import
 * com.fasterxml.jackson.databind.JsonNode;
 * 
 * public class MyEnumDeserializer extends JsonDeserializer<Gender> {
 * 
 * @Override public Gender deserialize(JsonParser jsonParser,
 * DeserializationContext deserializationContext) throws IOException,
 * JsonProcessingException { JsonNode node =
 * jsonParser.getCodec().readTree(jsonParser); String gender =
 * node.get("gender").asText(); return Stream.of(Gender.values())
 * .filter(enumValue -> enumValue.getGender().equals(gender)) .findFirst()
 * .orElseThrow(() -> new
 * IllegalArgumentException("gender "+gender+" is not recognized")); } }
 */

