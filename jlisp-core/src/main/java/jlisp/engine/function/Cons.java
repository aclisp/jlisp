package jlisp.engine.function;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

public class Cons extends Function {

    public Expression invoke(ListExpression args) {
        ListExpression result = new ListExpression();
        result.add(args.get(0));
        Expression rest = args.get(1);
        if (rest instanceof ListExpression) {
            result.addAll((ListExpression) rest);
        } else {
            result.add(rest);
        }
        return result;
    }

}
