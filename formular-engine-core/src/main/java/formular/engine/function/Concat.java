package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Concat extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        StringBuilder builder = new StringBuilder();
        for (Expression arg : args) {
            Object value = arg.getValue();
            builder.append(value);
        }
        return Util.expressionOf(builder.toString());
    }

}
