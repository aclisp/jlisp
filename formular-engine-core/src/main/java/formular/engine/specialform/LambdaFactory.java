package formular.engine.specialform;

import java.util.List;

import formular.engine.Environment;
import formular.engine.Expression;
import formular.engine.Lambda;
import formular.engine.ListExpression;
import formular.engine.SpecialForm;
import formular.engine.Symbol;

public class LambdaFactory extends SpecialForm {

    public Expression evaluate(List<Expression> args, Environment env) throws Exception {
        ListExpression params = (ListExpression) args.get(0);
        ListExpression body = new ListExpression(args.subList(1, args.size()));
        body.add(0, Symbol.of("progn"));
        return Lambda.of(params, body, env);
    }

}
