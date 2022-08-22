package formular.parser.json;

import formular.engine.Expression;

public class JavaObject implements Node {
    final static String type = "object";
    private formular.engine.JavaObject value;
    public JavaObject(formular.engine.JavaObject o) {
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
        this.value = formular.engine.JavaObject.of(value);
    }
    public Object getValue() {
        return value.getValue();
    }
    public void setType(String type) {}
}
