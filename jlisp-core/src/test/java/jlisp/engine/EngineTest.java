package jlisp.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jlisp.parser.Json;
import jlisp.parser.Symbolic;
import jlisp.parser.json.Node;
import jlisp.runner.Runner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EngineTest {

    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private Environment env;

    @BeforeEach
    public void setUp() {
        env = Default.environment();
    }

    @Test
    public void testEvaluate() throws Exception {
        ListExpression exp = new ListExpression();
        exp.add(Symbol.of("+"));
        exp.add(Expression.of(1));
        exp.add(Expression.of(2));
        exp.add(Expression.of(3));

        assertEquals(Expression.of(6), Engine.evaluate(exp, env));
    }

    @Test
    public void testSample1() throws Exception {
        Environment env = Default.environment();
        env.put(Symbol.of("X_QUERY"), new XQuery());
        env.put(Symbol.of("X_SAVE"), new XSave());
        env.put(Symbol.of("X_SEND_MSG"), new XSendMsg());

        Map<String, Object> xSaveParam = new HashMap<>();
        xSaveParam.put("p1", "v1");
        xSaveParam.put("p2", "v2");

        ListExpression process = new ListExpression();
        process.add(Symbol.of("progn"));
        process.add(
                defineVariable("result",
                        buildOperation("X_QUERY", "Account__s"))
        );
        process.add(
                buildDecision(
                        buildBranch(buildClause("==", "result", 1),
                                buildOperation("X_SAVE", xSaveParam)),
                        buildBranch(buildClause("==", "result", 2),
                                buildOperation("X_SEND_MSG", ""))
                )
        );

        String lispCode = Symbolic.format(process, true);
        //System.out.println(lispCode);

        String jsonCode =  objectMapper.writeValueAsString(Json.serialize(process));
        //System.out.println(jsonCode);

        Engine.evaluate(process, env);

        lispCode = "(progn\n" +
                " (def result (X_QUERY \"Account__s\"))\n" +
                " (cond\n" +
                "  ((== result 1) (X_SAVE (make-hash-table \"p1\" \"v1\" \"p2\" \"v2\")))\n" +
                "  ((== result 2) (X_SEND_MSG \"\"))))";
        System.out.println("--- execute lisp code ---");
        Runner.execute(lispCode, env);

        Node node = objectMapper.readValue(jsonCode, Node.class);
        Expression expr = Json.deserialize(node);
        System.out.println("--- execute json code ---");
        Engine.evaluate(expr, env);
    }

    private Expression buildOperation(String operationName, Object parameter) {
        ListExpression p = new ListExpression();
        p.add(Symbol.of(operationName));
        p.add(Expression.of(parameter));
        return p;
    }

    private Expression defineVariable(String name, Expression value) {
        ListExpression p = new ListExpression();
        p.add(Symbol.of("def"));
        p.add(Symbol.of(name));
        p.add(value);
        return p;
    }

    private Expression buildDecision(Expression... branches) {
        ListExpression p = new ListExpression();
        p.add(Symbol.of("cond"));
        for (Expression branch : branches) {
            p.add(branch);
        }
        return p;
    }

    private Expression buildBranch(Expression clause, Expression operation) {
        ListExpression p = new ListExpression();
        p.add(clause);
        p.add(operation);
        return p;
    }

    private Expression buildClause(String operator, String variable, Object value) {
        ListExpression p = new ListExpression();
        p.add(Symbol.of(operator));
        p.add(Symbol.of(variable));
        p.add(Expression.of(value));
        return p;
    }
}

class XQuery extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        System.out.println("XQuery: " + args);
        return Expression.of(1);
    }
}

class XSave extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        System.out.println("XSave: " + args);
        return Expression.of(null);
    }
}

class XSendMsg extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        System.out.println("XSendMsg: " + args);
        return Expression.of(null);
    }
}
