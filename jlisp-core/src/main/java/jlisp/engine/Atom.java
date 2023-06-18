package jlisp.engine;

public abstract class Atom<T> implements Expression {
    protected int id;
    protected T value;
    public T getValue() {
        return value;
    }
    public String toString() {
        return value instanceof String ? "\"" + Util.escapeString((String) value) + '"' : String.valueOf(value);
    }
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
    public boolean equals(Object o) {
        if (o != null && this.getClass().equals(o.getClass())) {
            Object oVal = ((Atom<?>) o).value;
            return value == oVal || (value != null && value.equals(oVal));
        } else {
            return false;
        }
    }
    public boolean asBoolean() {
        return value != null && !Boolean.FALSE.equals(value);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
