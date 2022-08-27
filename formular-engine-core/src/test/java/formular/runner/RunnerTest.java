package formular.runner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import formular.engine.Default;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.JavaObject;
import formular.engine.ListExpression;
import formular.engine.MethodInvoker;
import formular.engine.Symbol;
import formular.engine.Util;
import formular.parser.Symbolic;

public class RunnerTest {
    private static double DELTA = 0.0000000000000001;
    private Environment env;

    @BeforeEach
    public void setUp() {
        env = Default.environment();
        env.put(Symbol.of("addArray"), new Function() {
            public Expression invoke(ListExpression args) {
                int result = 0;
                for (Expression arg : args) {
                    Object array = arg.getValue();
                    for (int i = 0; i < Array.getLength(array); i++) {
                        result += Array.getInt(array, i);
                    }
                }
                return Util.expressionOf(result);
            }
        });
        env.put(Symbol.of("concat"), new Function() {
            public Expression invoke(ListExpression args) {
                StringBuilder builder = new StringBuilder();
                for (Expression arg : args) {
                    builder.append((String) arg.getValue());
                }
                return Util.expressionOf(builder.toString());
            }
        });
    }

    @Test
    public void testExecute() throws Exception {
        assertEquals(10, Runner.execute("(+ 1 2 3 4)", env).getValue());
    }

    @Test
    public void testArrays() throws Exception {
        assertEquals(15, Runner.execute("(addArray [1 2 3 4 5])", env).getValue());
        assertEquals(30, Runner.execute("(addArray [1 2 3 4 5] [1 2 3 4 5])", env).getValue());
        {
            Object result = Runner.execute("[1 2 3 4 5]", env).getValue();
            assertTrue(result instanceof int[]);
            assertArrayEquals((int[]) result, new int[] {1, 2, 3, 4, 5});
        }
        {
            Object result = Runner.execute("[1.1 2.2 3.3 4.4 5.5]", env).getValue();
            assertTrue(result instanceof double[]);
            assertArrayEquals(new double[] {1.1, 2.2, 3.3, 4.4, 5.5}, (double[]) result, 0);
        }
    }

    @Test
    public void testMethodInvoker() throws Exception {
        Method method = Integer.class.getMethod("toString", int.class, int.class);
        MethodInvoker toString = MethodInvoker.of(null, method);
        env.put(Symbol.of("toString"), toString);
        assertEquals("b", Runner.execute("(toString 11 16)", env).getValue());
    }

    @Test
    public void testDef() throws Exception {
        assertEquals(123, Runner.execute("(def foo 123)", env).getValue(),
            "Result of `def' is the value");
        assertTrue(env.containsKey(Symbol.of("foo")));
        assertEquals(Util.expressionOf(123), env.get(Symbol.of("foo")));
    }

    @Test
    public void testLambda() throws Exception {
        assertEquals(2, Runner.execute("((lambda (x) (+ 1 x)) 1)", env).getValue());
        Runner.execute("(def increment (lambda (x) (+ 1 x)))", env);
        assertEquals(5, Runner.execute("(increment 4)", env).getValue());
        assertEquals(3, Runner.execute("((lambda (x) 1 3) 1)", env).getValue());
    }

    @Test
    public void testIf() throws Exception {
        assertEquals(1, Runner.execute("(if (< 1 2) 1 2)", env).getValue());
        assertEquals(2, Runner.execute("(if (< 1 2 0) 1 2)", env).getValue());
        assertEquals(3, Runner.execute("(if (< 1 2 0) 1 2 3)", env).getValue(),
            "implied progn around else");
    }

    @Test
    public void testLetStar() throws Exception {
        assertEquals(3, Runner.execute("(let* ((foo 1) (bar 2)) (+ foo bar))", env).getValue());
        assertNull(env.get(Symbol.of("foo")));
        assertNull(env.get(Symbol.of("bar")));
        assertEquals(3, Runner.execute("(let* ((foo 1) (bar (+ 1 foo))) (+ foo bar))", env).getValue(),
            "Reference previous definitions");
        assertEquals(1, Runner.execute("(let* ((foo 1) (bar (+ 1 foo))) (+ foo bar) foo)", env).getValue(),
            "implied progn around body");
    }

