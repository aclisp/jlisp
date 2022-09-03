package formular.engine.function.time;

import java.time.LocalDateTime;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class AddMinute extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String datetimeText = (String) args.get(0).getValue();
        int num = ((Number) args.get(1).getValue()).intValue();
        LocalDateTime datetime = LocalDateTime.parse(datetimeText, Now.Format);
        String result = datetime.plusMinutes(num).format(Now.Format);
        return Util.expressionOf(result);
    }

}
