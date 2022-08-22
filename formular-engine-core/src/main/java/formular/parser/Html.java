package formular.parser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import formular.engine.Array;
import formular.engine.Default;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.JavaObject;
import formular.engine.ListExpression;
import formular.engine.Symbol;

public class Html {
    public static String out(Expression expr, int level) {
        String indent = level>0?String.format("%" + level*2 + "s", ""):"";
        StringBuilder b = new StringBuilder();
        if (expr instanceof Symbol) {
            b.append(indent);
            b.append("<div class=\"symbol\"> ");
            b.append(expr);
            b.append(" </div>\n");
        } else if (expr instanceof Array) {
            b.append(indent);
            b.append("<div class=\"array\"> ");
            b.append(expr);
            b.append(" </div>\n");
        } else if (expr instanceof JavaObject) {
            b.append(indent);
            b.append("<div class=\"object\"> ");
            b.append(expr);
            b.append(" </div>\n");
        } else if (expr instanceof ListExpression) {
            b.append(indent);
            b.append("<div class=\"list\">\n");
            ListExpression expression = (ListExpression) expr;
            for (Expression exp : expression) {
                b.append(out(exp, level+1));
            }
            b.append(indent);
            b.append("</div>\n");
        } else {
            throw new IllegalArgumentException("Unsupported expression: " + expr);
        }
        return b.toString();
    }
    public static void main(String[] args) throws Exception {
        byte[] encoded = Files.readAllBytes(Paths.get("a.lisp"));
        String program = new String(encoded, StandardCharsets.UTF_8);
        Expression expr = Symbolic.parse(program);
        System.out.println(out(expr, 0));

        Environment env = Default.environment();
        System.out.println(Engine.evaluate(expr, env).toString());
    }
}
