package jlisp.engine;

public interface Debugger {
    void expressionEvaluated(Expression before, Expression after, int depth, long nanoDuration);
    void exceptionCaught(Expression before, Throwable exception, int depth, long nanoDuration);
}
