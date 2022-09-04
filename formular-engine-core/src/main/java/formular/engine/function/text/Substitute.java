package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Substitutes new text for old text in a text string.
 */
public class Substitute extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        String oldText = args.get(1).asText("");
        String newText = args.get(2).asText("");
        String result = text.replace(oldText, newText);
        return Util.expressionOf(result);
    }

}
