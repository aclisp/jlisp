package jlisp.parser.json;

import jlisp.engine.Expression;

public class JavaObject implements Node {
    final static String type = "object";
    private jlisp.engine.JavaObject value;
    public JavaObject(jlisp.engine.JavaObject o) {
        value = o;
    }
    public JavaObject() { }
    public String getType() {
        return type;
    }
    public Expression getExpression() {
        return value;
    }
    public void setValue(Object value) {
        this.value = jlisp.engine.JavaObject.of(value);
    }
    public Object getValue() {
        return value.getValue();
    }
    public void setId(int id) {
        value.setId(id);
    }
    public int getId() {
        return value.getId();
    }
}
