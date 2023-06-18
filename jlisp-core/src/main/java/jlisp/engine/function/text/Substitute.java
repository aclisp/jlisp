package jlisp.engine.function.text;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

/**
 * Substitutes new text for old text in a text string.
 */
public class Substitute extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        String oldText = args.get(1).asText("");
        String newText = args.get(2).asText("");
        String result = text.replace(oldText, newText);
        return Expression.of(result);
    }

}
