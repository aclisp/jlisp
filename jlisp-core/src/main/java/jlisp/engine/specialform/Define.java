package jlisp.engine.specialform;

import java.util.List;

import jlisp.engine.Debugger;
import jlisp.engine.Engine;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.engine.SpecialForm;
import jlisp.engine.Symbol;

public class Define extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        Symbol name = (Symbol) args.get(0);
        Expression value = args.get(1);
        Expression eValue = Engine.evaluate(value, env, debugger, depth+1);
        env.put(name, eValue);
        return eValue;
    }

}