    @Test
    public void testRecursiveReference() throws Exception {
        Environment stdEnv = Default.environment();
        Runner.execute("(def ** (lambda (x y) (if (<= y 0) 1 (* x (** x (- y 1))))))", stdEnv);
        assertEquals(256, Runner.execute("(** 2 8)", stdEnv).getValue());
    }

    @Test
    public void testProgn() throws Exception {
        assertEquals(6, Runner.execute("(def x (progn (+ 2 8) (+ 1 5)))", env).getValue());
        assertEquals(6, Runner.execute("x", env).getValue());
        assertEquals(6, Runner.execute("(+ 2 8) (+ 1 5)", env).getValue(),
            "Implied progn around top-level sexps");
        assertNull(Runner.execute("(progn)", env).getValue());
    }

    @Test
    public void testQuote() throws Exception {
        assertEquals("foo", Runner.execute("(quote foo)", env).getValue());
        assertEquals("foo", Runner.execute("(quote foo)", env).toString(),
            "String representation of symbol should not be quoted");
        assertEquals(Arrays.asList("foo", "bar"), Runner.execute("(quote (foo bar))", env).getValue());
        assertEquals(Arrays.asList("foo", "bar", Arrays.asList("baz", "buzz")),
                Runner.execute("(quote (foo bar (baz buzz)))", env).getValue());
    }

    @Test
    public void testQuoteSugar() throws Exception {
        assertEquals("foo", Runner.execute("'foo", env).getValue());
        assertEquals(Arrays.asList("quote", "foo"), Symbolic.parse("'foo").getValue());
        assertEquals(Arrays.asList(1, 2, 3), Runner.execute("'(1 2 3)", env).getValue());
    }

    @Test
    public void testComment() throws Exception {
        assertEquals(6, Runner.execute("(+ ; blah\n1 2 3)", env).getValue());
    }

    @Test
    public void testString() throws Exception {
        assertTrue(Runner.execute("\"foo\"", env) instanceof JavaObject,
        "String should parse to Java object, not symbol");
        assertEquals("foo", Runner.execute("\"foo\"", env).getValue());
        assertEquals(" ", Runner.execute("\" \"", env).getValue());
        try {
            Runner.execute("foo", env);
            fail("String without quotes is a symbol; evaluating an undefined symbol is an error");
        } catch (Exception ex) {
            // Should fail
        }
        assertEquals("foobar baz",
        Runner.execute("(concat \"foo\" \"bar baz\")", env).getValue());
        assertTrue(Runner.execute("(concat \"foo\" \"bar baz\")", env) instanceof JavaObject,
        "String return value should wrap to Java object, not symbol");
        assertEquals("\"foo\"", Runner.execute("\"foo\"", env).toString(),
        "String representation should be homoiconic");
        // Program: "\""
        // Parsed string: "
        assertEquals("\"", Runner.execute("\"\\\"\"", env).getValue());
        // Program: "\\\""
        // Parsed string: \"
        assertEquals("\\\"", Runner.execute("\"\\\\\\\"\"", env).getValue());
        {
            // Program: "\""
            // Parsed string: "
            // String representation: "\"" (same as program)
            String program = "\"\\\"\"";
            assertEquals(program, Runner.execute(program, env).toString());
        }
        {
            // Program: "\\\""
            // Parsed string: \"
            // String representation: "\\\""
            String program = "\"\\\\\\\"\"";
            assertEquals(program, Runner.execute(program, env).toString());
        }
    }

    @Test
    public void testFunctionString() throws Exception {
        assertEquals("Function()", Runner.execute("(lambda () ())", env).toString());
        assertEquals("Function(x, y, z)", Runner.execute("(lambda (x y z) ())", env).toString());
        Method method = Integer.class.getMethod("toString", int.class, int.class);
        MethodInvoker toString = MethodInvoker.of(null, method);
        env.put(Symbol.of("toString"), toString);
        assertEquals("Function(int, int)", toString.toString());
        assertEquals("Function(int, int)", Runner.execute("toString", env).toString());
    }

