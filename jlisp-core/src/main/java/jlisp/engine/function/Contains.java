package jlisp.engine.function;

import java.util.Collection;
import java.util.Objects;

import jlisp.engine.Array;
import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class Contains extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Expression collection = args.get(0);
        Expression item = args.get(1);
        if (collection instanceof Array) {
            return arrayContains(collection, item);
        } else if (collection instanceof ListExpression) {
            return listContains(collection, item);
        } else if (collection.getValue() == null) {
            return Expression.of(false);
        } else if (collection.getValue() instanceof String) {
            return stringContains(collection, item);
        } else if (collection.getValue() instanceof java.util.Map) {
            return mapContains(collection, item);
        } else if (collection.getValue() instanceof java.util.Collection) {
            return collectionContains(collection, item);
        } else {
            throw new IllegalArgumentException("Unsupported collection: " + collection);
        }
    }

    private Expression collectionContains(Expression collection, Expression item) {
        Collection<?> col = (Collection<?>) collection.getValue();
        return Expression.of(col.contains(item.getValue()));
    }

    private Expression mapContains(Expression collection, Expression item) {
        java.util.Map<?, ?> map = (java.util.Map<?, ?>) collection.getValue();
        return Expression.of(map.containsKey(item.getValue()));
    }

    private Expression stringContains(Expression collection, Expression item) {
        if (item.getValue() == null) {
            return Expression.of(false);
        }
        String str = (String) collection.getValue();
        return Expression.of(str.contains((String) item.getValue()));
    }

    private Expression listContains(Expression collection, Expression item) {
        ListExpression list = (ListExpression) collection;
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i).getValue();
            if (Objects.equals(o, item.getValue())) {
                return Expression.of(true);
            }
        }
        return Expression.of(false);
    }

    private Expression arrayContains(Expression collection, Expression item) {
        Array array = (Array) collection;
        for (int i = 0; i < array.length(); i++) {
            Object o = array.get(i);
            if (Objects.equals(o, item.getValue())) {
                return Expression.of(true);
            }
        }
        return Expression.of(false);
    }

}
