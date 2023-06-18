package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

public class GreaterThan extends Function {

    public Expression invoke(ListExpression args) {
        for (int i = 0; i < args.size()-1; i++) {
            double arg1 = args.get(i).asNumber(0).doubleValue();
            double arg2 = args.get(i+1).asNumber(0).doubleValue();
            if (arg1 <= arg2) {
                return Expression.of(false);
            }
        }
        return Expression.of(true);
    }

}
