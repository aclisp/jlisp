package formular.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListExpression extends ArrayList<Expression> implements Expression {
    private int id = System.identityHashCode(this);
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
    public Expression get(int index) {
        try {
            return super.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new MissingArgumentException(missingArgMsg(index));
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    private String missingArgMsg(int index) {
        if (index < 0) {
            index = 0;
        }
        return "Missing the #"+(index+1)+" argument, Total provided "+this.size()+" arguments";
    }
}
