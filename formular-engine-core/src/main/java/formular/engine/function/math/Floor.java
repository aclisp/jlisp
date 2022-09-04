package formular.engine.function.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Rounds a number down to the nearest integer, away from zero if negative.
 */
public class Floor extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Number value = args.get(0).asNumber(0);
        BigDecimal result = Util.toBigDecimal(value).setScale(0, RoundingMode.FLOOR);
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
