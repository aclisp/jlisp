package jlisp.engine.specialform;

import java.util.List;

import jlisp.engine.Debugger;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.engine.Lambda;
import jlisp.engine.ListExpression;
import jlisp.engine.SpecialForm;
import jlisp.engine.Symbol;

public class LambdaFactory extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        ListExpression params = (ListExpression) args.get(0);
        ListExpression body = new ListExpression(args.subList(1, args.size()));
        body.add(0, Symbol.of("progn"));
        return Lambda.of(params, body, env, debugger, depth);
    }

}
