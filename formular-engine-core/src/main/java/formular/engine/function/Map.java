package formular.engine.function;

import java.util.Iterator;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.JsonNode;

import formular.engine.Array;
import formular.engine.Expression;
import formular.engine.Function;
import formular.engine.ListExpression;
import formular.engine.Util;

public class Map extends Function {

    public Expression invoke(ListExpression args) throws Exception {
        Function function = (Function) args.get(0);
        Expression sequence = args.get(1);
        ListExpression result;
        if (sequence instanceof ListExpression) {
            result = mapList(function, sequence);
        } else if (sequence instanceof Array) {
            result = mapArray(function, sequence);
        } else if (sequence.getValue() instanceof LinkedHashMap) {
            result = mapHashTable(function, sequence);
        } else if (sequence.getValue() instanceof JsonNode) {
            result = mapJsonNode(function, sequence);
        } else {
            throw new IllegalArgumentException("Unsupported sequence: " + sequence);
        }
        return result;
    }

    private ListExpression mapJsonNode(Function function, Expression sequence) throws Exception {
        JsonNode jsonNode = (JsonNode) sequence.getValue();
        ListExpression result = new ListExpression(jsonNode.size());
        if (jsonNode.isArray()) {
            for (int i = 0; i < jsonNode.size(); i++) {
                ListExpression arg = new ListExpression();
                arg.add(Util.expressionOf(jsonNode.get(i)));
                arg.add(Util.expressionOf(i));
                result.add(function.invoke(arg));
            }
        } else if (jsonNode.isObject()) {
            Iterator<java.util.Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            while (fields.hasNext()) {
                java.util.Map.Entry<String, JsonNode> field = fields.next();
                ListExpression arg = new ListExpression();
                arg.add(Util.expressionOf(field.getKey()));
                arg.add(Util.expressionOf(field.getValue()));
                result.add(function.invoke(arg));
            }
        } else {
            throw new IllegalArgumentException("Unsupported sequence: " + sequence);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private ListExpression mapHashTable(Function function, Expression sequence) throws Exception {
        LinkedHashMap<Object, Object> hashTable = (LinkedHashMap<Object, Object>) sequence.getValue();
        ListExpression result = new ListExpression(hashTable.size());
        for (java.util.Map.Entry<Object, Object> entry : hashTable.entrySet()) {
            ListExpression arg = new ListExpression();
            arg.add(Util.expressionOf(entry.getKey()));
            arg.add(Util.expressionOf(entry.getValue()));
            result.add(function.invoke(arg));
        }
        return result;
    }

    private ListExpression mapArray(Function function, Expression sequence) throws Exception {
        Array array = (Array) sequence;
        ListExpression result = new ListExpression(array.length());
        for (int i = 0; i < array.length(); i++) {
            ListExpression arg = new ListExpression();
            arg.add(Util.expressionOf(array.get(i)));
            arg.add(Util.expressionOf(i));
            result.add(function.invoke(arg));
        }
        return result;
    }

    private ListExpression mapList(Function function, Expression sequence) throws Exception {
        ListExpression list = (ListExpression) sequence;
        ListExpression result = new ListExpression(list.size());
        for (int i = 0; i < list.size(); i++) {
            ListExpression arg = new ListExpression();
            arg.add(list.get(i));
            arg.add(Util.expressionOf(i));
            result.add(function.invoke(arg));
        }
        return result;
    }

}
