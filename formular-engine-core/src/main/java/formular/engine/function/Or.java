package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;

public class Or extends Function {

    public Expression invoke(ListExpression args) throws Exception {
        for (int i = 0; i < args.size() - 1; i++) {
            Expression arg = args.get(i);
            if (arg.asBoolean()) {
                return arg;
            }
        }
        return args.get(args.size() - 1);
    }

}
