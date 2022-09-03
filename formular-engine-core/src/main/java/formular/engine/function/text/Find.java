package formular.engine.function.text;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

/**
 * Returns the position of a string within a string of text represented as a number.
 * The first character in a string is designated as one rather than zero.
 * If your search doesn't return any results, a 0 displays in the field.
 */
public class Find extends Function {

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String searchText = (String) args.get(0).getValue();
        String text = (String) args.get(1).getValue();
        int startNum = 1;
        if (args.size() > 2) {
            startNum = ((java.lang.Number) args.get(2).getValue()).intValue();
        }
        int indexOf = text.indexOf(searchText, startNum-1);
        return Util.expressionOf(indexOf+1);
    }

}
