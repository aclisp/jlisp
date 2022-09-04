package formular.engine.function.math;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Returns the lowest number from a list of numbers.
 */
public class Min extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        BigDecimal result = Util.toBigDecimal(args.get(0).asNumber(0));
        for (Expression arg : args.subList(1, args.size())) {
            Number value = arg.asNumber(0);
            BigDecimal target = Util.toBigDecimal(value);
            if (result.compareTo(target) > 0) {
                result = target;
            }
        }
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
