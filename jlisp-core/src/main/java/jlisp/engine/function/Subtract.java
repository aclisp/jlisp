package jlisp.engine.function;

import java.math.BigDecimal;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class Subtract extends Function {

    public Expression invoke(ListExpression args) {
        BigDecimal result = Util.toBigDecimal(args.get(0).asNumber(0));
        for (Expression arg : args.subList(1, args.size())) {
            Number value = arg.asNumber(0);
            result = result.subtract(Util.toBigDecimal(value));
        }
        return Expression.of(Util.reduceBigDecimal(result));
    }

}
