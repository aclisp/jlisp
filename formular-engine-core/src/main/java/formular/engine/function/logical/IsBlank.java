package formular.engine.function.logical;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;

import formular.engine.Array;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Determines if an expression has a value and returns TRUE if it doesn’t.
 * If it contains a value, this function returns FALSE.
 */
public class IsBlank extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Expression expr = args.get(0);
        if (expr instanceof Array) {
            if (((Array) expr).length() == 0) {
                return Util.expressionOf(true);
            }
        } else if (expr instanceof ListExpression) {
            if (((ListExpression) expr).isEmpty()) {
                return Util.expressionOf(true);
            }
        } else if (expr.getValue() == null) {
            return Util.expressionOf(true);
        } else if (expr.getValue() instanceof String) {
            if (((String) expr.getValue()).isEmpty()) {
                return Util.expressionOf(true);
            }
        } else if (expr.getValue() instanceof Map) {
            if (((Map<?, ?>) expr.getValue()).isEmpty()) {
                return Util.expressionOf(true);
            }
        } else if (expr.getValue() instanceof Collection) {
            if (((Collection<?>) expr.getValue()).isEmpty()) {
                return Util.expressionOf(true);
            }
        } else if (expr.getValue() instanceof MissingNode) {
            return Util.expressionOf(true);
        } else if (expr.getValue() instanceof NullNode) {
            return Util.expressionOf(true);
        }
        return Util.expressionOf(false);
    }

}
