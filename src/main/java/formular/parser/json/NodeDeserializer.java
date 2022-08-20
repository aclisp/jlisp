package formular.parser.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class NodeDeserializer extends StdDeserializer<Node> {

    private final static ObjectMapper mapper = new ObjectMapper();
    private final static ObjectReader nodeReader = mapper.readerFor(Node.class);
    private final static ObjectReader arrayReader = mapper.readerFor(List.class);
    private final static ObjectReader objectReader = mapper.readerFor(Object.class);

    public NodeDeserializer() {
        this(null);
    }

    public NodeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Node deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode json = p.getCodec().readTree(p);
        if (json == null) {
            throw new IllegalArgumentException("Bad node");
        }
        JsonNode jsonType = json.get("type");
        if (jsonType == null) {
            throw new IllegalArgumentException("Missing \"type\" in node");
        }
        String type = jsonType.asText();
        if (Symbol.type.equals(type)) {
            return createSymbol(json);
        } else if (Array.type.equals(type)) {
            return createArray(json);
        } else if (JavaObject.type.equals(type)) {
            return createJavaObject(json);
        } else if (ListExpression.type.equals(type)) {
            return createListExpression(json);
        } else {
            throw new IllegalArgumentException("Unknown \"type\" in node: " + type);
        }
    }

    private static Node createListExpression(JsonNode json) throws IOException {
        JsonNode jsonValue = json.get("value");
        if (jsonValue == null || !jsonValue.isArray()) {
            throw new IllegalArgumentException("Bad list node");
        }
        List<Node> nodes = new ArrayList<>(jsonValue.size());
        for (JsonNode nested : jsonValue) {
            Node node = nodeReader.readValue(nested);
            nodes.add(node);
        }
        ListExpression list = new ListExpression();
        list.setValue(nodes);
        return list;
    }

    private static Node createJavaObject(JsonNode json) throws IOException {
        JsonNode jsonValue = json.get("value");
        Object value = objectReader.readValue(jsonValue);
        JavaObject object = new JavaObject();
        object.setValue(value);
        return object;
    }

    private static Node createArray(JsonNode json) throws IOException {
        JsonNode jsonValue = json.get("value");
        if (jsonValue == null || !jsonValue.isArray()) {
            throw new IllegalArgumentException("Bad array node: " + jsonValue.asText());
        }
        List<Object> items = arrayReader.readValue(jsonValue);
        Array array = new Array();
        array.setValue(items);
        return array;
    }

    private static Node createSymbol(JsonNode json) {
        JsonNode jsonValue = json.get("value");
        if (jsonValue == null || !jsonValue.isTextual()) {
            throw new IllegalArgumentException("Bad symbol node: " + jsonValue.asText());
        }
        Symbol symbol = new Symbol();
        symbol.setValue(jsonValue.asText());
        return symbol;
    }

}
