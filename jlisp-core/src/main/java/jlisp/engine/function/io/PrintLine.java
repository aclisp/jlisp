package jlisp.engine.function.io;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

public class PrintLine extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        StringBuilder builder = new StringBuilder();
        for (Expression arg : args) {
            Object value = arg.getValue();
            builder.append(value);
        }
        System.out.println(builder);
        return Expression.of(null);
    }
}
