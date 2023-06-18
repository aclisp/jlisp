package jlisp.engine.function;

import com.fasterxml.jackson.databind.JsonNode;
import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

public class JsonParse extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String jsonString = args.get(0).asText("");
        JsonNode jsonNode = jsonReader.readTree(jsonString);
        return Expression.of(jsonNode);
    }

}
