package formular.engine.function.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Returns a date/time representing the current moment.
 */
public class Now extends Function {

    static final DateTimeFormatter Format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String now = LocalDateTime.now().format(Format);
        return Util.expressionOf(now);
    }

}
