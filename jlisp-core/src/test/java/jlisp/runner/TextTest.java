package jlisp.runner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jlisp.engine.Default;
import jlisp.engine.Environment;

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

    @Test
    public void testRight() throws Exception {
        assertEquals("1234", Runner.execute("(right \"abcd1234\" 4)", env).getValue());
    }

    @Test
    public void testLeftPad() throws Exception {
        assertEquals("       abc", Runner.execute("(left-pad \"abc\" 10)", env).getValue());
        assertEquals("     abcde", Runner.execute("(left-pad \"abcde\" 10)", env).getValue());
        assertEquals("=====abcde", Runner.execute("(left-pad \"abcde\" 10 \"=\")", env).getValue());
        assertEquals("=>=>=abcde", Runner.execute("(left-pad \"abcde\" 10 \"=>\")", env).getValue());
        assertEquals("abc", Runner.execute("(left-pad \"abcde\" 3)", env).getValue());
        assertEquals("abc", Runner.execute("(left-pad \"  abcde  \" 3)", env).getValue());
    }

    @Test
    public void testRightPad() throws Exception {
        assertEquals("abc       ", Runner.execute("(right-pad \"abc\" 10)", env).getValue());
        assertEquals("abcde     ", Runner.execute("(right-pad \"abcde\" 10)", env).getValue());
        assertEquals("abcde=====", Runner.execute("(right-pad \"abcde\" 10 \"=\")", env).getValue());
        assertEquals("abcde=>=>=", Runner.execute("(right-pad \"abcde\" 10 \"=>\")", env).getValue());
        assertEquals("cde", Runner.execute("(right-pad \"abcde\" 3)", env).getValue());
        assertEquals("cde", Runner.execute("(right-pad \"  abcde  \" 3)", env).getValue());
    }

    @Test
    public void testMid() throws Exception {
        assertEquals("cd12", Runner.execute("(mid \"abcd1234\" 3 4)", env).getValue());
    }

    @Test
    public void testSubstitute() throws Exception {
        Runner.execute("(def Email \"aclisp@gmail.com\")", env);
        assertEquals("www.gmail.com", Runner.execute("(subst Email (left Email (find \"@\" Email)) \"www.\")", env).getValue());
    }

    @Test
    public void testTrim() throws Exception {
        assertEquals("abcde", Runner.execute("(trim \"  abcde  \")", env).getValue());
    }

    @Test
    public void testLowerUpper() throws Exception {
        assertEquals("MYCOMPANY.COM", Runner.execute("(upper \"mycompany.com\")", env).getValue());
        assertEquals("mycompany.com", Runner.execute("(lower \"MYCOMPANY.COM\")", env).getValue());
    }

    @Test
    public void testNumber() throws Exception {
        assertEquals(123, Runner.execute("(number 123)", env).getValue());
        assertEquals(123, Runner.execute("(number \"123\")", env).getValue());
        try {
            assertEquals(123, Runner.execute("(number \"abc\")", env).getValue());
        } catch (Exception ex) {
            // Should fail
        }
    }

    @Test
    public void testText() throws Exception {
        assertEquals("abc", Runner.execute("(text \"abc\")", env).getValue());
        assertEquals("123", Runner.execute("(text 123)", env).getValue());
        assertEquals("9007199254740991", Runner.execute("(text 9007199254740991)", env).getValue());
        assertEquals("9007199254740992", Runner.execute("(text 9007199254740992)", env).getValue());
        assertEquals("9007199254740992", Runner.execute("(text 9007199254740993)", env).getValue());
    }

    @Test
    public void testRegex() throws Exception {
        assertTrue(Runner.execute("(regex \"999-99-9999\" \"[0-9]{3}-[0-9]{2}-[0-9]{4}\")", env).asBoolean());
        assertTrue(Runner.execute("(regex \"123-45-6789\" \"[0-9]{3}-[0-9]{2}-[0-9]{4}\")", env).asBoolean());
        assertFalse(Runner.execute("(regex \"123-45+6789\" \"[0-9]{3}-[0-9]{2}-[0-9]{4}\")", env).asBoolean());
    }
}
