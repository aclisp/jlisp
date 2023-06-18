package jlisp.engine.function.time;

import java.time.LocalDateTime;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class AddSecond extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String datetimeText = args.get(0).asText(Now.Null);
        int num = args.get(1).asNumber(0).intValue();
        LocalDateTime datetime = LocalDateTime.parse(datetimeText, Now.Format);
        String result = datetime.plusSeconds(num).format(Now.Format);
        return Expression.of(result);
    }

}