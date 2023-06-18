package jlisp.engine.function;

import java.util.Objects;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class Equal extends Function {

    public Expression invoke(ListExpression args) {
        for (int i = 0; i < args.size()-1; i++) {
            Object arg1 = args.get(i).getValue();
            Object arg2 = args.get(i+1).getValue();
            if (!Objects.equals(arg1, arg2)) {
                return Expression.of(false);
            }
        }
        return Expression.of(true);
    }

}
