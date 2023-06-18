package jlisp.engine.function.time;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;

import java.time.LocalDateTime;

public class AddMinute extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String datetimeText = args.get(0).asText(Now.Null);
        int num = args.get(1).asNumber(0).intValue();
        LocalDateTime datetime = LocalDateTime.parse(datetimeText, Now.Format);
        String result = datetime.plusMinutes(num).format(Now.Format);
        return Expression.of(result);
    }

}