    @Test
    public void testListString() throws Exception {
        Environment stdEnv = Default.environment();
        assertEquals("()", Runner.execute("()", stdEnv).toString());
        assertEquals("(1 2 3)", Runner.execute("(list 1 2 3)", stdEnv).toString());
    }

    @Test
    public void testArrayString() throws Exception {
        Environment stdEnv = Default.environment();
        assertEquals("[]", Runner.execute("[]", stdEnv).toString());
        assertEquals("[1 2 3]", Runner.execute("[1 2 3]", stdEnv).toString());
    }

    @Test
    public void testNull() throws Exception {
        assertNull(Runner.execute("null", env).getValue());
        try {
            Runner.execute("(def null 1)", env);
            fail("Can't redefine null");
        } catch (Exception ex) {
            // Should fail
        }
    }

    @Test
    public void testTrueFalse() throws Exception {
        assertEquals(true, Runner.execute("true", env).getValue());
        assertEquals(false, Runner.execute("false", env).getValue());
        try {
            Runner.execute("(def true 1)", env);
            fail("Can't redefine true");
        } catch (Exception ex) {
            // Should fail
        }
        try {
            Runner.execute("(def false 1)", env);
            fail("Can't redefine false");
        } catch (Exception ex) {
            // Should fail
        }
    }

