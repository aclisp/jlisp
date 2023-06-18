package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class Format extends Function {

    public Expression invoke(ListExpression args) throws Exception {
        String fmt = args.get(0).asText("");
        Object[] fmtArgs = new Object[args.size() - 1];
        for (int i = 1; i < args.size(); i++) {
            fmtArgs[i - 1] = args.get(i).getValue();
        }
        return Expression.of(String.format(fmt, fmtArgs));
    }

}
