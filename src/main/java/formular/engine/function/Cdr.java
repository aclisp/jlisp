package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;

public class Cdr extends Function {

    public Expression invoke(ListExpression args) {
        ListExpression arg = (ListExpression) args.get(0);
        return new ListExpression(arg.subList(1, arg.size()));
    }

}
