package jlisp.runner;

import jlisp.engine.*;
import jlisp.parser.Symbolic;

public class Runner {
    public static Expression execute(String program, Environment environment) throws Exception {
        return Engine.evaluate(Symbolic.parse(program), environment);
    }
    public static Expression execute(String program, Environment environment, Debugger debugger) throws Exception {
        return Engine.evaluate(Symbolic.parse(program), environment, debugger, 0);
    }
    public static void main(String[] args) throws Exception {
        Environment env = Default.environment();
        Debugger debugger = new Debugger() {
            public void exceptionCaught(Expression before, Throwable exception, int depth, long nanoDuration) {
            }
            public void expressionEvaluated(Expression before, Expression after, int depth, long nanoDuration) {
                if (!(before instanceof ListExpression)) {
                    return;
                }
                ListExpression expr = (ListExpression) before;
                if (expr.isEmpty()) {
                    return;
                }
                Expression first = expr.get(0);
                if (depth > 0 && first.equals(Symbol.of("progn"))) {
                    return;
                }
                StringBuilder b = new StringBuilder();
                for (int i=0; i<depth; i++) {
                    b.append("  ");
                }
                if (first.equals(Symbol.of("def"))) {
                    b.append(first);
                    b.append(" ");
                    b.append(expr.get(1));
                } else {
                    b.append(first);
                }
                b.append(" => ");
                b.append(after);
                b.append(String.format("%20.2fms", nanoDuration/1000000.0));
                System.out.println(b.toString());
            }
        };
        Runner.execute("(progn (def repeat (lambda (n f) (if (<= n 0) () (cons (f) (repeat (- n 1) f))))) (repeat 3 (lambda () 1))) ", env, debugger);
    }
}
