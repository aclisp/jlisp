package jlisp.engine.function;

import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;

import jlisp.engine.Array;
import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

public class Map extends Function {

    public Expression invoke(ListExpression args) throws Exception {
        Function function = (Function) args.get(0);
        Expression sequence = args.get(1);
        ListExpression result;
        if (sequence instanceof ListExpression) {
            result = mapList(function, sequence);
        } else if (sequence instanceof Array) {
            result = mapArray(function, sequence);
        } else if (sequence.getValue() instanceof java.util.Map) {
            result = mapMap(function, sequence);
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
                arg.add(Expression.of(jsonNode.get(i)));
                arg.add(Expression.of(i));
                result.add(function.invoke(arg));
            }
        } else if (jsonNode.isObject()) {
            Iterator<java.util.Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            while (fields.hasNext()) {
                java.util.Map.Entry<String, JsonNode> field = fields.next();
                ListExpression arg = new ListExpression();
                arg.add(Expression.of(field.getKey()));
                arg.add(Expression.of(field.getValue()));
                result.add(function.invoke(arg));
            }
        } else {
            throw new IllegalArgumentException("Unsupported sequence: " + sequence);
        }
        return result;
    }

    private ListExpression mapMap(Function function, Expression sequence) throws Exception {
        java.util.Map<?, ?> map = (java.util.Map<?, ?>) sequence.getValue();
        ListExpression result = new ListExpression(map.size());
        for (java.util.Map.Entry<?, ?> entry : map.entrySet()) {
            ListExpression arg = new ListExpression();
            arg.add(Expression.of(entry.getKey()));
            arg.add(Expression.of(entry.getValue()));
            result.add(function.invoke(arg));
        }
        return result;
    }

    private ListExpression mapArray(Function function, Expression sequence) throws Exception {
        Array array = (Array) sequence;
        ListExpression result = new ListExpression(array.length());
        for (int i = 0; i < array.length(); i++) {
            ListExpression arg = new ListExpression();
            arg.add(Expression.of(array.get(i)));
            arg.add(Expression.of(i));
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
            arg.add(Expression.of(i));
            result.add(function.invoke(arg));
        }
        return result;
    }

}
