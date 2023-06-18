package jlisp.engine.function;

import jlisp.engine.*;

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
        return Engine.evaluate(args.get(0), env, debugger, depth);
    }

}
