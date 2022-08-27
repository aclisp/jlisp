package formular.engine.function;

import java.util.LinkedHashMap;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class GetHash extends Function {

    @Override
    @SuppressWarnings("unchecked")
    public Expression invoke(ListExpression args) throws Exception {
        LinkedHashMap<Object, Object> hashTable = (LinkedHashMap<Object, Object>) args.get(0).getValue();
        Object key = args.get(1).getValue();
        return Util.expressionOf(hashTable.get(key));
    }

}
