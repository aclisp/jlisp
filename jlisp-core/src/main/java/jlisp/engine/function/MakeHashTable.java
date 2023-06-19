package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

import java.util.LinkedHashMap;

public class MakeHashTable extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Table table = new Table();
        for (int i = 0; i < args.size(); i += 2) {
            Object key = args.get(i).getValue();
            Object value = null;
            if (i+1 < args.size()) {
                value = args.get(i+1).getValue();
            }
            table.put(key, value);
        }
        return Expression.of(table);
    }

    public static class Table extends LinkedHashMap<Object, Object> {
    }

}
