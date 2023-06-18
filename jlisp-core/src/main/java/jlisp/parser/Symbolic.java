package jlisp.parser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import jlisp.engine.Array;
import jlisp.engine.Atom;
import jlisp.engine.Default;
import jlisp.engine.Engine;
import jlisp.engine.Environment;
import jlisp.engine.Expression;
import jlisp.engine.JavaObject;
import jlisp.engine.ListExpression;
import jlisp.engine.Symbol;
import jlisp.formatter.Formatter;

public class Symbolic {

    private final static Formatter fmt = new Formatter();

    public static Expression parse(String input) {
        input = input.trim();
        ArrayList<String> tokens = tokenize(input);
        Expression expression = readTokens(tokens);
        if (tokens.isEmpty()) {
            return expression;
        } else {
            ListExpression result = new ListExpression();
            result.add(Symbol.of("progn"));
            result.add(expression);
            while (!tokens.isEmpty()) {
                result.add(readTokens(tokens));
            }
            return result;
        }
    }

    public static String format(Expression expr, boolean pretty) {
        StringBuilder oneline = _format(expr);
        oneline.delete(oneline.length()-1, oneline.length()); // remove the last space
        if (!pretty) {
            return oneline.toString();
        }
        return fmt.format(oneline.toString());
    }

    private static StringBuilder _format(Expression expr) {
        StringBuilder b = new StringBuilder();
        if (expr instanceof Symbol) {
            b.append(expr);
            b.append(" ");
        } else if (expr instanceof Array) {
            b.append(expr);
            b.append(" ");
        } else if (expr instanceof JavaObject) {
            b.append(expr);
            b.append(" ");
        } else if (expr instanceof ListExpression) {
            b.append("(");
            ListExpression expression = (ListExpression) expr;
            for (Expression exp : expression) {
                b.append(_format(exp));
            }
            if (expression.isEmpty()) {
                b.append(")");
            } else {
                b.setCharAt(b.length()-1, ')');
            }
            b.append(" ");
        } else {
            throw new IllegalArgumentException("Unsupported expression: " + expr);
        }
        return b;
    }

    static Expression readTokens(ArrayList<String> tokens) {
        String token = popToNext(tokens);
        tokens.remove(0);
        if ("(".equals(token)) {
            ListExpression expression = new ListExpression();
            while (!")".equals(popToNext(tokens))) {
                expression.add(readTokens(tokens));
            }
            tokens.remove(0);
            return expression;
        } else if ("[".equals(token)) {
            List<Object> values = new ArrayList<>();
            while (!"]".equals(popToNext(tokens))) {
                // Arrays can only contain atoms
                Atom<?> atom = (Atom<?>) readTokens(tokens);
                values.add(atom.getValue());
            }
            tokens.remove(0);
            return Array.from(values);
        } else if ("\"".equals(token)) {
            String string = tokens.remove(0);
            tokens.remove(0);
            return JavaObject.of(string);
        } else if ("'".equals(token)) {
            ListExpression expression = new ListExpression();
            expression.add(atomize("quote"));
            expression.add(readTokens(tokens));
            return expression;
        } else {
            return atomize(token);
        }
    }

    public static ArrayList<String> tokenize(String input) {
        ArrayList<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        boolean inString = false;
        boolean inComment = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (inString) {
                if (c == '"') {
                    inString = false;
                    tokens.add(token.toString());
                    token = new StringBuilder();
                    tokens.add(String.valueOf(c));
                } else if (c == '\\') {
                    token.append(input.charAt(++i));
                } else {
                    token.append(c);
                }
            } else if (inComment) {
                if (c == '\n' || i == input.length() - 1) {
                    inComment = false;
                    token.append(c);
                    tokens.add(token.toString());
                    token = new StringBuilder();
                } else {
                    token.append(c);
                }
            } else {
                if (isBreakingChar(c)) {
                    if (token.length() > 0) {
                        tokens.add(token.toString());
                        token = new StringBuilder();
                    }
                    tokens.add(String.valueOf(c));
                    inString = c == '"';
                    inComment = c == ';';
                } else {
                    token.append(c);
                }
            }
        }
        if (token.length() > 0) {
            tokens.add(token.toString());
        }
        return tokens;
    }

    private static boolean isBreakingChar(char c) {
        return c == '(' || c == ')' || c == '[' || c == ']' || c == '\'' || c == '"' || c == ';' || Character.isWhitespace(c);
    }

    private static String popToNext(ArrayList<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("End of token list");
        }
        while (true) {
            String token = tokens.get(0);
            if (token.trim().isEmpty()) {
                tokens.remove(0);
            } else if (";".equals(token)) {
                if (tokens.size() == 1) {
                    tokens.subList(0, 1).clear();
                } else {
                    tokens.subList(0, 2).clear();
                }
            } else {
                return token;
            }
            if (tokens.isEmpty()) {
                throw new IllegalArgumentException("Input can not end with whitespaces or comments");
            }
        }
    }

    private static Expression atomize(String token) {
        try {
            return JavaObject.of(Integer.parseInt(token));
        } catch (NumberFormatException ex) {
            // Not an int
        }
        try {
            return JavaObject.of(Double.parseDouble(token));
        } catch (NumberFormatException ex) {
            // Not a double
        }
        if ("null".equals(token)) {
            return JavaObject.of(null);
        } else if ("true".equals(token)) {
            return JavaObject.of(true);
        } else if ("false".equals(token)) {
            return JavaObject.of(false);
        } else {
            return Symbol.of(token);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] encoded = Files.readAllBytes(Paths.get("a.lisp"));
        String program = new String(encoded, StandardCharsets.UTF_8);
        Expression expr = Symbolic.parse(program);
        Formatter fmt = new Formatter();
        System.out.println(fmt.format(program));
        System.out.println();
        System.out.println(Symbolic.format(expr, false));

        Environment env = Default.environment();
        System.out.println(Engine.evaluate(expr, env).toString());
    }
}
