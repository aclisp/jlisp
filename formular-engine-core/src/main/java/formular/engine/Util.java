package formular.engine;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {
    public static String escapeString(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '\\' || c == '"') {
                result.append('\\');
            }
            result.append(c);
        }
        return result.toString();
    }
    public static Atom<?> expressionOf(Object value) {
        if (value == null) {
            return JavaObject.of(null);
        } else if (value.getClass().isArray()) {
            return Array.of(value);
        } else {
            return JavaObject.of(value);
        }
    }
    static String listToString(String prefix, Iterable<?> items, String delimiter, String suffix) {
        StringBuilder builder = new StringBuilder(prefix);
        for (Object item : items) {
            builder.append(item).append(delimiter);
        }
        if (builder.substring(builder.length() - delimiter.length()).equals(delimiter)) {
            builder.delete(builder.length() - delimiter.length(), builder.length());
        }
        builder.append(suffix);
        return builder.toString();
    }
    public static BigDecimal toBigDecimal(Number value) {
        if (value instanceof Double) {
            return BigDecimal.valueOf(value.doubleValue());
        } else if (value instanceof Integer) {
            return BigDecimal.valueOf(value.intValue());
        } else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else {
            throw new IllegalArgumentException("Unsupported number type: " + value.getClass());
        }
    }
    public static Number reduceBigDecimal(BigDecimal value) {
        if (value.signum() == 0 || value.scale() <= 0 || value.stripTrailingZeros().scale() <= 0) {
            try {
                return value.intValueExact();
            } catch (ArithmeticException ex) {
                // Doesn't fit in an int, but is an integer
                return value.setScale(0, RoundingMode.UNNECESSARY);
            }
        } else {
            double dbl = value.doubleValue();
            if (!Double.isInfinite(dbl)) {
                return dbl;
            } else {
                return value.stripTrailingZeros();
            }
        }
    }
}
