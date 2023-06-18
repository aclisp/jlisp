package jlisp.parser.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jlisp.engine.Expression;

@JsonDeserialize(using = NodeDeserializer.class)
public interface Node {
    // Actually it's the node's expression's id.
    int getId();
    String getType();
    @JsonIgnore Expression getExpression();
    static Node of(Expression expr) {
        if (expr instanceof jlisp.engine.Symbol) {
            return new Symbol((jlisp.engine.Symbol)expr);
        } else if (expr instanceof jlisp.engine.Array) {
            return new Array((jlisp.engine.Array)expr);
        } else if (expr instanceof jlisp.engine.JavaObject) {
            return new JavaObject((jlisp.engine.JavaObject)expr);
        } else if (expr instanceof jlisp.engine.ListExpression) {
            return new ListExpression((jlisp.engine.ListExpression)expr);
        } else {
            throw new IllegalArgumentException("Unsupported expression: " + expr);
        }
    }
}
