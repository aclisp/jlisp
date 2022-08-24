package formular.engine.specialform;

import java.util.List;

import formular.engine.Debugger;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.SpecialForm;
import formular.engine.Util;

public class Program extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        Expression result = Util.expressionOf(null);
        for (Expression exp : args) {
            result = Engine.evaluate(exp, env, debugger, depth+1);
        }
        return result;
    }

}
