package formular.runner;

import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.parser.Symbolic;

public class Runner {
    private Engine engine = new Engine();
    public Expression execute(String program, Environment environment) throws Exception {
        return engine.evaluate(Symbolic.parse(program), environment);
    }
}
