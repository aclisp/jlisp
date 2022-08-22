package formular.engine;

import java.util.HashMap;
import java.util.List;

import formular.engine.specialform.Define;
import formular.engine.specialform.If;
import formular.engine.specialform.LambdaFactory;
import formular.engine.specialform.LetStar;
import formular.engine.specialform.Program;
import formular.engine.specialform.Quote;

public class Engine {
    final static HashMap<Symbol, SpecialForm> specialForms = new HashMap<>();
    private final static SpecialForm DEF = new Define();
    private final static SpecialForm LAMBDA = new LambdaFactory();
    private final static SpecialForm IF = new If();
    private final static SpecialForm QUOTE = new Quote();
    private final static SpecialForm PROGN = new Program();
    private final static SpecialForm LET_STAR = new LetStar();
    static {
        specialForms.put(Symbol.of("def"), DEF);
        specialForms.put(Symbol.of("定义"), DEF);
        specialForms.put(Symbol.of("lambda"), LAMBDA);
        specialForms.put(Symbol.of("函数"), LAMBDA);
        specialForms.put(Symbol.of("if"), IF);
        specialForms.put(Symbol.of("如果"), IF);
        specialForms.put(Symbol.of("quote"), QUOTE);
        specialForms.put(Symbol.of("引用"), QUOTE);
        specialForms.put(Symbol.of("progn"), PROGN);
        specialForms.put(Symbol.of("执行"), PROGN);
        specialForms.put(Symbol.of("let*"), LET_STAR);
        specialForms.put(Symbol.of("假设"), LET_STAR);
    }
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
            List<Expression> args = expression.subList(1, expression.size());
            SpecialForm form;
            if (first instanceof Symbol && (form = specialForms.get(first)) != null) {
                // Every special form has its own idiosyncratic syntax
                return form.evaluate(args, environment);
            } else {
                // First item wasn't a special form so it must evaluate to a function
                return funcEvaluate(first, args, environment);
            }
        } else {
            throw new IllegalArgumentException("Can't evaluate " + object);
        }
    }
    private static Expression funcEvaluate(Expression first, List<Expression> rest, Environment env) throws Exception {
        Function function = (Function) evaluate(first, env);
        ListExpression args = new ListExpression(rest.size());
        for (Expression expr : rest) {
            args.add(evaluate(expr, env));
        }
        try {
            return apply(function, args);
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            throw new RuntimeException(first + ": " + function + "\n" + ex, ex);
        }
    }
}
