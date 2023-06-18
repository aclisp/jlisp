package jlisp.parser.json;

import jlisp.engine.Expression;

import java.util.List;

public class Array implements Node {
    final static String type = "array";
    private jlisp.engine.Array value;
    public Array(jlisp.engine.Array v) {
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
        value = jlisp.engine.Array.from(values);
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
