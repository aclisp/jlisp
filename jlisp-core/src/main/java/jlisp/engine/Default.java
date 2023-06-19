package jlisp.engine;

import jlisp.engine.function.*;
import jlisp.engine.function.io.PrintLine;
import jlisp.engine.function.logical.IsBlank;
import jlisp.engine.function.logical.IsNumber;
import jlisp.engine.function.logical.NotBlank;
import jlisp.engine.function.math.*;
import jlisp.engine.function.text.Number;
import jlisp.engine.function.text.*;
import jlisp.engine.function.time.*;

public class Default {
    public static Environment environment() {
        final Environment environment = new Environment();
        environment.put(Symbol.of("eval"), new Eval(environment, null, 0));
        environment.put(Symbol.of("+"), new Add());
        environment.put(Symbol.of("-"), new Subtract());
        environment.put(Symbol.of("*"), new Multiply());
        environment.put(Symbol.of("/"), new Divide());
        environment.put(Symbol.of("%"), new Remainder());
        environment.put(Symbol.of("<"), new LessThan());
        environment.put(Symbol.of(">"), new GreaterThan());
        environment.put(Symbol.of("<="), new LessOrEqual());
        environment.put(Symbol.of(">="), new GreaterOrEqual());
        environment.put(Symbol.of("is"), new Is());
        environment.put(Symbol.of("eq"), new Equal());
        environment.put(Symbol.of("neq"), new NotEqual());
        environment.put(Symbol.of("car"), new Car());
        environment.put(Symbol.of("cdr"), new Cdr());
        environment.put(Symbol.of("cons"), new Cons());
        environment.put(Symbol.of("append"), new Append());
        environment.put(Symbol.of("length"), new Length());
        environment.put(Symbol.of("contains"), new Contains());
        environment.put(Symbol.of("list"), new List());
        environment.put(Symbol.of("map"), new Map());
        environment.put(Symbol.of("reduce"), new Reduce());
        environment.put(Symbol.of("filter"), new Filter());
        environment.put(Symbol.of("nth"), new Nth());
        environment.put(Symbol.of("apply"), new Apply());
        environment.put(Symbol.of("format"), new Format());
        environment.put(Symbol.of("concat"), new Concat());
        environment.put(Symbol.of("not"), new Not());
        environment.put(Symbol.of("json-parse"), new JsonParse());
        environment.put(Symbol.of("json-ptr"), new JsonPointer());
        environment.put(Symbol.of("json-stringify"), new JsonStringify());
        environment.put(Symbol.of("make-hash-table"), new MakeHashTable());
        environment.put(Symbol.of("getf"), new GetField());
        environment.put(Symbol.of("setf"), new SetField());
        environment.put(Symbol.of("abs"), new Abs());
        environment.put(Symbol.of("pow"), new Pow());
        environment.put(Symbol.of("ceiling"), new Ceiling());
        environment.put(Symbol.of("floor"), new Floor());
        environment.put(Symbol.of("max"), new Max());
        environment.put(Symbol.of("min"), new Min());
        environment.put(Symbol.of("round"), new Round());
        environment.put(Symbol.of("not-blank"), new NotBlank());
        environment.put(Symbol.of("is-blank"), new IsBlank());
        environment.put(Symbol.of("is-number"), new IsNumber());
        environment.put(Symbol.of("add-quote"), new AddQuote());
        environment.put(Symbol.of("begins"), new Begins());
        environment.put(Symbol.of("ends"), new Ends());
        environment.put(Symbol.of("find"), new Find());
        environment.put(Symbol.of("left"), new Left());
        environment.put(Symbol.of("right"), new Right());
        environment.put(Symbol.of("left-pad"), new LeftPad());
        environment.put(Symbol.of("right-pad"), new RightPad());
        environment.put(Symbol.of("mid"), new Mid());
        environment.put(Symbol.of("subst"), new Substitute());
        environment.put(Symbol.of("trim"), new Trim());
        environment.put(Symbol.of("lower"), new Lower());
        environment.put(Symbol.of("upper"), new Upper());
        environment.put(Symbol.of("number"), new Number());
        environment.put(Symbol.of("text"), new Text());
        environment.put(Symbol.of("regex"), new Regex());
        environment.put(Symbol.of("now"), new Now());
        environment.put(Symbol.of("timenow"), new TimeNow());
        environment.put(Symbol.of("today"), new Today());
        environment.put(Symbol.of("weekday"), new Weekday());
        environment.put(Symbol.of("add-month"), new AddMonth());
        environment.put(Symbol.of("add-year"), new AddYear());
        environment.put(Symbol.of("add-day"), new AddDay());
        environment.put(Symbol.of("add-week"), new AddWeek());
        environment.put(Symbol.of("add-hour"), new AddHour());
        environment.put(Symbol.of("add-minute"), new AddMinute());
        environment.put(Symbol.of("add-second"), new AddSecond());
        environment.put(Symbol.of("seconds-between"), new SecondsBetween());
        environment.put(Symbol.of("days-between"), new DaysBetween());
        environment.put(Symbol.of("println!"), new PrintLine());
        environment.alias(Symbol.of("eq"), Symbol.of("="));
        environment.alias(Symbol.of("eq"), Symbol.of("=="));
        environment.alias(Symbol.of("eq"), Symbol.of("equal"));
        environment.alias(Symbol.of("neq"), Symbol.of("!="));
        environment.alias(Symbol.of("neq"), Symbol.of("<>"));
        environment.alias(Symbol.of("+"), Symbol.of("加"));
        environment.alias(Symbol.of("-"), Symbol.of("减"));
        environment.alias(Symbol.of("*"), Symbol.of("乘"));
        environment.alias(Symbol.of("/"), Symbol.of("除"));
        environment.alias(Symbol.of("%"), Symbol.of("余数"));
        environment.alias(Symbol.of("<"), Symbol.of("小于"));
        environment.alias(Symbol.of(">"), Symbol.of("大于"));
        environment.alias(Symbol.of("<="), Symbol.of("小于或等于"));
        environment.alias(Symbol.of("<="), Symbol.of("不大于"));
        environment.alias(Symbol.of(">="), Symbol.of("大于或等于"));
        environment.alias(Symbol.of(">="), Symbol.of("不小于"));
        environment.alias(Symbol.of("not"), Symbol.of("否定"));
        environment.alias(Symbol.of("contains"), Symbol.of("包含"));
        environment.alias(Symbol.of("length"), Symbol.of("计数"));
        environment.alias(Symbol.of("setf"), Symbol.of("设置字段"));
        environment.alias(Symbol.of("getf"), Symbol.of("获取字段"));
        environment.alias(Symbol.of("abs"), Symbol.of("绝对值"));
        return environment;
    }
}
