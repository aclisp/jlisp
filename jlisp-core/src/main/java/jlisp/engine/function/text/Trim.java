package jlisp.engine.function.text;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

/**
 * Removes the spaces and tabs from the beginning and end of a text string.
 */
public class Trim extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        String result = text.trim();
        return Expression.of(result);
    }

}
