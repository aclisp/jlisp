package jlisp.engine.function.time;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

import java.time.LocalDate;

/**
 * Returns the day of the week for the given date, using 1 for Monday, 2 for Tuesday, through 7 for Sunday.
 */
public class Weekday extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String dateText = args.get(0).asText(Today.Null);
        LocalDate date = LocalDate.parse(dateText, Today.Format);
        return Expression.of(date.getDayOfWeek().getValue());
    }

}
