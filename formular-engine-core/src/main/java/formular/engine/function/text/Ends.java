package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Determines if text ends with specific characters and returns TRUE if it does. Returns FALSE if it doesn't.
 */
public class Ends extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String s = (String) args.get(0).getValue();
        String suffix = (String) args.get(1).getValue();
        return Util.expressionOf(s.endsWith(suffix));
    }

}
