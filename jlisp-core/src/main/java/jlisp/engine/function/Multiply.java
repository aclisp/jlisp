package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

import java.math.BigDecimal;

public class Multiply extends Function {

    public Expression invoke(ListExpression args) {
        BigDecimal result = BigDecimal.ONE;
        for (Expression arg : args) {
            Number value = arg.asNumber(1);
            result = result.multiply(Util.toBigDecimal(value));
        }
        return Expression.of(Util.reduceBigDecimal(result));
    }

}
