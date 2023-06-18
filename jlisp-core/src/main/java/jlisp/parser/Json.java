package jlisp.parser;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jlisp.engine.Array;
import jlisp.engine.Default;
import jlisp.engine.Engine;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.engine.JavaObject;
import jlisp.engine.ListExpression;
import jlisp.engine.Symbol;
import jlisp.parser.json.Node;

public class Json {
    public static Node serialize(Expression expr) {
        if (expr instanceof Symbol) {
            return new jlisp.parser.json.Symbol((Symbol)expr);
        } else if (expr instanceof Array) {
            return new jlisp.parser.json.Array((Array)expr);
        } else if (expr instanceof JavaObject) {
            return new jlisp.parser.json.JavaObject((JavaObject)expr);
        } else if (expr instanceof ListExpression) {
            return new jlisp.parser.json.ListExpression((ListExpression)expr);
        } else {
            throw new IllegalArgumentException("Unsupported expression: " + expr);
        }
    }
    public static Expression deserialize(Node node) {
        return node.getExpression();
    }
    public static void main(String[] args) throws Exception {
        byte[] encoded = Files.readAllBytes(Paths.get("a.lisp"));
        String program = new String(encoded, StandardCharsets.UTF_8);
        Expression expr = Symbolic.parse(program);
        Node node = serialize(expr);
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File("a.json"), node);

        Environment env = Default.environment();
        System.out.println(Engine.evaluate(expr, env).toString());
    }
}
