package formular.parser;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import formular.engine.Array;
import formular.engine.Default;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.JavaObject;
import formular.engine.ListExpression;
import formular.engine.Symbol;
import formular.parser.json.Node;

public class Json {
    public static Node serialize(Expression expr) {
        if (expr instanceof Symbol) {
            return new formular.parser.json.Symbol((Symbol)expr);
        } else if (expr instanceof Array) {
            return new formular.parser.json.Array((Array)expr);
        } else if (expr instanceof JavaObject) {
            return new formular.parser.json.JavaObject((JavaObject)expr);
        } else if (expr instanceof ListExpression) {
            return new formular.parser.json.ListExpression((ListExpression)expr);
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
