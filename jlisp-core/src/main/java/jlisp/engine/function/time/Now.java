package jlisp.engine.function.time;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Returns a date/time representing the current moment.
 */
public class Now extends Function {

    static final String Null = "2001-01-01 00:00:00";
    static final DateTimeFormatter Format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String now = LocalDateTime.now().format(Format);
        return Expression.of(now);
    }

}
