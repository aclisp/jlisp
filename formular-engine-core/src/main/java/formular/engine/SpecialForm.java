package formular.engine;

import java.util.List;

public abstract class SpecialForm {
    public abstract Expression evaluate(List<Expression> args, Environment env) throws Exception;
}
