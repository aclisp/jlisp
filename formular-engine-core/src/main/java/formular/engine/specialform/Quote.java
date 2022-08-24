package formular.engine.specialform;

import java.util.List;

import formular.engine.Debugger;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.SpecialForm;

public class Quote extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        return args.get(0);
    }

}
