package formular.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import formular.engine.Default;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.parser.json.Node;

public class JsonTest {
    private Environment env;

    @BeforeEach
    public void setUp() {
        env = Default.environment();
    }

    @Test
    public void testSerDes() throws Exception {
        String code = "(def ** (lambda (x y) (if (<= y 0) 1 (* x (** x (- y 1)))))) (** 2 8)";
        Expression expr = Symbolic.parse(code);
        assertEquals(256, Engine.evaluate(expr, env).getValue());
        ObjectMapper objectMapper = new ObjectMapper();
        Node node = Json.serialize(expr);
        String json = objectMapper.writeValueAsString(node);
        assertNotNull(json);
        Node node1 = objectMapper.readValue(json, Node.class);
        Expression expr1 = Json.deserialize(node1);
        assertEquals(256, Engine.evaluate(expr1, env).getValue());
    }
}
