package jlisp.engine.function.text;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

/**
 * Returns the position of a string within a string of text represented as a number.
 * The first character in a string is designated as one rather than zero.
 * If your search doesn't return any results, a 0 displays in the field.
 */
public class Find extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String searchText = args.get(0).asText("");
        String text = args.get(1).asText("");
        int startNum = 1;
        if (args.size() > 2) {
            startNum = args.get(2).asNumber(1).intValue();
        }
        int indexOf = text.indexOf(searchText, startNum-1);
        return Expression.of(indexOf+1);
    }

}
