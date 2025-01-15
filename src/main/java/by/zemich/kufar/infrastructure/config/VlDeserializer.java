package by.zemich.kufar.infrastructure.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class VlDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String result = "";

        if (node.isTextual()) {
            result = (node.asText());
        } else if (node.isArray()) {
            StringBuilder builder = new StringBuilder();
            node.forEach(item -> builder.append(", ").append(item.asText()));
            result = builder.toString();
        }
        return result;
    }
}
