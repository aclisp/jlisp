package jlisp.parser.json;

import jlisp.engine.Expression;

import java.util.ArrayList;
import java.util.List;

public class ListExpression implements Node {
    final static String type = "list";
    private jlisp.engine.ListExpression value;
    public ListExpression(jlisp.engine.ListExpression v) {
        value = v;
    }
    public ListExpression() { }
    public String getType() {
        return type;
    }
    public Expression getExpression() {
        return value;
    }
    public void setValue(List<Node> nodes) {
        jlisp.engine.ListExpression list = new jlisp.engine.ListExpression(nodes.size());
        for (Node node : nodes) {
            list.add(node.getExpression());
        }
        value = list;
    }
    public List<Node> getValue() {
        List<Node> list = new ArrayList<>(value.size());
        for (Expression expr : value) {
            list.add(Node.of(expr));
        }
        return list;
    }
    public void setId(int id) {
        value.setId(id);
    }
    public int getId() {
        return value.getId();
    }
}
