package formular.engine.function;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Multiply extends Function {

    public Expression invoke(ListExpression args) {
        BigDecimal result = BigDecimal.ONE;
        for (Expression arg : args) {
            Number value = (Number) arg.getValue();
            result = result.multiply(Util.toBigDecimal(value));
        }
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
