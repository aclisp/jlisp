package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

import java.math.BigDecimal;

public class Add extends Function {

    public Expression invoke(ListExpression args) {
        BigDecimal result = BigDecimal.ZERO;
        for (Expression arg : args) {
            Number value = arg.asNumber(0);
            result = result.add(Util.toBigDecimal(value));
        }
        return Expression.of(Util.reduceBigDecimal(result));
    }

}
