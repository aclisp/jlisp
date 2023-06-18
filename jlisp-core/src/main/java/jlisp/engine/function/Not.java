package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class Not extends Function {

    public Expression invoke(ListExpression args) throws Exception {
        return Expression.of(!args.get(0).asBoolean());
    }

}
