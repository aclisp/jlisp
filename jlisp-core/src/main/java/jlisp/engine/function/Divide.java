package jlisp.engine.function;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class Divide extends Function {

    public Expression invoke(ListExpression args) {
        if (args.get(0).getValue() == null) {
            throw new IllegalArgumentException("Dividend can not be null");
        }
        BigDecimal result = Util.toBigDecimal((Number) args.get(0).getValue());
        for (Expression arg : args.subList(1, args.size())) {
            Number value = arg.asNumber(1);
            result = result.divide(Util.toBigDecimal(value), 16, RoundingMode.UP);
        }
        return Expression.of(Util.reduceBigDecimal(result));
    }

}
