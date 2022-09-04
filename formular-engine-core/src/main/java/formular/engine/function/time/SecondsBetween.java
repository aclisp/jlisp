package formular.engine.function.time;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class SecondsBetween extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String fromText = args.get(0).asText(Now.Null);
        String toText = args.get(1).asText(Now.Null);
        LocalDateTime from = LocalDateTime.parse(fromText, Now.Format);
        LocalDateTime to = LocalDateTime.parse(toText, Now.Format);
        int seconds = (int) ChronoUnit.SECONDS.between(from, to);
        return Util.expressionOf(seconds);
    }

}
