package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

public class List extends Function {

    public Expression invoke(ListExpression args) {
        return args;
    }

}
