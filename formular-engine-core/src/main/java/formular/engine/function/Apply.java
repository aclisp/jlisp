package formular.engine.function;

import formular.engine.Engine;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.MissingArgumentException;

public class Apply extends Function {

    public Expression invoke(ListExpression args) throws Exception {
        if (args.isEmpty()) {
            throw new MissingArgumentException("Need at least 1 argument");
        }
        ListExpression applyArgs = new ListExpression(args.subList(1, args.size()));
        if (applyArgs.isEmpty()) {
            return Engine.apply((Function) args.get(0), applyArgs);
        }
        Expression last = applyArgs.get(applyArgs.size() - 1);
        if (last instanceof ListExpression) {
            applyArgs.remove(applyArgs.size() - 1);
            applyArgs.addAll((ListExpression) last);
        }
        return Engine.apply((Function) args.get(0), applyArgs);
    }

}
