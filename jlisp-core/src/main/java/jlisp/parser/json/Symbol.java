package jlisp.parser.json;

import jlisp.engine.Expression;

public class Symbol implements Node {
    final static String type = "symbol";
    private jlisp.engine.Symbol value;
    public Symbol(jlisp.engine.Symbol symbol) {
        value = symbol;
    }
    public Symbol() { }
    public String getType() {
        return type;
    }
    public Expression getExpression() {
        return value;
    }
    public void setValue(String value) {
        this.value = jlisp.engine.Symbol.of(value);
    }
    public String getValue() {
        return value.getValue();
    }
    public void setId(int id) {
        value.setId(id);
    }
    public int getId() {
        return value.getId();
    }
}
