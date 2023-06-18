package jlisp.engine.function.math;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Returns the nearest number to a number you specify, constraining the new number by a specified number of digits.
 */
public class Round extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Number value = args.get(0).asNumber(0);
        Number scale = args.get(1).asNumber(0);
        BigDecimal result = Util.toBigDecimal(value).setScale(scale.intValue(), RoundingMode.HALF_UP);
        return Expression.of(Util.reduceBigDecimal(result));
    }

}
