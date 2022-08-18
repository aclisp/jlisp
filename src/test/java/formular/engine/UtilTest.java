package formular.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilTest {
    @Test
    void escapeString() {
        assertEquals("abc", Util.escapeString("abc"));
        assertEquals("a\\\\bc", Util.escapeString("a\\bc"));
        assertEquals("a\\\"bc", Util.escapeString("a\"bc"));
    }
}
