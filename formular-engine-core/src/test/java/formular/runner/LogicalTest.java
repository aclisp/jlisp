package formular.runner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import formular.engine.Default;
import formular.engine.Environment;

public class LogicalTest {
    private Environment env;

    @BeforeEach
    public void setUp() {
        env = Default.environment();
    }

    @Test
    public void testBlankValue() throws Exception {
        assertEquals(2, Runner.execute("(blank-value 2 1)", env).getValue());
        assertEquals(1, Runner.execute("(blank-value () 1)", env).getValue());
        assertEquals(1, Runner.execute("(blank-value [] 1)", env).getValue());
        assertEquals(1, Runner.execute("(blank-value null 1)", env).getValue());
        assertEquals(1, Runner.execute("(blank-value \"\" 1)", env).getValue());
        Runner.execute("(def T (make-hash-table))", env);
        assertEquals(1, Runner.execute("(blank-value T 1)", env).getValue());
        Runner.execute("(def T (json-parse \"{}\"))", env);
        assertEquals(1, Runner.execute("(blank-value (json-ptr T \"/a\") 1)", env).getValue());
    }

    @Test
    public void testIsBlank() throws Exception {
        assertFalse(Runner.execute("(is-blank 2)", env).asBoolean());
        assertTrue(Runner.execute("(is-blank ())", env).asBoolean());
        assertTrue(Runner.execute("(is-blank [])", env).asBoolean());
        assertTrue(Runner.execute("(is-blank null)", env).asBoolean());
        assertTrue(Runner.execute("(is-blank \"\")", env).asBoolean());
        Runner.execute("(def T (make-hash-table))", env);
        assertTrue(Runner.execute("(is-blank T)", env).asBoolean());
        Runner.execute("(def T (json-parse \"{}\"))", env);
        assertTrue(Runner.execute("(is-blank (json-ptr T \"/a\"))", env).asBoolean());
    }

    @Test
    public void testIsNumber() throws Exception {
        assertTrue(Runner.execute("(is-number 123)", env).asBoolean());
        assertTrue(Runner.execute("(is-number \"123\")", env).asBoolean());
        assertFalse(Runner.execute("(is-number \"abc\")", env).asBoolean());
        assertFalse(Runner.execute("(is-number ())", env).asBoolean());
        assertTrue(Runner.execute("(is-number -123)", env).asBoolean());
        assertFalse(Runner.execute("(is-number \"-123\")", env).asBoolean());
        assertFalse(Runner.execute("(is-number \"+123\")", env).asBoolean());
    }
}
