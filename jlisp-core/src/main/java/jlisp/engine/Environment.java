package jlisp.engine;

import jlisp.engine.specialform.*;

import java.util.*;

public class Environment extends HashMap<Symbol, Expression> {
    private HashMap<Symbol, SpecialForm> specialForms = new HashMap<>();
    public Environment() {
        super();
        specialForms.put(Symbol.of("def"), new Define());
        specialForms.put(Symbol.of("lambda"), new LambdaFactory());
        specialForms.put(Symbol.of("if"), new If());
        specialForms.put(Symbol.of("quote"), new Quote());
        specialForms.put(Symbol.of("progn"), new Program());
        specialForms.put(Symbol.of("let*"), new LetStar());
        specialForms.put(Symbol.of("cond"), new Condition());
        specialForms.put(Symbol.of("loop"), new Loop());
        specialForms.put(Symbol.of("break"), new Break());
        aliasSpecialForm(Symbol.of("lambda"), Symbol.of("func"));
        aliasSpecialForm(Symbol.of("def"), Symbol.of("定义"));
        aliasSpecialForm(Symbol.of("if"), Symbol.of("如果"));
        aliasSpecialForm(Symbol.of("cond"), Symbol.of("选择"));
    }
    private Environment(Map<Symbol, Expression> env) {
        super(env);
    }
    public Environment copy() {
        Environment copy = new Environment(this);
        copy.specialForms = this.specialForms;
        return copy;
    }
    public Expression alias(Symbol from, Symbol to) {
        return put(to, get(from));
    }
    public SpecialForm aliasSpecialForm(Symbol from, Symbol to) {
        return specialForms.put(to, specialForms.get(from));
    }
    SpecialForm getSpecialForm(Symbol symbol) {
        return specialForms.get(symbol);
    }
    public SpecialForm putSpecialForm(Symbol symbol, SpecialForm specialForm) {
        return specialForms.put(symbol, specialForm);
    }
    public List<String> complete(String prefix) {
        List<String> result = new ArrayList<>();
        for (Symbol symbol : keySet()) {
            String name = symbol.getValue();
            if (name.startsWith(prefix)) {
                result.add(name);
            }
        }
        for (Symbol symbol : specialForms.keySet()) {
            String name = symbol.getValue();
            if (name.startsWith(prefix)) {
                result.add(name);
            }
        }
        Collections.sort(result);
        return Collections.unmodifiableList(result);
    }
}
