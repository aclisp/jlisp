package formular.runner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import formular.engine.Default;
import formular.engine.Environment;

public class TextTest {
    private Environment env;

    @BeforeEach
    public void setUp() {
        env = Default.environment();
    }

    @Test
    public void testAddQuote() throws Exception {
        assertEquals("\"abc\"", Runner.execute("(add-quote \"abc\")", env).getValue());
    }

    @Test
    public void testBegins() throws Exception {
        assertTrue(Runner.execute("(begins \"abcd1234\" \"abcd\")", env).asBoolean());
    }

    @Test
    public void testEnds() throws Exception {
        assertTrue(Runner.execute("(ends \"abcd1234\" \"1234\")", env).asBoolean());
    }

    @Test
    public void testFind() throws Exception {
        assertEquals(0, Runner.execute("(find \"efg\" \"abcd1234\")", env).getValue());
        assertEquals(5, Runner.execute("(find \"123\" \"abcd1234\")", env).getValue());
        assertEquals(9, Runner.execute("(find \"123\" \"abcd12341234\" 6)", env).getValue());
    }

    @Test
    public void testLeft() throws Exception {
        assertEquals("abcd", Runner.execute("(left \"abcd1234\" 4)", env).getValue());
        Runner.execute("(def Email \"aclisp@gmail.com\")", env);
        assertEquals("aclisp@", Runner.execute("(left Email (find \"@\" Email))", env).getValue());
    }
}
