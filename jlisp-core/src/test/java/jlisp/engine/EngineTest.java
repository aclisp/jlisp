package jlisp.engine;

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
        exp.add(Expression.of(1));
        exp.add(Expression.of(2));
        exp.add(Expression.of(3));

        assertEquals(Expression.of(6), Engine.evaluate(exp, env));
    }
}
