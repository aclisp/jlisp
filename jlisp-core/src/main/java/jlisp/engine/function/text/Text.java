package jlisp.engine.function.text;

import jlisp.engine.*;

import java.math.BigDecimal;

/**
 * Converts a percent, number, date, date/time, or currency type field into text anywhere formulas are used.
 */
public class Text extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        Expression expr = args.get(0);
        Object value = expr.getValue();
        if (value instanceof java.lang.String) {
            return expr;
        }
        if (value instanceof java.lang.Number) {
            BigDecimal number = Util.toBigDecimal((java.lang.Number) value);
            return JavaObject.of(number.toPlainString());
        }
        return JavaObject.of(String.valueOf(value));
    }

}
