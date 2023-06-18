package jlisp.engine.function;

import jlisp.engine.Atom;
import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SetField extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        java.util.Map map = (java.util.Map) args.get(0).getValue();
        if (map == null) {
            return Expression.of(null);
        }
        Expression key = args.get(1);
        Object value = args.get(2).getValue();

        if (key instanceof Atom) {
            map.put(key.getValue(), value);
        } else {
            put(map, (ListExpression) key, value);
        }

        return Expression.of(value);
    }

    private void put(java.util.Map map, ListExpression keys, Object value) {
        int size = keys.size();
        if (size == 0) {
            return;
        }
        if (size == 1) {
            map.put(keys.get(0).getValue(), value);
            return;
        }
        for (Expression key : keys.subList(0, size-1)) {
            Object k = key.getValue();
            Object v = map.get(k);
            if (v instanceof java.util.Map) {
                map = (java.util.Map) v;
                continue;
            }
            java.util.Map n = new MakeHashTable.Table();
            map.put(k, n); // Overwrite !!!
            map = n;
        }
        map.put(keys.get(size-1).getValue(), value);
    }

}
