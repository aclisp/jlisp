package formular.parser.json;

import java.util.ArrayList;
import java.util.List;

import formular.engine.Expression;

public class ListExpression implements Node {
    final static String type = "list";
    private formular.engine.ListExpression value;
    public ListExpression(formular.engine.ListExpression v) {
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
        formular.engine.ListExpression list = new formular.engine.ListExpression(nodes.size());
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
