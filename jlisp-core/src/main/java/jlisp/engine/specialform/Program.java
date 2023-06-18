package jlisp.engine.specialform;

import jlisp.engine.*;

import java.util.List;

public class Program extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        Expression result = Expression.of(null);
        for (Expression exp : args) {
            result = Engine.evaluate(exp, env, debugger, depth);
        }
        return result;
    }

}
