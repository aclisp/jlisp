package jlisp.engine.specialform;

import jlisp.engine.*;

import java.util.List;

public class Define extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        Symbol name = (Symbol) args.get(0);
        Expression value = args.get(1);
        Expression eValue = Engine.evaluate(value, env, debugger, depth+1);
        env.put(name, eValue);
        return eValue;
    }

}
