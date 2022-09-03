package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Removes the spaces and tabs from the beginning and end of a text string.
 */
public class Trim extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = (String) args.get(0).getValue();
        String result = text.trim();
        return Util.expressionOf(result);
    }

}
