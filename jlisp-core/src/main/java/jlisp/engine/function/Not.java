package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

public class Not extends Function {

    public Expression invoke(ListExpression args) throws Exception {
        return Expression.of(!args.get(0).asBoolean());
    }

}
