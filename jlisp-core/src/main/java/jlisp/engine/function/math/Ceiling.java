package jlisp.engine.function.math;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Rounds a number up to the nearest integer, towards zero if negative.
 */
public class Ceiling extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Number value = args.get(0).asNumber(0);
        BigDecimal result = Util.toBigDecimal(value).setScale(0, RoundingMode.CEILING);
        return Expression.of(Util.reduceBigDecimal(result));
    }

}
