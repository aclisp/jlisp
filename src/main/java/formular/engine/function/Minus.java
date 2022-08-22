package formular.engine.function;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Minus extends Function {

    public Expression invoke(ListExpression args) {
        BigDecimal result = Util.toBigDecimal((Number) args.get(0).getValue());
        for (Expression arg : args.subList(1, args.size())) {
            Number value = (Number) arg.getValue();
            result = result.subtract(Util.toBigDecimal(value));
        }
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
