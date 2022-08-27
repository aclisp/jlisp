package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class JsonStringify extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Object value = args.get(0).getValue();
        String jsonString = jsonWriter.writeValueAsString(value);
        return Util.expressionOf(jsonString);
    }

}
