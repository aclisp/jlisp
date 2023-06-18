package jlisp.engine.specialform;

import java.util.List;

import jlisp.engine.Debugger;
import jlisp.engine.Engine;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.engine.SpecialForm;
import jlisp.engine.Util;

public class Break extends SpecialForm {

    @Override
    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        Expression result = Expression.of(null);
        if (!args.isEmpty()) {
            result = Engine.evaluate(args.get(0), env, debugger, depth+1);
        }
        throw new WithResult(result);
    }

    public static class WithResult extends Exception {
        private final Expression result;
        public WithResult(Expression result) {
            this.result = result;
        }
        public Expression getResult() {
            return result;
        }
    }
}
