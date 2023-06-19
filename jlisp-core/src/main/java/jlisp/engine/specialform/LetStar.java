package jlisp.engine.specialform;

import jlisp.engine.*;

import java.util.List;

public class LetStar extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        ListExpression defs = (ListExpression) args.get(0);
        ListExpression body = new ListExpression(args.subList(1, args.size()));
        body.add(0, Symbol.of("progn"));
        Environment localEnvironment = env.copy();
        for (Expression exp : defs) {
            ListExpression def = (ListExpression) exp;
            Symbol symbol = (Symbol) def.get(0);
            localEnvironment.put(symbol, Engine.evaluate(def.get(1), localEnvironment, debugger, depth+1));
        }
        return Engine.evaluate(body, localEnvironment, debugger, depth+1);
    }

}
