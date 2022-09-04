package formular.engine.function;

import java.util.Collection;

import formular.engine.Array;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Length extends Function {

    public Expression invoke(ListExpression args) {
        Expression collection = args.get(0);
        if (collection instanceof Array) {
            return Util.expressionOf(((Array) collection).length());
        } else if (collection instanceof ListExpression) {
            return Util.expressionOf(((ListExpression) collection).size());
        } else if (collection.getValue() == null) {
            return Util.expressionOf(0);
        } else if (collection.getValue() instanceof String) {
            return Util.expressionOf(((String) collection.getValue()).length());
        } else if (collection.getValue() instanceof java.util.Map) {
            return Util.expressionOf(((java.util.Map<?, ?>) collection.getValue()).size());
        } else {
            return Util.expressionOf(((Collection<?>) collection.getValue()).size());
        }
    }

}
