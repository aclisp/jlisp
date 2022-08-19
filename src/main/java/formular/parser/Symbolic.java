package formular.parser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import formular.engine.Array;
import formular.engine.Atom;
import formular.engine.Default;
import formular.engine.Engine;
import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.JavaObject;
import formular.engine.ListExpression;
import formular.engine.Symbol;
import formular.formatter.Formatter;

public class Symbolic {

    public static Expression parse(String input) {
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
                tokens.subList(0, 2).clear();
            } else {
                return token;
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
        String program = new String(encoded, StandardCharsets.UTF_8).trim();
        Expression expr = Symbolic.parse(program);
        Formatter fmt = new Formatter();
        System.out.println(fmt.format(program));

        Engine engine = new Engine();
        Environment env = Default.environment();
        System.out.println(engine.evaluate(expr, env).toString());
    }
}
