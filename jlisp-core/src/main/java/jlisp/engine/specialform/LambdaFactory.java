package jlisp.engine.specialform;

import jlisp.engine.*;

import java.util.List;

public class LambdaFactory extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        ListExpression params = (ListExpression) args.get(0);
        ListExpression body = new ListExpression(args.subList(1, args.size()));
        body.add(0, Symbol.of("progn"));
        return Lambda.of(params, body, env, debugger, depth);
    }

}
