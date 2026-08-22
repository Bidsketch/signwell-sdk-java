package com.signwell.sdk.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** A recursive SignWell validation error value. */
@JsonDeserialize(using = ValidationErrorValue.Deserializer.class)
@JsonSerialize(using = ValidationErrorValue.Serializer.class)
public final class ValidationErrorValue {
    private final Object value;

    public ValidationErrorValue(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    public ValidationErrorValue(List<String> value) {
        this.value = List.copyOf(value);
    }

    public ValidationErrorValue(Map<String, ValidationErrorValue> value) {
        this.value = Map.copyOf(value);
    }

    public Object value() {
        return value;
    }

    public String toUrlQueryString(String prefix) {
        return appendQuery(prefix == null ? "" : prefix, value);
    }

    private static String appendQuery(String prefix, Object value) {
        if (value instanceof Map<?, ?>) {
            StringJoiner query = new StringJoiner("&");
            ((Map<?, ?>) value)
                    .forEach((key, item) -> query.add(appendQuery(prefix + "[" + key + "]", item)));
            return query.toString();
        }
        if (value instanceof List<?>) {
            StringJoiner query = new StringJoiner("&");
            List<?> values = (List<?>) value;
            for (int index = 0; index < values.size(); index++) {
                query.add(appendQuery(prefix + "[" + index + "]", values.get(index)));
            }
            return query.toString();
        }
        return URLEncoder.encode(prefix, StandardCharsets.UTF_8)
                + "="
                + URLEncoder.encode(String.valueOf(value), StandardCharsets.UTF_8);
    }

    static final class Deserializer extends StdDeserializer<ValidationErrorValue> {
        Deserializer() {
            super(ValidationErrorValue.class);
        }

        @Override
        public ValidationErrorValue deserialize(JsonParser parser, DeserializationContext context)
                throws IOException {
            return fromNode(parser.getCodec().readTree(parser), parser);
        }

        private static ValidationErrorValue fromNode(JsonNode node, JsonParser parser)
                throws IOException {
            if (node.isTextual()) return new ValidationErrorValue(node.textValue());
            if (node.isArray()) {
                List<String> values = new ArrayList<>();
                for (JsonNode item : node) {
                    if (!item.isTextual()) return invalid(parser);
                    values.add(item.textValue());
                }
                return new ValidationErrorValue(values);
            }
            if (node.isObject()) {
                Map<String, ValidationErrorValue> values = new LinkedHashMap<>();
                var fields = node.properties().iterator();
                while (fields.hasNext()) {
                    var field = fields.next();
                    values.put(field.getKey(), fromNode(field.getValue(), parser));
                }
                return new ValidationErrorValue(values);
            }
            return invalid(parser);
        }

        private static ValidationErrorValue invalid(JsonParser parser) throws IOException {
            throw JsonMappingException.from(
                    parser, "Validation error values must be strings, string arrays, or objects.");
        }
    }

    static final class Serializer extends StdSerializer<ValidationErrorValue> {
        Serializer() {
            super(ValidationErrorValue.class);
        }

        @Override
        public void serialize(
                ValidationErrorValue value, JsonGenerator generator, SerializerProvider provider)
                throws IOException {
            generator.writeObject(value.value);
        }
    }
}
