package formular.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final static List<String> specialForms = Arrays.asList(
        Engine.DEF.id,      Engine.DEF.alias,
        Engine.LAMBDA.id,   Engine.LAMBDA.alias,
        Engine.IF.id,       Engine.IF.alias,
        Engine.QUOTE.id,    Engine.QUOTE.alias,
        Engine.PROGN.id,    Engine.PROGN.alias,
        Engine.LET_STAR.id, Engine.LET_STAR.alias
    );
    public List<String> complete(String prefix) {
        List<String> result = new ArrayList<>();
        for (Symbol symbol : keySet()) {
            String name = symbol.getValue();
            if (name.startsWith(prefix)) {
                result.add(name);
            }
        }
        for (String name : specialForms) {
            if (name.startsWith(prefix)) {
                result.add(name);
            }
        }
        Collections.sort(result);
        return Collections.unmodifiableList(result);
    }
}
