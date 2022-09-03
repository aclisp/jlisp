package formular.engine.function.time;

import java.time.LocalDate;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class AddDay extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String dateText = (String) args.get(0).getValue();
        int num = ((Number) args.get(1).getValue()).intValue();
        LocalDate date = LocalDate.parse(dateText, Today.Format);
        String result = date.plusDays(num).format(Today.Format);
        return Util.expressionOf(result);
    }

}
