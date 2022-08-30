package formular.engine.specialform;

import java.util.List;

import formular.engine.Debugger;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.SpecialForm;
import formular.engine.Util;

public class Break extends SpecialForm {

    @Override
    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        Expression result = Util.expressionOf(null);
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
