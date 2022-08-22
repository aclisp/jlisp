package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;

public class Car extends Function {

    public Expression invoke(ListExpression args) {
        ListExpression arg = (ListExpression) args.get(0);
        return arg.get(0);
    }

}
