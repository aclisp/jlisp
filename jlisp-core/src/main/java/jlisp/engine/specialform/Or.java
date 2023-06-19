package jlisp.engine.specialform;

import jlisp.engine.*;

import java.util.List;

public class Or extends SpecialForm {

    @Override
    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {

        // evaluates each form, one at a time, from left to right. If any form other than the last evaluates to something
        // other than nil, "or" immediately returns that non-nil value without evaluating the remaining forms.
        for (int i = 0; i < args.size() - 1; i++) {
            Expression result = Engine.evaluate(args.get(i), env, debugger, depth+1);
            if (result.asBoolean()) {
                return result;
            }
        }

        // If every form but the last evaluates to nil, "or" returns whatever evaluation of the last of the forms returns.
        return Engine.evaluate(args.get(args.size() - 1), env, debugger, depth+1);
    }
}
