package formular.engine.function;

import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;

public class Eval extends Function {

    private final Environment env;

    public Eval(Environment env) {
        this.env = env;
    }

    public Expression invoke(ListExpression args) throws Exception {
        return Engine.evaluate(args.get(0), env);
    }

}
