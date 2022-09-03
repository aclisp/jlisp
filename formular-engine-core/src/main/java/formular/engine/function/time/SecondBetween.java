package formular.engine.function.time;

import java.time.LocalDateTime;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class SecondBetween extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String fromText = (String) args.get(0).getValue();
        String toText = (String) args.get(1).getValue();
        LocalDateTime from = LocalDateTime.parse(fromText, Now.Format);
        LocalDateTime to = LocalDateTime.parse(toText, Now.Format);
        int second = (int) java.time.Duration.between(from, to).getSeconds();
        return Util.expressionOf(second);
    }

}
