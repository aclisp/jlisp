package formular.engine.function;

import com.fasterxml.jackson.databind.JsonNode;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class JsonParse extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String jsonString = args.get(0).asText("");
        JsonNode jsonNode = jsonReader.readTree(jsonString);
        return Util.expressionOf(jsonNode);
    }

}
