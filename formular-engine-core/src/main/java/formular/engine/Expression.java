package formular.engine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;

public interface Expression {
    // Every expression has an ID, mainly for debugging purpose.
    int getId();
    Object getValue();
    boolean asBoolean();
    // Get the expression's value with null safety.
    default Number asNumber(Number defaultValue) {
        Object value;
        if ((value = getValue()) == null) {
            return defaultValue;
        }
        return (Number) value;
    }
    default String asText(String defaultValue) {
        Object value;
        if ((value = getValue()) == null) {
            return defaultValue;
        }
        return (String) value;
    }
    default JsonNode asJsonNode() {
        Object value;
        if ((value = getValue()) == null) {
            return NullNode.getInstance();
        }
        return (JsonNode) value;
    }
}
