package jlisp.engine.function.logical;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.JavaObject;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

/**
 * Determines if a text value is a number and returns TRUE if it is.
 * Otherwise, it returns FALSE.
 */
public class IsNumber extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Expression expr = args.get(0);
        if (expr instanceof JavaObject) {
            if (expr.getValue() instanceof Number) {
                return Expression.of(true);
            } else if (expr.getValue() instanceof String) {
                return Expression.of(isNumeric((String) expr.getValue()));
            }
        }
        return Expression.of(false);
    }

    public static boolean isNumeric(final CharSequence cs) {
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
