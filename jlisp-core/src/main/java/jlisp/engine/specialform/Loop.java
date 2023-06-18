package jlisp.engine.specialform;

import java.util.List;

import jlisp.engine.Debugger;
import jlisp.engine.Engine;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.engine.SpecialForm;
import jlisp.engine.Util;

public class Loop extends SpecialForm {

    @Override
    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {

        if (args.isEmpty()) {
            return Expression.of(null);
        }

        // Each form is evaluated in turn from left to right. When the last form has been evaluated,
        // then the first form is evaluated again, and so on, in a never-ending cycle.

        try {
            while (true) {
                for (Expression exp : args) {
                    Engine.evaluate(exp, env, debugger, depth+1);
                }
            }
        } catch (Break.WithResult brk) {
            return brk.getResult();
        }

        // The loop construct never returns a value. Its execution must be terminated explicitly, using break.

    }

}
