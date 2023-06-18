package jlisp.engine.function.math;

import java.math.BigDecimal;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class Pow extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        BigDecimal result = Util.toBigDecimal(args.get(0).asNumber(1));
        for (Expression arg : args.subList(1, args.size())) {
            Number value = arg.asNumber(1);
            result = result.pow(Util.toBigDecimal(value).intValueExact());
        }
        return Expression.of(Util.reduceBigDecimal(result));
    }

}
