package jlisp.engine.function;

import java.util.Collection;

import jlisp.engine.Array;
import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class Length extends Function {

    public Expression invoke(ListExpression args) {
        Expression collection = args.get(0);
        if (collection instanceof Array) {
            return Expression.of(((Array) collection).length());
        } else if (collection instanceof ListExpression) {
            return Expression.of(((ListExpression) collection).size());
        } else if (collection.getValue() == null) {
            return Expression.of(0);
        } else if (collection.getValue() instanceof String) {
            return Expression.of(((String) collection.getValue()).length());
        } else if (collection.getValue() instanceof java.util.Map) {
            return Expression.of(((java.util.Map<?, ?>) collection.getValue()).size());
        } else if (collection.getValue() instanceof java.util.Collection) {
            return Expression.of(((Collection<?>) collection.getValue()).size());
        } else {
            throw new IllegalArgumentException("Unsupported collection: " + collection);
        }
    }

}
