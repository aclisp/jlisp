package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class GetHash extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        MakeHashTable.Table hashTable = (MakeHashTable.Table) args.get(0).getValue();
        Object key = args.get(1).getValue();
        return Util.expressionOf(hashTable.get(key));
    }

}
