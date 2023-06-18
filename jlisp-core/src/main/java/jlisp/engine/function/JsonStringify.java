package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class JsonStringify extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Object value = args.get(0).getValue();
        String jsonString = jsonWriter.writeValueAsString(value);
        return Expression.of(jsonString);
    }

}
