package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;

public class Append extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        ListExpression result = new ListExpression();
        for (Expression arg : args) {
            if (arg instanceof ListExpression) {
                result.addAll((ListExpression) arg);
            } else {
                result.add(arg);
            }
        }
        return result;
    }

}
