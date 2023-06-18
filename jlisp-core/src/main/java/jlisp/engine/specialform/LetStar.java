package jlisp.engine.specialform;

import java.util.List;

import jlisp.engine.Debugger;
import jlisp.engine.Engine;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.engine.ListExpression;
import jlisp.engine.SpecialForm;
import jlisp.engine.Symbol;

public class LetStar extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        ListExpression defs = (ListExpression) args.get(0);
        ListExpression body = new ListExpression(args.subList(1, args.size()));
        body.add(0, Symbol.of("progn"));
        Environment localEnvironment = new Environment(env);
        for (Expression exp : defs) {
            ListExpression def = (ListExpression) exp;
            Symbol symbol = (Symbol) def.get(0);
            localEnvironment.put(symbol, Engine.evaluate(def.get(1), localEnvironment, debugger, depth+1));
        }
        return Engine.evaluate(body, localEnvironment, debugger, depth+1);
    }

}
