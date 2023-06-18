package jlisp.engine.function.time;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class SecondsBetween extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String fromText = args.get(0).asText(Now.Null);
        String toText = args.get(1).asText(Now.Null);
        LocalDateTime from = LocalDateTime.parse(fromText, Now.Format);
        LocalDateTime to = LocalDateTime.parse(toText, Now.Format);
        int seconds = (int) ChronoUnit.SECONDS.between(from, to);
        return Expression.of(seconds);
    }

}
