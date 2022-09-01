package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class SetHash extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        MakeHashTable.Table hashTable = (MakeHashTable.Table) args.get(0).getValue();
        Object key = args.get(1).getValue();
        Object value = args.get(2).getValue();
        hashTable.put(key, value);
        return Util.expressionOf(value);
    }

}