    @Test
    public void testDefaultEnvironment() throws Exception {
        Environment stdEnv = Default.environment();
        // +
        assertEquals(
                1.1, Runner.execute("(+ 0.5 0.6)", stdEnv).getValue(),
                "Double + Double");
        assertEquals(
                5, Runner.execute("(+ 2 3)", stdEnv).getValue(),
                "Integer + Integer");
        assertEquals(
                1.1, Runner.execute("(+ 0.1 1)", stdEnv).getValue(),
                "Double + Integer");
        // -
        assertEquals(
                0.5 - 0.6, (double) Runner.execute("(- 0.5 0.6)", stdEnv).getValue(), DELTA,
                "Double - Double");
        assertEquals(
                -1, Runner.execute("(- 2 3)", stdEnv).getValue(),
                "Integer - Integer");
        assertEquals(
                0.1 - 1, Runner.execute("(- 0.1 1)", stdEnv).getValue(),
                "Double - Integer");
        // *
        assertEquals(
                0.5 * 0.6, Runner.execute("(* 0.5 0.6)", stdEnv).getValue(),
                "Double * Double");
        assertEquals(
                6, Runner.execute("(* 2 3)", stdEnv).getValue(),
                "Integer * Integer");
        assertEquals(
                0.1 * 2, Runner.execute("(* 0.1 2)", stdEnv).getValue(),
                "Double * Integer");
        // /
        assertEquals(
                0.5 / 0.6, Runner.execute("(/ 0.5 0.6)", stdEnv).getValue(),
                "Double / Double");
        assertEquals(
                1.5, Runner.execute("(/ 3 2)", stdEnv).getValue(),
                "Integer / Integer");
        assertEquals(
                0.1 / 2, Runner.execute("(/ 0.1 2)", stdEnv).getValue(),
                "Double / Integer");
        assertEquals(
            70 / 7, Runner.execute("(/ 70 7)", stdEnv).getValue(),
            "Integer / Integer = Integer");
        // %
        assertEquals(1, Runner.execute("(% 10 3)", stdEnv).getValue());
        assertEquals(2, Runner.execute("(% 10 4)", stdEnv).getValue());
        assertEquals(0, Runner.execute("(% 10 5)", stdEnv).getValue());
        // <
        assertTrue(Runner.execute("(< 0.5 0.6)", stdEnv).asBoolean(), "Double < Double");
        assertFalse(Runner.execute("(< 3 2)", stdEnv).asBoolean(), "Integer < Integer");
        assertTrue(Runner.execute("(< 0.1 2)", stdEnv).asBoolean(), "Double < Integer");
        // >
        assertFalse(Runner.execute("(> 0.5 0.6)", stdEnv).asBoolean(), "Double > Double");
        assertTrue(Runner.execute("(> 3 2)", stdEnv).asBoolean(), "Integer > Integer");
        assertFalse(Runner.execute("(> 0.1 2)", stdEnv).asBoolean(), "Double > Integer");
        // <=
        assertTrue(Runner.execute("(<= 0.5 0.6)", stdEnv).asBoolean(), "Double <= Double");
        assertTrue(Runner.execute("(<= 0.6 0.6)", stdEnv).asBoolean(), "Double <= Double");
        assertFalse(Runner.execute("(<= 3 2)", stdEnv).asBoolean(), "Integer <= Integer");
        assertTrue(Runner.execute("(<= 3 3)", stdEnv).asBoolean(), "Integer <= Integer");
        assertTrue(Runner.execute("(<= 0.1 2)", stdEnv).asBoolean(), "Double <= Integer");
        assertTrue(Runner.execute("(<= 2.0 2)", stdEnv).asBoolean(), "Double <= Integer");
        // >=
        assertFalse(Runner.execute("(>= 0.5 0.6)", stdEnv).asBoolean(), "Double >= Double");
        assertTrue(Runner.execute("(>= 0.6 0.6)", stdEnv).asBoolean(), "Double >= Double");
        assertTrue(Runner.execute("(>= 3 2)", stdEnv).asBoolean(), "Integer >= Integer");
        assertTrue(Runner.execute("(>= 3 3)", stdEnv).asBoolean(),"Integer >= Integer");
        assertFalse(Runner.execute("(>= 0.1 2)", stdEnv).asBoolean(), "Double >= Integer");
        assertTrue(Runner.execute("(>= 2.0 2)", stdEnv).asBoolean(), "Double >= Integer");
        // is
        Object obj = new Object();
        stdEnv.put(Symbol.of("obj"), Util.expressionOf(obj));
        stdEnv.put(Symbol.of("obj2"), Util.expressionOf(obj));
        stdEnv.put(Symbol.of("obj3"), Util.expressionOf(new Object()));
        assertTrue(Runner.execute("(is obj obj2)", stdEnv).asBoolean());
        assertFalse(Runner.execute("(is obj obj3)", stdEnv).asBoolean());
        assertFalse(Runner.execute("(is \"foo\" \"foo\")", stdEnv).asBoolean());
        // eq
        assertTrue(Runner.execute("(eq 1 1)", stdEnv).asBoolean());
        assertFalse(Runner.execute("(eq 1 2)", stdEnv).asBoolean());
        assertTrue(Runner.execute("(eq \"foo\" \"foo\")", stdEnv).asBoolean());
        assertTrue(Runner.execute("(is eq =)", stdEnv).asBoolean());
        // neq
        assertTrue(Runner.execute("(neq 1 2)", stdEnv).asBoolean());
        assertFalse(Runner.execute("(neq 1 1)", stdEnv).asBoolean());
        assertFalse(Runner.execute("(neq \"foo\" \"foo\")", stdEnv).asBoolean());
        // car
        assertEquals(1, Runner.execute("(car (quote (1)))", stdEnv).getValue());
        assertEquals(1, Runner.execute("(car (quote (1 2 3 4)))", stdEnv).getValue());
        // cdr
        assertEquals(Collections.emptyList(), Runner.execute("(cdr (quote (1)))", stdEnv).getValue());
        assertEquals(Arrays.asList(2, 3, 4), Runner.execute("(cdr (quote (1 2 3 4)))", stdEnv).getValue());
        // cons
        assertEquals(Arrays.asList(1, 2), Runner.execute("(cons 1 2)", stdEnv).getValue());
        assertEquals(Arrays.asList(0, 1, 2, 3), Runner.execute("(cons 0 (quote (1 2 3)))", stdEnv).getValue());
        // length
        assertEquals(0, Runner.execute("(length ())", stdEnv).getValue());
        assertEquals(3, Runner.execute("(length (quote (1 2 3)))", stdEnv).getValue());
        assertEquals(3, Runner.execute("(length [1 2 3])", stdEnv).getValue());
        // list
        assertEquals(Arrays.asList(1, 2, 3), Runner.execute("(list 1 2 3)", stdEnv).getValue());
        assertEquals(Arrays.asList(1, 2, 6), Runner.execute("(list 1 2 (+ 1 2 3))", stdEnv).getValue());
        // map
        assertEquals(Arrays.asList(2, 3, 4),
        Runner.execute("(map (lambda (x) (+ x 1)) (list 1 2 3))", stdEnv).getValue());
        // nth
        assertEquals(1, Runner.execute("(nth 1 [0 1 2])", stdEnv).getValue());
        assertEquals(1, Runner.execute("(nth 1 (list 0 1 2))", stdEnv).getValue());
        // not
        assertFalse(Runner.execute("(not 1)", stdEnv).asBoolean());
        assertTrue(Runner.execute("(not false)", stdEnv).asBoolean());
        assertTrue(Runner.execute("(not null)", stdEnv).asBoolean());
        assertTrue(Runner.execute("(not ())", stdEnv).asBoolean());
        // and
        assertEquals(3, Runner.execute("(and 1 2 3)", stdEnv).getValue());
        assertEquals(false, Runner.execute("(and 1 false 3)", stdEnv).getValue());
        // or
        assertEquals(1, Runner.execute("(or 1 2 3)", stdEnv).getValue());
        assertEquals(3, Runner.execute("(or false false 3)", stdEnv).getValue());
    }

