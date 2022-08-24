package formular.engine;

import java.util.ArrayList;
import java.util.List;

public class Array extends Atom<Object> {
    private Array() {}
    public static Array of(Object value) {
        if (value == null || !value.getClass().isArray()) {
            throw new IllegalArgumentException("Value is not an array: " + value);
        }
        Array array = new Array();
        array.value = value;
        array.id = array.hashCode();
        return array;
    }
    public static Array from(List<Object> values) {
        Class<?> arrayClass = getClass(values);
        Object value;
        if (Integer.class.equals(arrayClass)) {
            int[] intArray = new int[values.size()];
            for (int i = 0; i < values.size(); i++) {
                intArray[i] = (int) values.get(i);
            }
            value = intArray;
        } else if (Double.class.equals(arrayClass)) {
            double[] doubleArray = new double[values.size()];
            for (int i = 0; i < values.size(); i++) {
                doubleArray[i] = (double) values.get(i);
            }
            value = doubleArray;
        } else {
            value = values.toArray();
        }
        Array array = new Array();
        array.value = value;
        array.id = array.hashCode();
        return array;
    }
    public Object get(int index) {
        if (value instanceof int[]) {
            return ((int[]) value)[index];
        } else if (value instanceof double[]) {
            return ((double[]) value)[index];
        } else {
            return ((Object[]) value)[index];
        }
    }
    public int length() {
        if (value instanceof int[]) {
            return ((int[]) value).length;
        } else if (value instanceof double[]) {
            return ((double[]) value).length;
        } else {
            return ((Object[]) value).length;
        }
    }
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        if (value instanceof int[]) {
            for (int i : ((int[]) value)) {
                builder.append(i).append(' ');
            }
        } else if (value instanceof double[]) {
            for (double d : ((double[]) value)) {
                builder.append(d).append(' ');
            }
        } else {
            for (Object o : ((Object[]) value)) {
                builder.append(o).append(' ');
            }
        }
        if (builder.charAt(builder.length() - 1) == ' ') {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append(']');
        return builder.toString();
    }
    public List<Object> toList() {
        List<Object> list = new ArrayList<Object>(length());
        if (value instanceof int[]) {
            for (int i : ((int[]) value)) {
                list.add(i);
            }
        } else if (value instanceof double[]) {
            for (double d : ((double[]) value)) {
                list.add(d);
            }
        } else {
            for (Object o : ((Object[]) value)) {
                list.add(o);
            }
        }
        return list;
    }
    private static Class<?> getClass(List<Object> values) {
        if (values.isEmpty()) {
            return Object.class;
        }
        Class<?> result = values.get(0).getClass();
        for (Object value : values) {
            if (!value.getClass().equals(result)) {
                return Object.class;
            }
        }
        return result;
    }
}
