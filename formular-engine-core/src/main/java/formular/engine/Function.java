package formular.engine;

import java.util.Collections;
import java.util.List;

public abstract class Function implements Expression {
    private int id = System.identityHashCode(this);
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
