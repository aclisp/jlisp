package jlisp.engine.function.text;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

/**
 * Determines if text ends with specific characters and returns TRUE if it does. Returns FALSE if it doesn't.
 */
public class Ends extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String s = args.get(0).asText("");
        String suffix = args.get(1).asText("");
        return Expression.of(s.endsWith(suffix));
    }

}
