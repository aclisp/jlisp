package formular.parser.json;

import java.util.List;

import formular.engine.Expression;

public class Array implements Node {
    final static String type = "array";
    private formular.engine.Array value;
    public Array(formular.engine.Array v) {
        value = v;
    }
    public Array() { }
    public String getType() {
        return type;
    }
    public Expression getExpression() {
        return value;
    }
    public void setValue(List<Object> values) {
        value = formular.engine.Array.from(values);
    }
    public List<Object> getValue() {
        return value.toList();
    }
    public void setId(int id) {
        value.setId(id);
    }
    public int getId() {
        return value.getId();
    }
}
