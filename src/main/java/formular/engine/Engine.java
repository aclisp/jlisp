package formular.engine;

public class Engine {
    static class SpecialForm {
        final String id;
        final String alias;
        SpecialForm(String id, String alias) {
            this.id = id;
            this.alias = alias;
        }
        boolean is(Expression expr) {
            return Util.isSymbol(expr, id) || Util.isSymbol(expr, alias);
        }
    }
    final static SpecialForm DEF = new SpecialForm("def", "定义");
    final static SpecialForm LAMBDA = new SpecialForm("lambda", "函数");
    final static SpecialForm IF = new SpecialForm("if", "如果");
    final static SpecialForm QUOTE = new SpecialForm("quote", "引用");
    final static SpecialForm PROGN = new SpecialForm("progn", "执行");
    final static SpecialForm LET_STAR = new SpecialForm("let*", "假设");
    public static Expression apply(Function function, ListExpression arguments) throws Exception {
        return function.invoke(arguments);
    }
    public static Expression evaluate(Expression object, Environment environment) throws Exception {
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
            if (DEF.is(first)) {
                Symbol name = (Symbol) expression.get(1);
                Expression value = expression.get(2);
                Expression eValue = evaluate(value, environment);
                environment.put(name, eValue);
                return eValue;
            } else if (LAMBDA.is(first)) {
                ListExpression params = (ListExpression) expression.get(1);
                ListExpression body = new ListExpression(expression.subList(2, expression.size()));
                body.add(0, Symbol.of("progn"));
                return Lambda.of(params, body, environment);
            } else if (IF.is(first)) {
                Expression condition = expression.get(1);
                Expression then = expression.get(2);
                ListExpression els = new ListExpression(expression.subList(3, expression.size()));
                els.add(0, Symbol.of("progn"));
                boolean result = evaluate(condition, environment).asBoolean();
                return evaluate(result ? then : els, environment);
            } else if (QUOTE.is(first)) {
                return expression.get(1);
            } else if (PROGN.is(first)) {
                Expression result = Util.expressionOf(null);
                for (Expression exp : expression.subList(1, expression.size())) {
                    result = evaluate(exp, environment);
                }
                return result;
            } else if (LET_STAR.is(first)) {
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
                ListExpression args = new ListExpression(expression.size()-1);
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
