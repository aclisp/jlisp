package formular.engine.specialform;

import java.util.List;

import formular.engine.Debugger;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.SpecialForm;
import formular.engine.Symbol;

public class Define extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        Symbol name = (Symbol) args.get(0);
        Expression value = args.get(1);
        Expression eValue = Engine.evaluate(value, env, debugger, depth+1);
        env.put(name, eValue);
        return eValue;
    }

}
