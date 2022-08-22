package formular.engine.function;

import formular.engine.Array;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Nth extends Function {

    public Expression invoke(ListExpression args) {
        int n = (Integer) args.get(0).getValue();
        Expression listOrArray = args.get(1);
        if (listOrArray instanceof Array) {
            return Util.expressionOf(((Array) listOrArray).get(n));
        } else {
            return ((ListExpression) listOrArray).get(n);
        }
    }

}
