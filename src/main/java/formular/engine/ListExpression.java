package formular.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListExpression extends ArrayList<Expression> implements Expression {
    public static ListExpression of (Collection<?> items) {
        ListExpression list = new ListExpression(items.size());
        for (Object item : items) {
            list.add(Util.expressionOf(item));
        }
        return list;
    }
    public ListExpression() {
        super();
    }
    public ListExpression(int initialCapacity) {
        super(initialCapacity);
    }
    public ListExpression(List<Expression> list) {
        super(list);
    }
    public boolean asBoolean() {
        return !isEmpty();
    }
    public List<Object> getValue() {
        List<Object> result = new ArrayList<>(this.size());
        for (Expression expression : this) {
            result.add(expression.getValue());
        }
        return result;
    }
    public String toString() {
        return Util.listToString("(", this, " ", ")");
    }
}
