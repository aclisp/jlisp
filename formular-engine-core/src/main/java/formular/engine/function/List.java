package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;

public class List extends Function {

    public Expression invoke(ListExpression args) {
        return args;
    }

}
