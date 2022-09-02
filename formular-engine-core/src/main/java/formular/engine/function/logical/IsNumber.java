package formular.engine.function.logical;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.JavaObject;
import formular.engine.ListExpression;
import formular.engine.Util;

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
                return Util.expressionOf(true);
            } else if (expr.getValue() instanceof String) {
                return Util.expressionOf(isNumeric((String) expr.getValue()));
            }
        }
        return Util.expressionOf(false);
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
