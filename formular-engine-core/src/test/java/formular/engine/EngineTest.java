package formular.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EngineTest {

    private Environment env;

    @BeforeEach
    public void setUp() {
        env = Default.environment();
    }

    @Test
    public void testEvaluate() throws Exception {
        ListExpression exp = new ListExpression();
        exp.add(Symbol.of("+"));
        exp.add(Util.expressionOf(1));
        exp.add(Util.expressionOf(2));
        exp.add(Util.expressionOf(3));

        assertEquals(Util.expressionOf(6), Engine.evaluate(exp, env));
    }
}
