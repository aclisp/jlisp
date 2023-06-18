package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

public class Car extends Function {

    public Expression invoke(ListExpression args) {
        ListExpression arg = (ListExpression) args.get(0);
        return arg.get(0);
    }

}
