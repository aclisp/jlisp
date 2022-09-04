package formular.engine.function;

import java.math.BigDecimal;
import java.math.RoundingMode;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

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
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
