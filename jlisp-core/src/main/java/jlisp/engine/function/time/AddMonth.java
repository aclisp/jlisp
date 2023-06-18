package jlisp.engine.function.time;

import java.time.LocalDate;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

/**
 * Returns the date that is the indicated number of months before or after a specified date.
 * If the specified date is the last day of the month, the resulting date is the last day
 * of the resulting month. Otherwise, the result has the same date component as the specified date.
 */
public class AddMonth extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String dateText = args.get(0).asText(Today.Null);
        int num = args.get(1).asNumber(0).intValue();
        LocalDate date = LocalDate.parse(dateText, Today.Format);
        String result = date.plusMonths(num).format(Today.Format);
        return Expression.of(result);
    }

}
