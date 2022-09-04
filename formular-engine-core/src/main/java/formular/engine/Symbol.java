package formular.engine;

public class Symbol extends Atom<String> {
    private Symbol() {}
    public static Symbol of(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value can not be null");
        }
        Symbol symbol = new Symbol();
        symbol.value = value;
        symbol.id = symbol.hashCode();
        return symbol;
    }
    public String toString() {
        return value;
    }
}
