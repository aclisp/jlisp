package formular.engine;

public class Symbol extends Atom<String> {
    private Symbol() {}
    public static Symbol of(String value) {
        Symbol symbol = new Symbol();
        symbol.value = value;
        return symbol;
    }
    public String toString() {
        return value;
    }
}
