package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.JavaObject;
import formular.engine.ListExpression;

/**
 * Converts a text string to a number.
 */
public class Number extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Expression expr = args.get(0);
        Object value = expr.getValue();
        if (value instanceof java.lang.Number) {
            return expr;
        }

        String text = (String) value;
        try {
            return JavaObject.of(Integer.parseInt(text));
        } catch (NumberFormatException ex) {
            // Not an int
        }
        try {
            return JavaObject.of(Double.parseDouble(text));
        } catch (NumberFormatException ex) {
            // Not a double
        }
        throw new IllegalArgumentException("Not a number: " + text);
    }

}
