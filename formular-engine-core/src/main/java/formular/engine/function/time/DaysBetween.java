package formular.engine.function.time;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class DaysBetween extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String fromText = (String) args.get(0).getValue();
        String toText = (String) args.get(1).getValue();
        LocalDate from = LocalDate.parse(fromText, Today.Format);
        LocalDate to = LocalDate.parse(toText, Today.Format);
        int days = (int) ChronoUnit.DAYS.between(from, to);
        return Util.expressionOf(days);
    }

}
