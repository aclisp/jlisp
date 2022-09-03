package formular.engine.function.time;

import java.time.LocalDate;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Returns the day of the week for the given date, using 1 for Monday, 2 for Tuesday, through 7 for Sunday.
 */
public class Weekday extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String dateText = (String) args.get(0).getValue();
        LocalDate date = LocalDate.parse(dateText, Today.Format);
        return Util.expressionOf(date.getDayOfWeek().getValue());
    }

}
