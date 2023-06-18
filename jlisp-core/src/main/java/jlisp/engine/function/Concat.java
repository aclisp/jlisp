package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class Concat extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        StringBuilder builder = new StringBuilder();
        for (Expression arg : args) {
            Object value = arg.getValue();
            builder.append(value);
        }
        return Expression.of(builder.toString());
    }

}
