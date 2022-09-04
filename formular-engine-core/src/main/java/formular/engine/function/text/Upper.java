package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Converts all letters in the specified text string to uppercase.
 * Any characters that aren’t letters are unaffected by this function.
 */
public class Upper extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        String result = text.toUpperCase();
        return Util.expressionOf(result);
    }

}
