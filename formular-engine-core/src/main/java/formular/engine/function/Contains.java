package formular.engine.function;

import java.util.Collection;
import java.util.Objects;

import formular.engine.Array;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Contains extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Expression collection = args.get(0);
        Expression item = args.get(1);
        if (collection instanceof Array) {
            return arrayContains(collection, item);
        } else if (collection instanceof ListExpression) {
            return listContains(collection, item);
        } else {
            Collection<?> col = (Collection<?>) collection.getValue();
            return Util.expressionOf(col.contains(item.getValue()));
        }
    }

    private Expression listContains(Expression collection, Expression item) {
        ListExpression list = (ListExpression) collection;
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i).getValue();
            if (Objects.equals(o, item.getValue())) {
                return Util.expressionOf(true);
            }
        }
        return Util.expressionOf(false);
    }

    private Expression arrayContains(Expression collection, Expression item) {
        Array array = (Array) collection;
        for (int i = 0; i < array.length(); i++) {
            Object o = array.get(i);
            if (Objects.equals(o, item.getValue())) {
                return Util.expressionOf(true);
            }
        }
        return Util.expressionOf(false);
    }

}
