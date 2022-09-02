package formular.engine.function.math;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Abs extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Number value = (Number) args.get(0).getValue();
        BigDecimal result = Util.toBigDecimal(value).abs();
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
