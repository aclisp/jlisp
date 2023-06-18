package jlisp.engine.function.logical;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;

import jlisp.engine.Array;
import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

/**
 * Determines if an expression has a value and returns a substitute expression if it doesn’t.
 * If the expression has a value, returns the value of the expression.
 */
public class NotBlank extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Expression expr = args.get(0);
        Expression subst = args.get(1);
        if (expr instanceof Array) {
            if (((Array) expr).length() == 0) {
                return subst;
            }
        } else if (expr instanceof ListExpression) {
            if (((ListExpression) expr).isEmpty()) {
                return subst;
            }
        } else if (expr.getValue() == null) {
            return subst;
        } else if (expr.getValue() instanceof String) {
            if (((String) expr.getValue()).isEmpty()) {
                return subst;
            }
        } else if (expr.getValue() instanceof Map) {
            if (((Map<?, ?>) expr.getValue()).isEmpty()) {
                return subst;
            }
        } else if (expr.getValue() instanceof Collection) {
            if (((Collection<?>) expr.getValue()).isEmpty()) {
                return subst;
            }
        } else if (expr.getValue() instanceof MissingNode) {
            return subst;
        } else if (expr.getValue() instanceof NullNode) {
            return subst;
        }

        return expr;
    }

}
