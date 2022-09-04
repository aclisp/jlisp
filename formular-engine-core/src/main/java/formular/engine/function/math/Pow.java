package formular.engine.function.math;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Pow extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        BigDecimal result = Util.toBigDecimal(args.get(0).asNumber(1));
        for (Expression arg : args.subList(1, args.size())) {
            Number value = arg.asNumber(1);
            result = result.pow(Util.toBigDecimal(value).intValueExact());
        }
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
