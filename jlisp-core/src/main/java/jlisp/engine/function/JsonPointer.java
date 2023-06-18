package jlisp.engine.function;

import com.fasterxml.jackson.databind.JsonNode;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class JsonPointer extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        JsonNode jsonNode = args.get(0).asJsonNode();
        String jsonPtrExpr = args.get(1).asText("");
        JsonNode jsonPtr = jsonNode.at(jsonPtrExpr);
        // Unwrap the value of JsonNode if it is possible
        if (jsonPtr.isValueNode()) {
            if (jsonPtr.isBoolean()) {
                return Expression.of(jsonPtr.asBoolean());
            } else if (jsonPtr.isTextual()) {
                return Expression.of(jsonPtr.asText());
            } else if (jsonPtr.isIntegralNumber()) {
                return Expression.of(jsonPtr.asInt());
            } else if (jsonPtr.isFloatingPointNumber()) {
                return Expression.of(jsonPtr.asDouble());
            }
        }
        return Expression.of(jsonPtr);
    }

}
