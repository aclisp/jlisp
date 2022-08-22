package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class GreaterThanOrEqualTo extends Function {

    public Expression invoke(ListExpression args) {
        double first = ((Number) args.get(0).getValue()).doubleValue();
        for (Expression arg : args.subList(1, args.size())) {
            if (first < ((Number) arg.getValue()).doubleValue()) {
                return Util.expressionOf(false);
            }
        }
        return Util.expressionOf(true);
    }

}
