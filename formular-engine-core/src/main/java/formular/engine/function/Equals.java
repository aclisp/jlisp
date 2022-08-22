package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Equals extends Function {

    public Expression invoke(ListExpression args) {
        Object arg1 = args.get(0).getValue();
        Object arg2 = args.get(1).getValue();
        return Util.expressionOf(arg1 == arg2 || arg1 != null && arg1.equals(arg2));
    }

}
