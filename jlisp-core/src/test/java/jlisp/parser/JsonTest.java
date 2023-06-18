package jlisp.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jlisp.engine.Default;
import jlisp.engine.Engine;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.parser.json.Node;

public class JsonTest {
    private Environment env;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        env = Default.environment();
    }

    @Test
    public void testSerDes() throws Exception {
        String code = "(def ** (lambda (x y) (if (<= y 0) 1 (* x (** x (- y 1)))))) (** 2 8)";
        Expression expr = Symbolic.parse(code);
        assertEquals(256, Engine.evaluate(expr, env).getValue());
        Node node = Json.serialize(expr);
        String json = objectMapper.writeValueAsString(node);
        assertNotNull(json);
        Node node1 = objectMapper.readValue(json, Node.class);
        Expression expr1 = Json.deserialize(node1);
        assertEquals(256, Engine.evaluate(expr1, env).getValue());
    }

    @Test
    public void testExpressionId() throws JsonProcessingException {
        String code = "(+ 1 2)";
        Expression expr = Symbolic.parse(code);
        Node node = Json.serialize(expr);
        assertTrue(node.getId() == node.getExpression().getId());
        String json = objectMapper.writeValueAsString(node);
        assertNotNull(json);
        Node node1 = objectMapper.readValue(json, Node.class);
        Expression expr1 = Json.deserialize(node1);
        assertTrue(expr1.getId() == node1.getId());
    }
}
