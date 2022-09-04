package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Determines if text begins with specific characters and returns TRUE if it does. Returns FALSE if it doesn't.
 */
public class Begins extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String s = args.get(0).asText("");
        String prefix = args.get(1).asText("");
        return Util.expressionOf(s.startsWith(prefix));
    }

}
