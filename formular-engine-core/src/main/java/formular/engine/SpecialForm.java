package formular.engine;

import java.util.List;

public abstract class SpecialForm {
    public abstract Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception;
}
