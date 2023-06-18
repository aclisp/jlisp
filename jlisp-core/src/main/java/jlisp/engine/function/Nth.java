package jlisp.engine.function;

import jlisp.engine.Array;
import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

public class Nth extends Function {

    public Expression invoke(ListExpression args) {
        int n = args.get(0).asNumber(0).intValue();
        Expression collection = args.get(1);
        if (collection instanceof Array) {
            return Expression.of(((Array) collection).get(n));
        } else if (collection instanceof ListExpression) {
            return getNthExpression(n, (ListExpression) collection);
        } else if (collection.getValue() == null) {
            return Expression.of(null);
        } else if (collection.getValue() instanceof java.util.List) {
            return Expression.of(((java.util.List<?>) collection.getValue()).get(n));
        } else {
            throw new IllegalArgumentException("Unsupported collection: " + collection);
        }
    }

    private Expression getNthExpression(int n, ListExpression collection) {
        int m = collection.size() - 1;
        if (n < 0 || n > m) {
            throw new IllegalArgumentException("N (="+n+") should be in the range [0, "+m+"]");
        }
        return collection.get(n);
    }

}
