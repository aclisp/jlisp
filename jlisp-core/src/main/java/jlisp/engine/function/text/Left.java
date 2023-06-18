package jlisp.engine.function.text;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

/**
 * Returns the specified number of characters from the beginning of a text string.
 */
public class Left extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        int numChars = args.get(1).asNumber(0).intValue();
        String left = text.substring(0, numChars);
        return Expression.of(left);
    }

}
