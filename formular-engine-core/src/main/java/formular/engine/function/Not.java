package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Not extends Function {

    public Expression invoke(ListExpression args) throws Exception {
        return Util.expressionOf(!args.get(0).asBoolean());
    }

}
