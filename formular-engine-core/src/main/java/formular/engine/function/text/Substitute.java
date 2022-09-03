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
        String text = (String) args.get(0).getValue();
        String oldText = (String) args.get(1).getValue();
        String newText = (String) args.get(2).getValue();
        String result = text.replace(oldText, newText);
        return Util.expressionOf(result);
    }

}
