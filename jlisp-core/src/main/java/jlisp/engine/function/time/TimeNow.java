package jlisp.engine.function.time;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Returns a time value in GMT representing the current moment.
 * Use this function instead of the NOW function if you only want to track time, without a date.
 */
public class TimeNow extends Function {

    static final String Null = "00:00:00";
    static final DateTimeFormatter Format = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String now = LocalTime.now().format(Format);
        return Expression.of(now);
    }

}
