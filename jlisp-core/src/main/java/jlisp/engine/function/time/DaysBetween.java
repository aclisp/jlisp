package jlisp.engine.function.time;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class DaysBetween extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String fromText = args.get(0).asText(Today.Null);
        String toText = args.get(1).asText(Today.Null);
        LocalDate from = LocalDate.parse(fromText, Today.Format);
        LocalDate to = LocalDate.parse(toText, Today.Format);
        int days = (int) ChronoUnit.DAYS.between(from, to);
        return Expression.of(days);
    }

}
