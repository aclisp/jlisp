package formular.engine;

public class Engine {
    public Expression apply(Function function, ListExpression arguments) throws Exception {
        return function.invoke(arguments);
    }
    public Expression evaluate(Expression object, Environment environment) throws Exception {
        if (object instanceof Symbol) {
            Symbol symbol = (Symbol) object;
            Expression result = environment.get(symbol);
            if (result == null) {
                throw new RuntimeException("Symbol undefined: " + symbol);
            }
            return result;
        } else if (object instanceof Atom) {
            return object;
        } else if (object instanceof ListExpression) {
            ListExpression expression = (ListExpression) object;
            if (expression.isEmpty()) {
                // Empty list is nil/false
                return expression;
            }
            // The first item in a list must be a symbol
            Expression first = expression.get(0);
            if (Util.isSymbol(first, "def")) {
                Symbol name = (Symbol) expression.get(1);
                Expression value = expression.get(2);
                Expression eValue = evaluate(value, environment);
                environment.put(name, eValue);
                return eValue;
            } else if (Util.isSymbol(first, "lambda")) {
                ListExpression params = (ListExpression) expression.get(1);
                ListExpression body = new ListExpression(expression.subList(2, expression.size()));
                body.add(0, Symbol.of("progn"));
                return Lambda.of(params, body, environment, this);
            } else if (Util.isSymbol(first, "if")) {
                Expression condition = expression.get(1);
                Expression then = expression.get(2);
                ListExpression els = new ListExpression(expression.subList(3, expression.size()));
                els.add(0, Symbol.of("progn"));
                boolean result = evaluate(condition, environment).asBoolean();
                return evaluate(result ? then : els, environment);
            } else if (Util.isSymbol(first, "quote")) {
                return expression.get(1);
            } else if (Util.isSymbol(first, "progn")) {
                Expression result = Util.expressionOf(null);
                for (Expression exp : expression.subList(1, expression.size())) {
                    result = evaluate(exp, environment);
                }
                return result;
            } else if (Util.isSymbol(first, "let*")) {
                ListExpression defs = (ListExpression) expression.get(1);
                ListExpression body = new ListExpression(expression.subList(2, expression.size()));
                body.add(0, Symbol.of("progn"));
                Environment localEnvironment = new Environment(environment);
                for (Expression exp : defs) {
                    ListExpression def = (ListExpression) exp;
                    Symbol symbol = (Symbol) def.get(0);
                    localEnvironment.put(symbol, evaluate(def.get(1), localEnvironment));
                }
                return evaluate(body, localEnvironment);
            } else {
                // First item wasn't a special form so it must evaluate to a function
                Function function = (Function) evaluate(first, environment);
                ListExpression args = new ListExpression();
                for (Expression exp : expression.subList(1, expression.size())) {
                    args.add(evaluate(exp, environment));
                }
                try {
                    return apply(function, args);
                } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
                    throw new RuntimeException(first + ": " + function + "\n" + ex, ex);
                }
            }
        } else {
            throw new IllegalArgumentException("Can't evaluate " + object);
        }
    }
}
