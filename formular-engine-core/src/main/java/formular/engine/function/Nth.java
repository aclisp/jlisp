package formular.engine.function;

import formular.engine.Array;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Nth extends Function {

    public Expression invoke(ListExpression args) {
        int n = args.get(0).asNumber(0).intValue();
        Expression collection = args.get(1);
        if (collection instanceof Array) {
            return Util.expressionOf(((Array) collection).get(n));
        } else if (collection instanceof ListExpression) {
            return ((ListExpression) collection).get(n);
        } else if (collection.getValue() == null) {
            return Util.expressionOf(null);
        } else if (collection.getValue() instanceof java.util.List) {
            return Util.expressionOf(((java.util.List<?>) collection.getValue()).get(n));
        } else {
            throw new IllegalArgumentException("Unsupported collection: " + collection);
        }
    }

}
