package formular.engine.function;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Add extends Function {

    public Expression invoke(ListExpression args) {
        BigDecimal result = BigDecimal.ZERO;
        for (Expression arg : args) {
            Number value = arg.asNumber(0);
            result = result.add(Util.toBigDecimal(value));
        }
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
