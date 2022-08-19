package formular.parser.json;

import formular.engine.Expression;

public class Symbol implements Node {
    private final String type = "symbol";
    private formular.engine.Symbol value;
    public Symbol(formular.engine.Symbol symbol) {
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
        this.value = formular.engine.Symbol.of(value);
    }
    public String getValue() {
        return value.getValue();
    }
}
