package jlisp.engine.function.logical;

import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import jlisp.engine.Array;
import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

import java.util.Collection;
import java.util.Map;

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
                return Expression.of(true);
            }
        } else if (expr instanceof ListExpression) {
            if (((ListExpression) expr).isEmpty()) {
                return Expression.of(true);
            }
        } else if (expr.getValue() == null) {
            return Expression.of(true);
        } else if (expr.getValue() instanceof String) {
            if (((String) expr.getValue()).isEmpty()) {
                return Expression.of(true);
            }
        } else if (expr.getValue() instanceof Map) {
            if (((Map<?, ?>) expr.getValue()).isEmpty()) {
                return Expression.of(true);
            }
        } else if (expr.getValue() instanceof Collection) {
            if (((Collection<?>) expr.getValue()).isEmpty()) {
                return Expression.of(true);
            }
        } else if (expr.getValue() instanceof MissingNode) {
            return Expression.of(true);
        } else if (expr.getValue() instanceof NullNode) {
            return Expression.of(true);
        }
        return Expression.of(false);
    }

}
