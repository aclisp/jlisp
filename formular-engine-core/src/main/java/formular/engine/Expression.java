package formular.engine;

public interface Expression {
    // Every expression has an ID, mainly for debugging purpose.
    int getId();
    Object getValue();
    boolean asBoolean();
}
