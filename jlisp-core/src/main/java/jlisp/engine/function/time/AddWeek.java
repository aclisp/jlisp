package jlisp.engine.function.time;

import java.time.LocalDate;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class AddWeek extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String dateText = args.get(0).asText(Today.Null);
        int num = args.get(1).asNumber(0).intValue();
        LocalDate date = LocalDate.parse(dateText, Today.Format);
        String result = date.plusWeeks(num).format(Today.Format);
        return Expression.of(result);
    }

}
