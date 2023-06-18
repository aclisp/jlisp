package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

public class Cdr extends Function {

    public Expression invoke(ListExpression args) {
        ListExpression arg = (ListExpression) args.get(0);
        return new ListExpression(arg.subList(1, arg.size()));
    }

}
