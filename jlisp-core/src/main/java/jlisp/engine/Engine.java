package jlisp.engine;

import jlisp.engine.specialform.*;

import java.util.HashMap;
import java.util.List;

public class Engine {
    public static Expression apply(Function function, ListExpression arguments) throws Exception {
        return function.invoke(arguments);
    }
    public static Expression evaluate(Expression object, Environment environment) throws Exception {
        return evaluate(object, environment, null, 0);
    }
    public static Expression evaluate(Expression object, Environment environment, Debugger debugger) throws Exception {
        return evaluate(object, environment, debugger, 0);
    }
    public static Expression evaluate(Expression object, Environment environment, Debugger debugger, int depth) throws Exception {
        long begin = 0;
        Expression result = null;
        if (debugger != null) {
            begin = System.nanoTime();
            result = Symbol.of("?");
        }
        try {
            if (object instanceof Symbol) {
                Symbol symbol = (Symbol) object;
                result = environment.get(symbol);
                if (result == null) {
                    throw new RuntimeException("Symbol undefined: " + symbol);
                }
                return result;
            } else if (object instanceof Atom) {
                return result = object;
            } else if (object instanceof ListExpression) {
                ListExpression expression = (ListExpression) object;
                if (expression.isEmpty()) {
                    // Empty list is nil/false
                    return result = expression;
                }
                // The first item in a list must be a symbol
                Expression first = expression.get(0);
                List<Expression> args = expression.subList(1, expression.size());
                SpecialForm form;
                if (first instanceof Symbol && (form = environment.getSpecialForm((Symbol) first)) != null) {
                    // Every special form has its own idiosyncratic syntax
                    return result = form.evaluate(args, environment, debugger, depth);
                } else {
                    // First item wasn't a special form so it must evaluate to a function
                    return result = funcEvaluate(first, args, environment, debugger, depth);
                }
            } else {
                throw new IllegalArgumentException("Can't evaluate " + object);
            }
        } catch (Throwable exception) {
            if (debugger != null) {
                if (!(exception instanceof Break.WithResult)) {
                    long end = System.nanoTime();
                    debugger.exceptionCaught(object, exception, depth, end-begin);
                }
            }
            throw exception;
        } finally {
            if (debugger != null) {
                long end = System.nanoTime();
                debugger.expressionEvaluated(object, result, depth, end-begin);
            }
        }
    }
    private static Expression funcEvaluate(Expression first, List<Expression> rest, Environment env, Debugger debugger, int depth) throws Exception {
        Function function = (Function) evaluate(first, env, debugger, depth+1);
        ListExpression args = new ListExpression(rest.size());
        for (Expression expr : rest) {
            args.add(evaluate(expr, env, debugger, depth+1));
        }
        try {
            return apply(function, args);
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            throw new RuntimeException(first + ": " + function + ": " + ex, ex);
        }
    }
}
