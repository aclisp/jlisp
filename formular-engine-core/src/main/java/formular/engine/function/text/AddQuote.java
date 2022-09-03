package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Add the double quotation marks around the text.
 */
public class AddQuote extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String s = (String) args.get(0).getValue();
        return Util.expressionOf("\"" + s + "\"");
    }

}
