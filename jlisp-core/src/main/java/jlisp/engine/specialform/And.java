package jlisp.engine.specialform;

import jlisp.engine.*;

import java.util.List;

public class And extends SpecialForm {

    @Override
    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {

        // evaluates each form, one at a time, from left to right. If any form evaluates to nil,
        // the value nil is immediately returned without evaluating the remaining forms.
        for (int i = 0; i < args.size() - 1; i++) {
            Expression result = Engine.evaluate(args.get(i), env, debugger, depth+1);
            if (!result.asBoolean()) {
                return result;
            }
        }

        // If every form but the last evaluates to a non-nil value, and returns whatever the last form returns.
        return Engine.evaluate(args.get(args.size() - 1), env, debugger, depth+1);
    }
}
