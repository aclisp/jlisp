package formular.engine.function;

import formular.engine.Atom;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SetField extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        java.util.Map map = (java.util.Map) args.get(0).getValue();
        Expression key = args.get(1);
        Object value = args.get(2).getValue();

        if (key instanceof Atom) {
            map.put(key.getValue(), value);
        } else {
            put(map, (ListExpression) key, value);
        }

        return Util.expressionOf(value);
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
