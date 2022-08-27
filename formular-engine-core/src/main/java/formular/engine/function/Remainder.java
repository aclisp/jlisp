package formular.engine.function;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Remainder extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        BigDecimal result = Util.toBigDecimal((Number) args.get(0).getValue());
        BigDecimal divisor = Util.toBigDecimal((Number) args.get(1).getValue());
        result = result.remainder(divisor);
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
