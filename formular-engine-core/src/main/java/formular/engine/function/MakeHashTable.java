package formular.engine.function;

import java.util.LinkedHashMap;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class MakeHashTable extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        return Util.expressionOf(new Table());
    }

    public static class Table extends LinkedHashMap<Object, Object> {
    }

}
