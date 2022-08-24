package formular.runner;

import formular.engine.Debugger;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.parser.Symbolic;

public class Runner {
    public static Expression execute(String program, Environment environment) throws Exception {
        return Engine.evaluate(Symbolic.parse(program), environment);
    }
    public static Expression execute(String program, Environment environment, Debugger debugger) throws Exception {
        return Engine.evaluate(Symbolic.parse(program), environment, debugger, 0);
    }
}
