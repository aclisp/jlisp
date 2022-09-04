package formular.engine.function;

import java.math.BigDecimal;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Subtract extends Function {

    public Expression invoke(ListExpression args) {
        BigDecimal result = Util.toBigDecimal(args.get(0).asNumber(0));
        for (Expression arg : args.subList(1, args.size())) {
            Number value = arg.asNumber(0);
            result = result.subtract(Util.toBigDecimal(value));
        }
        return Util.expressionOf(Util.reduceBigDecimal(result));
    }

}
