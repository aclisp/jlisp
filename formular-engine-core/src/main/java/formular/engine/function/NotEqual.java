package formular.engine.function;

import java.util.Objects;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class NotEqual extends Function {

    public Expression invoke(ListExpression args) {
        for (int i = 0; i < args.size()-1; i++) {
            for (int j = i+1; j < args.size(); j++) {
                Object arg1 = args.get(i).getValue();
                Object arg2 = args.get(j).getValue();
                if (Objects.equals(arg1, arg2)) {
                    return Util.expressionOf(false);
                }
            }
        }
        return Util.expressionOf(true);
    }

}
