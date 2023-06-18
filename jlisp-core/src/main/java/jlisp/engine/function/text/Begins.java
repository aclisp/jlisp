package jlisp.engine.function.text;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

/**
 * Determines if text begins with specific characters and returns TRUE if it does. Returns FALSE if it doesn't.
 */
public class Begins extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String s = args.get(0).asText("");
        String prefix = args.get(1).asText("");
        return Expression.of(s.startsWith(prefix));
    }

}
