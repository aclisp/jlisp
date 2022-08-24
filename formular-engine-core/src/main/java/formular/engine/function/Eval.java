package formular.engine.function;

import formular.engine.Debugger;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;

public class Eval extends Function {

    private final Environment env;
    private final Debugger debugger;
    private final int depth;

    public Eval(Environment env, Debugger debugger, int depth) {
        this.env = env;
        this.debugger = debugger;
        this.depth = depth;
    }

    public Expression invoke(ListExpression args) throws Exception {
        return Engine.evaluate(args.get(0), env, debugger, depth+1);
    }

}
