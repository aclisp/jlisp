package jlisp.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

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
