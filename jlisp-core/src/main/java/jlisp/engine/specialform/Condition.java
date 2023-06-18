package jlisp.engine.specialform;

import jlisp.engine.*;

import java.util.ArrayList;
import java.util.List;


public class Condition extends SpecialForm {

    @Override
    public Expression evaluate(List<Expression> args, Environment env, Debugger debugger, int depth) throws Exception {

        // A cond form has a number (possibly zero) of clauses, which are lists of forms.
        // Each clause consists of a test followed by zero or more consequents.

        List<ListExpression> clauses = new ArrayList<>(args.size());
        for (Expression arg : args) {
            ListExpression clause = (ListExpression) arg;
            if (clause.isEmpty()) {
                throw new IllegalArgumentException("Every clause of a cond must have a test");
            }
            clauses.add(clause);
        }

        // The first clause whose test evaluates to true is selected; all other clauses are ignored,
        // and the consequents of the selected clause are evaluated in order (as an implicit progn).

        // The cond special form returns the results of evaluating the last of the selected consequents;
        // if there were no consequents in the selected clause, then the single (and necessarily true)
        // value of the test is returned.

        for (ListExpression clause : clauses) {
            Expression test = clause.get(0);
            Expression result = Engine.evaluate(test, env, debugger, depth+1);
            if (result.asBoolean()) {
                if (clause.size() == 1) {
                    return result;
                }
                ListExpression consequents = new ListExpression(clause.subList(1, clause.size()));
                consequents.add(0, Symbol.of("progn"));
                return Engine.evaluate(consequents, env, debugger, depth+1);
            }
        }

        // If cond runs out of clauses (every test produced false, and therefore no clause was selected),
        // the value of the cond form is nil.

        return Expression.of(null);
    }

}
