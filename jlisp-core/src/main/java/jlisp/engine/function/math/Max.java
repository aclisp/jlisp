package jlisp.engine.function.math;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

import java.math.BigDecimal;

/**
 * Returns the highest number from a list of numbers.
 */
public class Max extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        BigDecimal result = Util.toBigDecimal(args.get(0).asNumber(0));
        for (Expression arg : args.subList(1, args.size())) {
            Number value = arg.asNumber(0);
            BigDecimal target = Util.toBigDecimal(value);
            if (result.compareTo(target) < 0) {
                result = target;
            }
        }
        return Expression.of(Util.reduceBigDecimal(result));
    }

}
