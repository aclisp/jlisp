package formular.engine.function.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Returns the nearest number to a number you specify, constraining the new number by a specified number of digits.
 */
public class Round extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Number value = (Number) args.get(0).getValue();
        Number scale = (Number) args.get(1).getValue();
        BigDecimal result = Util.toBigDecimal(value).setScale(scale.intValue(), RoundingMode.HALF_UP);
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