    @Test
    public void testBigNumbers() throws Exception {
        Environment stdEnv = Default.environment();
        stdEnv.put(Symbol.of("INT_MAX"), Util.expressionOf(Integer.MAX_VALUE));
        stdEnv.put(Symbol.of("INT_MIN"), Util.expressionOf(Integer.MIN_VALUE));
        stdEnv.put(Symbol.of("LONG_MAX"), Util.expressionOf(BigDecimal.valueOf(Long.MAX_VALUE)));
        stdEnv.put(Symbol.of("LONG_MIN"), Util.expressionOf(BigDecimal.valueOf(Long.MIN_VALUE)));
        stdEnv.put(Symbol.of("DOUBLE_MAX"), Util.expressionOf(Double.MAX_VALUE));
        stdEnv.put(Symbol.of("DOUBLE_MIN"), Util.expressionOf(Double.MIN_VALUE));
        BigDecimal intMaxPlusOne = BigDecimal.valueOf(Integer.MAX_VALUE).add(BigDecimal.ONE);
        assertEquals(intMaxPlusOne, Runner.execute("(+ INT_MAX 1)", stdEnv).getValue());
        BigDecimal intMinMinusOne = BigDecimal.valueOf(Integer.MIN_VALUE).subtract(BigDecimal.ONE);
        assertEquals(intMinMinusOne, Runner.execute("(- INT_MIN 1)", stdEnv).getValue());
        BigDecimal intMaxTimesTwo = BigDecimal.valueOf(Integer.MAX_VALUE).multiply(BigDecimal.valueOf(2));
        assertEquals(intMaxTimesTwo, Runner.execute("(* 2 INT_MAX)", stdEnv).getValue());
        BigDecimal intMinTimesTwo = BigDecimal.valueOf(Integer.MIN_VALUE).multiply(BigDecimal.valueOf(2));
        assertEquals(intMinTimesTwo, Runner.execute("(* INT_MIN 2)", stdEnv).getValue());
        BigDecimal intMaxDivHalf = BigDecimal.valueOf(Integer.MAX_VALUE).divide(BigDecimal.valueOf(0.5), RoundingMode.UP);
        assertEquals(intMaxDivHalf, Runner.execute("(/ INT_MAX 0.5)", stdEnv).getValue());
        BigDecimal intMinDivHalf = BigDecimal.valueOf(Integer.MIN_VALUE).divide(BigDecimal.valueOf(0.5), RoundingMode.UP);
        assertEquals(intMinDivHalf, Runner.execute("(/ INT_MIN 0.5)", stdEnv).getValue());
        BigDecimal longMaxPlusOne = BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE);
        assertEquals(longMaxPlusOne, Runner.execute("(+ LONG_MAX 1)", stdEnv).getValue());
        BigDecimal longMinMinusOne = BigDecimal.valueOf(Long.MIN_VALUE).subtract(BigDecimal.ONE);
        assertEquals(longMinMinusOne, Runner.execute("(- LONG_MIN 1)", stdEnv).getValue());
        BigDecimal longMaxTimesTwo = BigDecimal.valueOf(Long.MAX_VALUE).multiply(BigDecimal.valueOf(2));
        assertEquals(longMaxTimesTwo, Runner.execute("(* 2 LONG_MAX)", stdEnv).getValue());
        BigDecimal longMinTimesTwo = BigDecimal.valueOf(Long.MIN_VALUE).multiply(BigDecimal.valueOf(2));
        assertEquals(longMinTimesTwo, Runner.execute("(* LONG_MIN 2)", stdEnv).getValue());
        BigDecimal longMaxDivHalf = BigDecimal.valueOf(Long.MAX_VALUE).divide(BigDecimal.valueOf(0.5), RoundingMode.UP);
        assertEquals(longMaxDivHalf, Runner.execute("(/ LONG_MAX 0.5)", stdEnv).getValue());
        BigDecimal longMinDivHalf = BigDecimal.valueOf(Long.MIN_VALUE).divide(BigDecimal.valueOf(0.5), RoundingMode.UP);
        assertEquals(longMinDivHalf, Runner.execute("(/ LONG_MIN 0.5)", stdEnv).getValue());
        BigDecimal doubleMaxPlusOne = BigDecimal.valueOf(Double.MAX_VALUE).add(BigDecimal.ONE);
        assertEquals(doubleMaxPlusOne, Runner.execute("(+ DOUBLE_MAX 1)", stdEnv).getValue());
        BigDecimal doubleMaxTimesTwo = BigDecimal.valueOf(Double.MAX_VALUE).multiply(BigDecimal.valueOf(2));
        assertEquals(Util.reduceBigDecimal(doubleMaxTimesTwo), Runner.execute("(* 2 DOUBLE_MAX)", stdEnv).getValue());
        BigDecimal doubleMaxDivHalf = BigDecimal.valueOf(Double.MAX_VALUE).divide(BigDecimal.valueOf(0.5), RoundingMode.UP);
        assertEquals(Util.reduceBigDecimal(doubleMaxDivHalf), Runner.execute("(/ DOUBLE_MAX 0.5)", stdEnv).getValue());
        Runner.execute("(def fact (lambda (n) (if (<= n 1) 1 (* n (fact (- n 1))))))", stdEnv);
        assertEquals(new BigDecimal("93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000"),
        Runner.execute("(fact 100)", stdEnv).getValue());
    }

    @Test
    public void testWhitespace() throws Exception {
        Environment stdEnv = Default.environment();
        assertEquals(Arrays.asList(1, 2, 3), Runner.execute("(list 1 2 3 )", stdEnv).getValue());
        assertEquals(Arrays.asList(1, 2, 3), Runner.execute("( list 1 2 3)", stdEnv).getValue());
        assertEquals(Collections.emptyList(), Runner.execute("()", stdEnv).getValue());
        assertEquals(Collections.emptyList(), Runner.execute("( )", stdEnv).getValue());
        assertEquals(Collections.emptyList(), Runner.execute("(  )", stdEnv).getValue());
        assertArrayEquals(new int[] { 1, 2, 3 }, (int[]) Runner.execute("[1 2 3 ]", stdEnv).getValue());
        assertArrayEquals(new int[] { 1, 2, 3 }, (int[]) Runner.execute("[ 1 2 3]", stdEnv).getValue());
        assertArrayEquals(new Object[] {}, (Object[]) Runner.execute("[]", stdEnv).getValue());
        assertArrayEquals(new Object[] {}, (Object[]) Runner.execute("[ ]", stdEnv).getValue());
        assertArrayEquals(new Object[] {}, (Object[]) Runner.execute("[  ]", stdEnv).getValue());
    }

    @Test
    public void testFormat() throws Exception {
        Environment stdEnv = Default.environment();
        assertEquals("foo bar", Runner.execute("(format \"foo bar\")", stdEnv).getValue());
        assertEquals("foo 1 bar", Runner.execute("(format \"foo %d bar\" 1)", stdEnv).getValue());
        assertEquals("foo baz bar", Runner.execute("(format \"foo %s bar\" \"baz\")", stdEnv).getValue());
    }

    @Test
    public void testChinese() throws Exception {
        String code = "(定义 乘方 (函数             " +
		              "          (a n)            " +
                      "          (如果 (不大于 n 0) " +
		              "            1               " +
                      "            (乘积 a (乘方 a (相减 n 1)))" +
                      ")))                         " +
                      "(乘方 2 8)                   ";
        Environment stdEnv = Default.environment();
        assertEquals(256, Runner.execute(code, stdEnv).getValue());
        assertEquals(2, Runner.execute("(假设 ((x 1)) (求和 x 1))", stdEnv).getValue());
        assertEquals(10, Runner.execute("(定义 x 10) (假设 ((x 1)) (求和 x 1)) x", stdEnv).getValue(), "“假设”不会改变“定义”");
    }

    @Test
    public void testConcat() throws Exception {
        Environment stdEnv = Default.environment();
        assertEquals("123",
            Runner.execute("(concat 1 2 3)", stdEnv).getValue()
        );
        assertEquals("a2b",
            Runner.execute("(concat \"a\" 2 \"b\")", stdEnv).getValue()
        );
        assertEquals("\"2\"",
            Runner.execute("(concat \"\\\"\" 2 \"\\\"\")", stdEnv).getValue()
        );
    }

    @Test
    public void testMapReduceFilter() throws Exception {
        Environment stdEnv = Default.environment();
        assertEquals(Arrays.asList(2, 4, 6),
            Runner.execute("(map (lambda (x) (* x 2)) (list 1 2 3))", stdEnv).getValue()
        );
        assertEquals(15,
            Runner.execute("(reduce + (list 1 2 3 4 5))", stdEnv).getValue()
        );
        assertEquals("abc d",
            Runner.execute("(reduce concat (list 'a 'b 'c \" \" \"d\"))", stdEnv).getValue()
        );
        assertEquals(Arrays.asList(3, 4, 5),
            Runner.execute("(filter (lambda (x) (> x 2)) (list 1 2 3 4 5))", stdEnv).getValue()
        );
        assertEquals(Arrays.asList(1, 3, 5),
            Runner.execute("(filter (lambda (x i) (= (% i 2) 0)) (list 1 2 3 4 5))", stdEnv).getValue()
        );
    }

    @Test
    public void testContains() throws Exception {
        Environment stdEnv = Default.environment();
        assertTrue(Runner.execute("(contains (list 1 2 3) 2)", stdEnv).asBoolean());
        assertTrue(Runner.execute("(contains (list \"a\" \"b\") \"b\")", stdEnv).asBoolean());
        assertFalse(Runner.execute("(contains (list \"a\" \"b\") \"c\")", stdEnv).asBoolean());
        assertTrue(Runner.execute("(contains (list \"a\" 2 null 4) null)", stdEnv).asBoolean());
        assertTrue(Runner.execute("(contains [1 2 3] 2)", stdEnv).asBoolean());
        assertFalse(Runner.execute("(contains [1 2 3] 4)", stdEnv).asBoolean());
    }
}
