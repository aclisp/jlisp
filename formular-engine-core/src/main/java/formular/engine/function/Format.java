package formular.engine.function;

import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Format extends Function {

    public Expression invoke(ListExpression args) throws Exception {
        String fmt = args.get(0).asText("");
        Object[] fmtArgs = new Object[args.size() - 1];
        for (int i = 1; i < args.size(); i++) {
            fmtArgs[i - 1] = args.get(i).getValue();
        }
        return Util.expressionOf(String.format(fmt, fmtArgs));
    }

}
