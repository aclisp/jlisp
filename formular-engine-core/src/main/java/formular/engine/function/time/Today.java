package formular.engine.function.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Returns the current date as a date data type.
 */
public class Today extends Function {

    static final DateTimeFormatter Format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String now = LocalDate.now().format(Format);
        return Util.expressionOf(now);
    }

}
