package jlisp.engine;

import jlisp.parser.Symbolic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EngineTest {

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

        ListExpression process = new ListExpression();
        process.add(Symbol.of("progn"));
        process.add(
                defineVariable("result",
                        buildOperation("X_QUERY", "Account__s"))
        );
        process.add(
                buildDecision(
                        buildBranch(buildClause("==", "result", 1),
                                buildOperation("X_SAVE", "")),
                        buildBranch(buildClause("==", "result", 2),
                                buildOperation("X_SEND_MSG", ""))
                )
        );
        System.out.println(Symbolic.format(process, true));
        Engine.evaluate(process, env);
    }

    private Expression buildOperation(String operationName, String parameter) {
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
        System.out.println("XQuery: " + args);;
        return Expression.of(2);
    }
}

class XSave extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        System.out.println("XSave: " + args);;
        return Expression.of(null);
    }
}

class XSendMsg extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        System.out.println("XSendMsg: " + args);;
        return Expression.of(null);
    }
}
