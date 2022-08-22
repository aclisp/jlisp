package formular.engine.specialform;

import java.util.List;

import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.ListExpression;
import formular.engine.SpecialForm;
import formular.engine.Symbol;

public class LetStar extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env) throws Exception {
        ListExpression defs = (ListExpression) args.get(0);
        ListExpression body = new ListExpression(args.subList(1, args.size()));
        body.add(0, Symbol.of("progn"));
        Environment localEnvironment = new Environment(env);
        for (Expression exp : defs) {
            ListExpression def = (ListExpression) exp;
            Symbol symbol = (Symbol) def.get(0);
            localEnvironment.put(symbol, Engine.evaluate(def.get(1), localEnvironment));
        }
        return Engine.evaluate(body, localEnvironment);
    }

}
