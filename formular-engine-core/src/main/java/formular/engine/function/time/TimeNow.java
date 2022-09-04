package formular.engine.function.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

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
        return Util.expressionOf(now);
    }

}
