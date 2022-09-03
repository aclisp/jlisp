package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Converts all letters in the specified text string to lowercase.
 * Any characters that aren’t letters are unaffected by this function.
 */
public class Lower extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = (String) args.get(0).getValue();
        String result = text.toLowerCase();
        return Util.expressionOf(result);
    }

}
