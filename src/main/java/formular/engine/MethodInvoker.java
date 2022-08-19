package formular.engine;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodInvoker extends Function {
    private MethodInvoker() {}
    public static MethodInvoker of(Object object, Method method) {
        MethodInvoker function = new MethodInvoker();
        function.object = object;
        function.method = method;
        return function;
    }
    private Object object;
    private Method method;
    public Expression invoke(ListExpression args) throws Exception {
        Object[] jargs = new Object[args.size()];
        for (int i = 0; i < args.size(); i++) {
            jargs[i] = args.get(i).getValue();
        }
        return Util.expressionOf(method.invoke(object, jargs));
    }
    protected List<?> getParameterHelpNames() {
        List<Object> names = new ArrayList<>(method.getParameterTypes().length);
        for (Class<?> param : method.getParameterTypes()) {
            names.add(param.getSimpleName());
        }
        return names;
    }
}
