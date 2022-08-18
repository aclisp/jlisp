package formular.engine;

import java.util.List;

public class Lambda extends Function {
    private Lambda() {}
    public static Lambda of(ListExpression params, ListExpression body, Environment env, Engine engine) {
        Lambda lambda = new Lambda();
        lambda.params = params;
        lambda.body = body;
        lambda.env = env;
        lambda.engine = engine;
        return lambda;
    }
    private ListExpression params;
    private ListExpression body;
    private Environment env;
    private Engine engine;
    public Expression invoke(ListExpression args) throws Exception {
        Environment tempEnv = new Environment(env);
        for (int i = 0; i < params.size(); i++) {
            Symbol param = (Symbol) params.get(i);
            Expression arg = args.get(i);
            tempEnv.put(param, arg);
        }
        return engine.evaluate(body, tempEnv);
    }
    protected List<?> getParameterHelpNames() {
        return params.getValue();
    }
}
