package jlisp.engine.function.time;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Returns the current date as a date data type.
 */
public class Today extends Function {

    static final String Null = "2001-01-01";
    static final DateTimeFormatter Format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String now = LocalDate.now().format(Format);
        return Expression.of(now);
    }

}
