package jlisp.engine;

public class MissingArgumentException extends IndexOutOfBoundsException {
    public MissingArgumentException(String s) {
        super(s);
    }
}
