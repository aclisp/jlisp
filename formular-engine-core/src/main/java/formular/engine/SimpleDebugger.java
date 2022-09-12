package formular.engine;

import java.util.ArrayList;
import java.util.List;

public class SimpleDebugger implements Debugger {

    public static class EvaluationStep {
        public int id;
        public int depth;
        public String form;
        public String value;
        public String type;
        public long us;
    }

    private final List<EvaluationStep> steps = new ArrayList<>();
    private boolean exceptionCaught = false;

    public List<EvaluationStep> getSteps() {
        return steps;
    }

    @Override
    public void expressionEvaluated(Expression before, Expression after, int depth, long nanoDuration) {
        if (exceptionCaught) {
            return;
        }
        if (!(before instanceof ListExpression)) {
            return;
        }
        ListExpression expr = (ListExpression) before;
        if (expr.isEmpty()) {
            return;
        }
        Expression first = expr.get(0);
        if (depth > 0 && first.equals(Symbol.of("progn"))) {
            return;
        }
        EvaluationStep step = new EvaluationStep();
        step.id = expr.getId();
        step.depth = depth;
        step.form = getStepForm(expr, first);
        step.value = getStringValueWithThreshold(after);
        step.type = getExpressionType(after);
        step.us = nanoDuration/1000;
        steps.add(step);
    }

    @Override
    public void exceptionCaught(Expression before, Throwable exception, int depth, long nanoDuration) {
        if (exceptionCaught) {
            return;
        }
        exceptionCaught = true;
        EvaluationStep step = new EvaluationStep();
        step.id = before.getId();
        step.depth = depth;
        step.form = getStringValueWithThreshold(before);
        step.value = getStringValueWithThreshold(exception);
        step.type = "Exception";
        step.us = nanoDuration/1000;
        steps.add(step);
    }

    private static String getStepForm(ListExpression expr, Expression first) {
        if (first.equals(Symbol.of("def")) || first.equals(Symbol.of("apply")) || first.equals(Symbol.of("定义"))) {
            return first.toString() + " " + expr.get(1);
        }
        return first.toString();
    }

    private static String getStringValueWithThreshold(Object o) {
        String s = String.valueOf(o);
        final int threshold = 100;
        if (s.length() <= threshold) {
            return s;
        }
        return s.substring(0, threshold) + "...(omit)";
    }

    private static String getExpressionType(Expression after) {
        Object value = after.getValue();
        if (value == null) {
            return "Java `null`";
        }
        return value.getClass().getSimpleName();
    }
}
