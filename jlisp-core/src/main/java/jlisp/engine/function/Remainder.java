package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

import java.math.BigDecimal;

public class Remainder extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        if (args.get(0).getValue() == null) {
            throw new IllegalArgumentException("Dividend can not be null");
        }
        BigDecimal result = Util.toBigDecimal((Number) args.get(0).getValue());
        for (Expression arg : args.subList(1, args.size())) {
            Number value = arg.asNumber(1);
            result = result.remainder(Util.toBigDecimal(value));
        }
        return Expression.of(Util.reduceBigDecimal(result));
    }

}
