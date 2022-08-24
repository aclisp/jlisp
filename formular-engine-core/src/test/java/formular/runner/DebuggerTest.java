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
            public void expressionEvaluated(Expression before, Expression after, int depth) {
                for (int i=0; i<depth; i++) {
                    System.out.print("  ");
                }
                System.out.println("before: " + before + " , after: " + after);
            }
        };
    }

    @Test
    public void testEvaluate() throws Exception {
        //Runner.execute("(假设 ((里程 10) (单价 2.3) (总价 (乘积 里程 单价))) (如果 (小于 总价 20) \"很便宜\" (如果 (小于 总价 50) \"可接受\" (如果 (大于 总价 100) \"坐地铁\" \"有点亏\")))) ", env, debugger);
        Runner.execute("((lambda (x) (+ 1 x)) 1)", env, debugger);
    }

}
