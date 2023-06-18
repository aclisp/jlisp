package jlisp.engine.specialform;

import java.util.List;

import jlisp.engine.Debugger;
import jlisp.engine.Engine;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.engine.ListExpression;
import jlisp.engine.SpecialForm;
import jlisp.engine.Symbol;

public class If extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        Expression condition = args.get(0);
        Expression then = args.get(1);
        ListExpression els = new ListExpression(args.subList(2, args.size()));
        els.add(0, Symbol.of("progn"));
        boolean result = Engine.evaluate(condition, env, debugger, depth+1).asBoolean();
        return Engine.evaluate(result ? then : els, env, debugger, depth+1);
    }

}
