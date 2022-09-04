package formular.engine.function.math;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Abs extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Number value = args.get(0).asNumber(0);
        BigDecimal result = Util.toBigDecimal(value).abs();
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
