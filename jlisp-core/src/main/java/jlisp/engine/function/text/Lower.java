package jlisp.engine.function.text;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

/**
 * Converts all letters in the specified text string to lowercase.
 * Any characters that aren’t letters are unaffected by this function.
 */
public class Lower extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        String result = text.toLowerCase();
        return Expression.of(result);
    }

}
