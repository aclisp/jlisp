package formular.engine.function.time;

import java.time.LocalDateTime;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class AddMinute extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String datetimeText = args.get(0).asText(Now.Null);
        int num = args.get(1).asNumber(0).intValue();
        LocalDateTime datetime = LocalDateTime.parse(datetimeText, Now.Format);
        String result = datetime.plusMinutes(num).format(Now.Format);
        return Util.expressionOf(result);
    }

}
