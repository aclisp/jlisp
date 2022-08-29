package formular.runner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import formular.engine.Debugger;
import formular.engine.Default;
import formular.engine.Environment;
import formular.engine.Expression;

public class DebuggerTest {

    private Environment env;
    private Debugger debugger;

    @BeforeEach
    public void setUp() {
        env = Default.environment();
        debugger = new Debugger() {
            public void exceptionCaught(Expression before, Throwable exception, int depth, long nanoDuration) {
            }
            public void expressionEvaluated(Expression before, Expression after, int depth, long nanoDuration) {
                for (int i=0; i<depth; i++) {
                    System.out.print("  ");
                }
                System.out.println("before: " + before + " , after: " + after);
            }
        };
    }

    @Test
    public void testEvaluate() throws Exception {
        Runner.execute("((lambda (x) (+ 1 x)) 1)", env, debugger);
    }

}
