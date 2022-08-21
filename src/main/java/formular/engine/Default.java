package formular.engine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;

public class Default {
    public static Environment environment() {
        final Environment environment = new Environment();
        environment.put(Symbol.of("+"), new Function() {
            public Expression invoke(ListExpression args) {
                BigDecimal result = BigDecimal.ZERO;
                for (Expression arg : args) {
                    Number value = (Number) arg.getValue();
                    result = result.add(Util.toBigDecimal(value));
                }
                return Util.expressionOf(Util.reduceBigDecimal(result));
            }
        });
        environment.put(Symbol.of("-"), new Function() {
            public Expression invoke(ListExpression args) {
                BigDecimal result = Util.toBigDecimal((Number) args.get(0).getValue());
                for (Expression arg : args.subList(1, args.size())) {
                    Number value = (Number) arg.getValue();
                    result = result.subtract(Util.toBigDecimal(value));
                }
                return Util.expressionOf(Util.reduceBigDecimal(result));
            }
        });
        environment.put(Symbol.of("*"), new Function() {
            public Expression invoke(ListExpression args) {
                BigDecimal result = BigDecimal.ONE;
                for (Expression arg : args) {
                    Number value = (Number) arg.getValue();
                    result = result.multiply(Util.toBigDecimal(value));
                }
                return Util.expressionOf(Util.reduceBigDecimal(result));
            }
        });
        environment.put(Symbol.of("/"), new Function() {
            public Expression invoke(ListExpression args) {
                BigDecimal result = Util.toBigDecimal((Number) args.get(0).getValue());
                for (Expression arg : args.subList(1, args.size())) {
                    Number value = (Number) arg.getValue();
                    result = result.divide(Util.toBigDecimal(value), 16, RoundingMode.UP);
                }
                return Util.expressionOf(Util.reduceBigDecimal(result));
            }
        });
        environment.put(Symbol.of("<"), new Function() {
            public Expression invoke(ListExpression args) {
                double first = ((Number) args.get(0).getValue()).doubleValue();
                for (Expression arg : args.subList(1, args.size())) {
                    if (first >= ((Number) arg.getValue()).doubleValue()) {
                        return Util.expressionOf(false);
                    }
                }
                return Util.expressionOf(true);
            }
        });
        environment.put(Symbol.of(">"), new Function() {
            public Expression invoke(ListExpression args) {
                double first = ((Number) args.get(0).getValue()).doubleValue();
                for (Expression arg : args.subList(1, args.size())) {
                    if (first <= ((Number) arg.getValue()).doubleValue()) {
                        return Util.expressionOf(false);
                    }
                }
                return Util.expressionOf(true);
            }
        });
        environment.put(Symbol.of("<="), new Function() {
            public Expression invoke(ListExpression args) {
                double first = ((Number) args.get(0).getValue()).doubleValue();
                for (Expression arg : args.subList(1, args.size())) {
                    if (first > ((Number) arg.getValue()).doubleValue()) {
                        return Util.expressionOf(false);
                    }
                }
                return Util.expressionOf(true);
            }
        });
        environment.put(Symbol.of(">="), new Function() {
            public Expression invoke(ListExpression args) {
                double first = ((Number) args.get(0).getValue()).doubleValue();
                for (Expression arg : args.subList(1, args.size())) {
                    if (first < ((Number) arg.getValue()).doubleValue()) {
                        return Util.expressionOf(false);
                    }
                }
                return Util.expressionOf(true);
            }
        });
        environment.put(Symbol.of("is"), new Function() {
            public Expression invoke(ListExpression args) {
                Object arg1 = args.get(0).getValue();
                Object arg2 = args.get(1).getValue();
                return Util.expressionOf(arg1 == arg2);
            }
        });
        environment.put(Symbol.of("eq"), new Function() {
            public Expression invoke(ListExpression args) {
                Object arg1 = args.get(0).getValue();
                Object arg2 = args.get(1).getValue();
                return Util.expressionOf(arg1 == arg2 || arg1 != null && arg1.equals(arg2));
            }
        });
        environment.put(Symbol.of("car"), new Function() {
            public Expression invoke(ListExpression args) {
                ListExpression arg = (ListExpression) args.get(0);
                return arg.get(0);
            }
        });
        environment.put(Symbol.of("cdr"), new Function() {
            public Expression invoke(ListExpression args) {
                ListExpression arg = (ListExpression) args.get(0);
                return new ListExpression(arg.subList(1, arg.size()));
            }
        });
        environment.put(Symbol.of("cons"), new Function() {
            public Expression invoke(ListExpression args) {
                ListExpression result = new ListExpression();
                result.add(args.get(0));
                Expression rest = args.get(1);
                if (rest instanceof ListExpression) {
                    result.addAll((ListExpression) rest);
                } else {
                    result.add(rest);
                }
                return result;
            }
        });
        environment.put(Symbol.of("length"), new Function() {
            public Expression invoke(ListExpression args) {
                Expression listOrArray = args.get(0);
                if (listOrArray instanceof Array) {
                    return Util.expressionOf(((Array) listOrArray).length());
                } else {
                    return Util.expressionOf(((ListExpression) listOrArray).size());
                }
            }
        });
        environment.put(Symbol.of("list"), new Function() {
            public Expression invoke(ListExpression args) {
                return args;
            }
        });
        environment.put(Symbol.of("map"), new Function() {
            public Expression invoke(ListExpression args) throws Exception {
                Function function = (Function) args.get(0);
                ListExpression list = (ListExpression) args.get(1);
                ListExpression result = new ListExpression(list.size());
                for (Expression arg : list) {
                    result.add(function.invoke(new ListExpression(Collections.singletonList(arg))));
                }
                return result;
            }
        });
        environment.put(Symbol.of("nth"), new Function() {
            public Expression invoke(ListExpression args) {
                int n = (Integer) args.get(0).getValue();
                Expression listOrArray = args.get(1);
                if (listOrArray instanceof Array) {
                    return Util.expressionOf(((Array) listOrArray).get(n));
                } else {
                    return ((ListExpression) listOrArray).get(n);
                }
            }
        });
        environment.put(Symbol.of("eval"), new Function() {
            public Expression invoke(ListExpression args) throws Exception {
                return Engine.evaluate(args.get(0), environment);
            }
        });
        environment.put(Symbol.of("apply"), new Function() {
            public Expression invoke(ListExpression args) throws Exception {
                ListExpression applyArgs = new ListExpression(args.subList(1, args.size()));
                Expression last = applyArgs.get(applyArgs.size() - 1);
                if (last instanceof ListExpression) {
                    applyArgs.remove(applyArgs.size() - 1);
                    applyArgs.addAll((ListExpression) last);
                }
                return Engine.apply((Function) args.get(0), applyArgs);
            }
        });
        environment.put(Symbol.of("format"), new Function() {
            public Expression invoke(ListExpression args) throws Exception {
                String fmt = (String) args.get(0).getValue();
                Object[] fmtArgs = new Object[args.size() - 1];
                for (int i = 1; i < args.size(); i++) {
                    fmtArgs[i - 1] = args.get(i).getValue();
                }
                return Util.expressionOf(String.format(fmt, fmtArgs));
            }
        });
        environment.alias(Symbol.of("eq"), Symbol.of("="));
        environment.alias(Symbol.of("eq"), Symbol.of("=="));
        environment.alias(Symbol.of("eq"), Symbol.of("equals"));
        environment.alias(Symbol.of("eq"), Symbol.of("相等"));
        environment.alias(Symbol.of("car"), Symbol.of("head"));
        environment.alias(Symbol.of("cdr"), Symbol.of("tail"));
        environment.alias(Symbol.of("+"), Symbol.of("add"));
        environment.alias(Symbol.of("+"), Symbol.of("求和"));
        environment.alias(Symbol.of("-"), Symbol.of("minus"));
        environment.alias(Symbol.of("-"), Symbol.of("相减"));
        environment.alias(Symbol.of("*"), Symbol.of("multiply"));
        environment.alias(Symbol.of("*"), Symbol.of("乘积"));
        environment.alias(Symbol.of("/"), Symbol.of("divide"));
        environment.alias(Symbol.of("/"), Symbol.of("相除"));
        environment.alias(Symbol.of("<"), Symbol.of("less-than"));
        environment.alias(Symbol.of("<"), Symbol.of("小于"));
        environment.alias(Symbol.of(">"), Symbol.of("greater-than"));
        environment.alias(Symbol.of(">"), Symbol.of("大于"));
        environment.alias(Symbol.of("<="), Symbol.of("less-than-or-equal-to"));
        environment.alias(Symbol.of("<="), Symbol.of("小于或等于"));
        environment.alias(Symbol.of("<="), Symbol.of("not-greater-than"));
        environment.alias(Symbol.of("<="), Symbol.of("不大于"));
        environment.alias(Symbol.of(">="), Symbol.of("greater-than-or-equal-to"));
        environment.alias(Symbol.of(">="), Symbol.of("大于或等于"));
        environment.alias(Symbol.of(">="), Symbol.of("not-less-than"));
        environment.alias(Symbol.of(">="), Symbol.of("不小于"));
        return environment;
    }
}
