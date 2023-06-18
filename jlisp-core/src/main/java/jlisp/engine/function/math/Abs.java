package jlisp.engine.function.math;

import java.math.BigDecimal;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class Abs extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Number value = args.get(0).asNumber(0);
        BigDecimal result = Util.toBigDecimal(value).abs();
        return Expression.of(Util.reduceBigDecimal(result));
    }

}
