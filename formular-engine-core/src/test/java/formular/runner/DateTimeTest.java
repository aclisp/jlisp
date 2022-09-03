package formular.runner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import formular.engine.Default;
import formular.engine.Environment;

public class DateTimeTest {
    private Environment env;

    @BeforeEach
    public void setUp() {
        env = Default.environment();
    }

    @Test
    public void testNow() throws Exception {
        String now = (String) Runner.execute("(now)", env).getValue();
        System.out.println(now);
    }
}
