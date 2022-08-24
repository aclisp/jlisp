package formular.engine;

public interface Debugger {
    void expressionEvaluated(Expression before, Expression after, int depth);
}
