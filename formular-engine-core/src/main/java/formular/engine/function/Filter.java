package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Filter extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Function function = (Function) args.get(0);
        ListExpression sequence = (ListExpression) args.get(1);
        ListExpression result = new ListExpression();
        for (int i = 0; i < sequence.size(); i++) {
            ListExpression arg = new ListExpression();
            arg.add(sequence.get(i));
            arg.add(Util.expressionOf(i));
            if (function.invoke(arg).asBoolean()) {
                result.add(sequence.get(i));
            }
        }
        return result;
    }

}
