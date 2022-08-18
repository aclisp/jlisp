package formular.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

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

}
