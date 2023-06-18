package jlisp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    void hello() {
        assertTrue(true, "hello testing");
        String indent = String.format("%" + 1*2 + "s", "");
        assertEquals("  ", indent);
    }

    @Test
    void testTryFinally() {
        assertEquals("bc", tryFinally("abc"));
    }

    String tryFinally(String param) {
        String result = "";
        try {
            return result = param.substring(1);
        } finally {
            assertEquals("bc", result);
        }
    }
}
