package formular.engine.function;

import com.fasterxml.jackson.databind.JsonNode;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class JsonPointer extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        JsonNode jsonNode = args.get(0).asJsonNode();
        String jsonPtrExpr = args.get(1).asText("");
        JsonNode jsonPtr = jsonNode.at(jsonPtrExpr);
        // Unwrap the value of JsonNode if it is possible
        if (jsonPtr.isValueNode()) {
            if (jsonPtr.isBoolean()) {
                return Util.expressionOf(jsonPtr.asBoolean());
            } else if (jsonPtr.isTextual()) {
                return Util.expressionOf(jsonPtr.asText());
            } else if (jsonPtr.isIntegralNumber()) {
                return Util.expressionOf(jsonPtr.asInt());
            } else if (jsonPtr.isFloatingPointNumber()) {
                return Util.expressionOf(jsonPtr.asDouble());
            }
        }
        return Util.expressionOf(jsonPtr);
    }

}
