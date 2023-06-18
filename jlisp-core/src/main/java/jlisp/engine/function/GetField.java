package jlisp.engine.function;

import java.util.Map;

import jlisp.engine.Atom;
import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

@SuppressWarnings({"rawtypes", "unchecked"})
public class GetField extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        java.util.Map map = (java.util.Map) args.get(0).getValue();
        if (map == null) {
            return Expression.of(null);
        }
        Expression key = args.get(1);
        Object defaultValue = null;
        if (args.size() > 2) {
            defaultValue = args.get(2).getValue();
        }
        Object result;

        if (key instanceof Atom) {
            result = map.getOrDefault(key.getValue(), defaultValue);
        } else {
            result = get(map, (ListExpression) key, defaultValue);
        }

        return Expression.of(result);
    }

    private Object get(Map map, ListExpression keys, Object defaultValue) {
        int size = keys.size();
        if (size == 0) {
            return defaultValue;
        }
        if (size == 1) {
            return map.getOrDefault(keys.get(0).getValue(), defaultValue);
        }
        for (Expression key : keys.subList(0, size-1)) {
            Object k = key.getValue();
            Object v = map.get(k);
            if (!(v instanceof java.util.Map)) {
                return defaultValue;
            }
            map = (java.util.Map) v;
        }
        return map.getOrDefault(keys.get(size-1).getValue(), defaultValue);
    }

}
