package jlisp.engine.specialform;

import java.util.List;

import jlisp.engine.Debugger;
import jlisp.engine.Engine;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.engine.SpecialForm;
import jlisp.engine.Util;

public class Program extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        Expression result = Expression.of(null);
        for (Expression exp : args) {
            result = Engine.evaluate(exp, env, debugger, depth);
        }
        return result;
    }

}
