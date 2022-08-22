package formular.engine.function;

import formular.engine.Array;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Length extends Function {

    public Expression invoke(ListExpression args) {
        Expression listOrArray = args.get(0);
        if (listOrArray instanceof Array) {
            return Util.expressionOf(((Array) listOrArray).length());
        } else {
            return Util.expressionOf(((ListExpression) listOrArray).size());
        }
    }

}
