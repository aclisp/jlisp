package formular.engine.function;

import formular.engine.Array;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Nth extends Function {

    public Expression invoke(ListExpression args) {
        int n = (Integer) args.get(0).getValue();
        Expression collection = args.get(1);
        if (collection instanceof Array) {
            return Util.expressionOf(((Array) collection).get(n));
        } else if (collection instanceof ListExpression) {
            return ((ListExpression) collection).get(n);
        } else {
            return Util.expressionOf(((java.util.List<?>) collection.getValue()).get(n));
        }
    }

}
