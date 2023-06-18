package jlisp.runner;

import jlisp.engine.Default;
import jlisp.engine.Environment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicalTest {
    private Environment env;

    @BeforeEach
    public void setUp() {
        env = Default.environment();
    }

    @Test
    public void testBlankValue() throws Exception {
        assertEquals(2, Runner.execute("(not-blank 2 1)", env).getValue());
        assertEquals(1, Runner.execute("(not-blank () 1)", env).getValue());
        assertEquals(1, Runner.execute("(not-blank [] 1)", env).getValue());
        assertEquals(1, Runner.execute("(not-blank null 1)", env).getValue());
        assertEquals(1, Runner.execute("(not-blank \"\" 1)", env).getValue());
        Runner.execute("(def T (make-hash-table))", env);
        assertEquals(1, Runner.execute("(not-blank T 1)", env).getValue());
        Runner.execute("(def T (json-parse \"{}\"))", env);
        assertEquals(1, Runner.execute("(not-blank (json-ptr T \"/a\") 1)", env).getValue());
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
