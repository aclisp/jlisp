package formular.engine.function.math;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Pow extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        BigDecimal result = Util.toBigDecimal((Number) args.get(0).getValue());
        for (Expression arg : args.subList(1, args.size())) {
            Number value = (Number) arg.getValue();
            result = result.pow(Util.toBigDecimal(value).intValueExact());
        }
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
