package formular.engine.function.math;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Returns the highest number from a list of numbers.
 */
public class Max extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        BigDecimal result = Util.toBigDecimal((Number) args.get(0).getValue());
        for (Expression arg : args.subList(1, args.size())) {
            Number value = (Number) arg.getValue();
            BigDecimal target = Util.toBigDecimal(value);
            if (result.compareTo(target) < 0) {
                result = target;
            }
        }
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
