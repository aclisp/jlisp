package jlisp.engine.function.text;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

/**
 * Returns the specified number of characters from the end of a text string.
 */
public class Right extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        int numChars = args.get(1).asNumber(0).intValue();
        String right = text.substring(text.length()-numChars, text.length());
        return Expression.of(right);
    }

}
