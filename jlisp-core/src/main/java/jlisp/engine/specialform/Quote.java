package jlisp.engine.specialform;

import jlisp.engine.Debugger;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.engine.SpecialForm;

import java.util.List;

public class Quote extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        return args.get(0);
    }

}
