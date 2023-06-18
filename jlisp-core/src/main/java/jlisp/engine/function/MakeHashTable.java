package jlisp.engine.function;

import java.util.LinkedHashMap;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class MakeHashTable extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        return Expression.of(new Table());
    }

    public static class Table extends LinkedHashMap<Object, Object> {
    }

}
