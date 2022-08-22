package formular.engine;

public class JavaObject extends Atom<Object> {
    private JavaObject() {}
    public static JavaObject of(Object value) {
        JavaObject jobj = new JavaObject();
        jobj.value = value;
        return jobj;
    }
}
