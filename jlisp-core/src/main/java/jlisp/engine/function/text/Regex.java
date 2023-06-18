package jlisp.engine.function.text;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

/**
 * Compares a text field to a regular expression and returns TRUE if there’s a match. Otherwise, it returns FALSE.
 * A regular expression is a string used to describe a format of a string according to certain syntax rules.
 */
public class Regex extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        String regex = args.get(1).asText("");
        return Expression.of(text.matches(regex));
    }

}
