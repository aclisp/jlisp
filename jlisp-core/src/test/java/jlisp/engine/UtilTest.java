package jlisp.engine;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {
    @Test
    void escapeString() {
        assertEquals("abc", Util.escapeString("abc"));
        assertEquals("a\\\\bc", Util.escapeString("a\\bc"));
        assertEquals("a\\\"bc", Util.escapeString("a\"bc"));
    }

    @Test
    void testTest() {
        Set<String> s = new LinkedHashSet<>();
        s.add("aaa");
        s.add("bbb");
        assertEquals("[aaa, bbb]", s.toString());
    }
}
