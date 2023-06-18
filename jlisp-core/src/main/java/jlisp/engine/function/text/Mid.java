package jlisp.engine.function.text;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

/**
 * Returns the specified number of characters from the middle of a text string given the starting position.
 */
public class Mid extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        int startNum = args.get(1).asNumber(1).intValue();
        int numChars = args.get(2).asNumber(0).intValue();
        String mid = text.substring(startNum-1, startNum-1+numChars);
        return Expression.of(mid);
    }

}
