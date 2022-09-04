package formular.engine.function;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

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
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
