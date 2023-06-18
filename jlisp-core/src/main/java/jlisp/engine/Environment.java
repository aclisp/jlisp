package jlisp.engine;

import java.util.*;

public class Environment extends HashMap<Symbol, Expression> {
    public Environment() {
        super();
    }
    public Environment(Map<Symbol, Expression> env) {
        super(env);
    }
    public Expression alias(Symbol from, Symbol to) {
        return put(to, get(from));
    }
    public List<String> complete(String prefix) {
        List<String> result = new ArrayList<>();
        for (Symbol symbol : keySet()) {
            String name = symbol.getValue();
            if (name.startsWith(prefix)) {
                result.add(name);
            }
        }
        for (Symbol symbol : Engine.specialForms.keySet()) {
            String name = symbol.getValue();
            if (name.startsWith(prefix)) {
                result.add(name);
            }
        }
        Collections.sort(result);
        return Collections.unmodifiableList(result);
    }
}
