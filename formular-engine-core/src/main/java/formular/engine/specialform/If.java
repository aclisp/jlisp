package formular.engine.specialform;

import java.util.List;

import formular.engine.Debugger;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.ListExpression;
import formular.engine.SpecialForm;
import formular.engine.Symbol;

public class If extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {
        Expression condition = args.get(0);
        Expression then = args.get(1);
        ListExpression els = new ListExpression(args.subList(2, args.size()));
        els.add(0, Symbol.of("progn"));
        boolean result = Engine.evaluate(condition, env, debugger, depth+1).asBoolean();
        return Engine.evaluate(result ? then : els, env, debugger, depth+1);
    }

}
