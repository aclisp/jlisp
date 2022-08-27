package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;

public class Reduce extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Function function = (Function) args.get(0);
        ListExpression sequence = (ListExpression) args.get(1);
        Expression result = sequence.get(0);
        for (Expression current : sequence.subList(1, sequence.size())) {
            ListExpression arg = new ListExpression();
            arg.add(result);
            arg.add(current);
            result = function.invoke(arg);
        }
        return result;
    }

}
