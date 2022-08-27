package formular.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import formular.engine.Expression;

public class SymbolicTest {
    @Test
    public void testTokenize() {
        assertEquals(Collections.singletonList("foo"), Symbolic.tokenize("foo"));
        assertEquals(Arrays.asList("(", "a", " ", "b", " ", "c", ")"), Symbolic.tokenize("(a b c)"));
        assertEquals(Arrays.asList("(", " ", "a", " ", "b", " ", "c", " ", ")", " "), Symbolic.tokenize("( a b c ) "));
        assertEquals(Arrays.asList("(", "a", " ", "b", " ", "[", "c", "]", ")"), Symbolic.tokenize("(a b [c])"));
        assertEquals(Arrays.asList("(", "a", " ", "b", " ", "\"", "foo bar", "\"", ")"),
        Symbolic.tokenize("(a b \"foo bar\")"));
        assertEquals(Arrays.asList("(", "a", " ", "b", " ", "\"", "  ", "\"", ")"),
        Symbolic.tokenize("(a b \"  \")"));
        assertEquals(Arrays.asList("'", "(", "a", " ", "b", " ", "c", ")"), Symbolic.tokenize("'(a b c)"));
    }

    @Test
    public void testComment() throws Exception {
        assertEquals(Arrays.asList(";", " (a b c)"), Symbolic.tokenize("; (a b c)"));
        assertEquals(Arrays.asList(";", " (a b c)\n", "(", "1", " ", "2", " ", "3", ")"),
        Symbolic.tokenize("; (a b c)\n(1 2 3)"));
        assertEquals(Arrays.asList("foo", " ", ";", " bar"), Symbolic.tokenize("foo ; bar"));
        assertEquals(Arrays.asList("foo", " ", ";", "bar\n", "baz"), Symbolic.tokenize("foo ;bar\nbaz"));
    }

    @Test
    public void testFormat() {
        String code = "(let ((foo 1) (bar 2)) (baz) (buzz))";
        Expression expr = Symbolic.parse(code);
        assertEquals(code, Symbolic.format(expr, false));
        assertEquals("(let ((foo 1)\n      (bar 2))\n (baz)\n (buzz))", Symbolic.format(expr, true));
    }
}
