package formular.engine;

import java.util.Collections;
import java.util.List;

public abstract class Function implements Expression {
    public abstract Expression invoke(ListExpression args) throws Exception;
    protected List<?> getParameterHelpNames() {
        return Collections.emptyList();
    }
    public Object getValue() {
        return this;
    }
    public boolean asBoolean() {
        return true;
    }
    public String toString() {
        return Util.listToString("Function(", getParameterHelpNames(), ", ", ")");
    }
}
