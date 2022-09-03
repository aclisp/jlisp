package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Returns the specified number of characters from the middle of a text string given the starting position.
 */
public class Mid extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = (String) args.get(0).getValue();
        int startNum = ((java.lang.Number) args.get(1).getValue()).intValue();
        int numChars = ((java.lang.Number) args.get(2).getValue()).intValue();
        String mid = text.substring(startNum-1, startNum-1+numChars);
        return Util.expressionOf(mid);
    }

}
