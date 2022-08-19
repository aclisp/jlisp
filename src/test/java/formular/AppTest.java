package formular;

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
}
