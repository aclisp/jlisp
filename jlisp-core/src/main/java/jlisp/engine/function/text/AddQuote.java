package jlisp.engine.function.text;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

/**
 * Add the double quotation marks around the text.
 */
public class AddQuote extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String s = args.get(0).asText("");
        return Expression.of("\"" + s + "\"");
    }

}
