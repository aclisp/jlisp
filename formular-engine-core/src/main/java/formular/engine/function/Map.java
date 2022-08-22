package formular.engine.function;

import java.util.Collections;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;

public class Map extends Function {

    public Expression invoke(ListExpression args) throws Exception {
        Function function = (Function) args.get(0);
        ListExpression list = (ListExpression) args.get(1);
        ListExpression result = new ListExpression(list.size());
        for (Expression arg : list) {
            result.add(function.invoke(new ListExpression(Collections.singletonList(arg))));
        }
        return result;
    }

}
