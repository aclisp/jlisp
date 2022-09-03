package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Returns the specified number of characters from the end of a text string.
 */
public class Right extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = (String) args.get(0).getValue();
        int numChars = ((java.lang.Number) args.get(1).getValue()).intValue();
        String right = text.substring(text.length()-numChars, text.length());
        return Util.expressionOf(right);
    }

}
