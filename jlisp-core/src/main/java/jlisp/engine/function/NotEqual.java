package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

import java.util.Objects;

public class NotEqual extends Function {

    public Expression invoke(ListExpression args) {
        for (int i = 0; i < args.size()-1; i++) {
            for (int j = i+1; j < args.size(); j++) {
                Object arg1 = args.get(i).getValue();
                Object arg2 = args.get(j).getValue();
                if (Objects.equals(arg1, arg2)) {
                    return Expression.of(false);
                }
            }
        }
        return Expression.of(true);
    }

}
