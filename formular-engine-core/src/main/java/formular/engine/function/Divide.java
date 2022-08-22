package formular.engine.function;

import java.math.BigDecimal;
import java.math.RoundingMode;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Divide extends Function {

    public Expression invoke(ListExpression args) {
        BigDecimal result = Util.toBigDecimal((Number) args.get(0).getValue());
        for (Expression arg : args.subList(1, args.size())) {
            Number value = (Number) arg.getValue();
            result = result.divide(Util.toBigDecimal(value), 16, RoundingMode.UP);
        }
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
