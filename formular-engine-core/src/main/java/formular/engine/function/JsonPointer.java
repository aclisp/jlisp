package formular.engine.function;

import com.fasterxml.jackson.databind.JsonNode;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class JsonPointer extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        JsonNode jsonNode = (JsonNode) args.get(0).getValue();
        String jsonPtrExpr = (String) args.get(1).getValue();
        JsonNode jsonPtr = jsonNode.at(jsonPtrExpr);
        return Util.expressionOf(jsonPtr);
    }

}
