package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Returns the specified number of characters from the beginning of a text string.
 */
public class Left extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        int numChars = args.get(1).asNumber(0).intValue();
        String left = text.substring(0, numChars);
        return Util.expressionOf(left);
    }

}
