package formular.parser.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import formular.engine.Expression;

@JsonDeserialize(using = NodeDeserializer.class)
public interface Node {
    // Actually it's the node's expression's id.
    int getId();
    String getType();
    @JsonIgnore Expression getExpression();
    static Node of(Expression expr) {
        if (expr instanceof formular.engine.Symbol) {
            return new Symbol((formular.engine.Symbol)expr);
        } else if (expr instanceof formular.engine.Array) {
            return new Array((formular.engine.Array)expr);
        } else if (expr instanceof formular.engine.JavaObject) {
            return new JavaObject((formular.engine.JavaObject)expr);
        } else if (expr instanceof formular.engine.ListExpression) {
            return new ListExpression((formular.engine.ListExpression)expr);
        } else {
            throw new IllegalArgumentException("Unsupported expression: " + expr);
        }
    }
}
