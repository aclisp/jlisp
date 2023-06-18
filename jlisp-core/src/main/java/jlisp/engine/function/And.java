package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

public class And extends Function {

    public Expression invoke(ListExpression args) throws Exception {
        for (int i = 0; i < args.size() - 1; i++) {
            Expression arg = args.get(i);
            if (!arg.asBoolean()) {
                return arg;
            }
        }
        return args.get(args.size() - 1);
    }

}
