package jlisp.runner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jlisp.engine.Default;
import jlisp.engine.Environment;

public class DateTimeTest {
    private Environment env;

    @BeforeEach
    public void setUp() {
        env = Default.environment();
    }

    @Test
    public void testNow() throws Exception {
        String now = (String) Runner.execute("(now)", env).getValue();
        System.out.println(now);
    }

    @Test
    public void testWeekday() throws Exception {
        assertEquals(6, Runner.execute("(weekday \"2022-09-03\")", env).getValue());
    }

    @Test
    public void testAddMonth() throws Exception {
        assertEquals("2018-02-20", Runner.execute("(add-month \"2017-09-20\" 5)", env).getValue());
        assertEquals("2018-02-28", Runner.execute("(add-month \"2017-09-30\" 5)", env).getValue());
        assertEquals("2017-06-30", Runner.execute("(add-month \"2017-08-31\" -2)", env).getValue());
    }

    @Test
    public void testAddYear() throws Exception {
        assertEquals("2022-09-20", Runner.execute("(add-year \"2017-09-20\" 5)", env).getValue());
        assertEquals("2012-09-30", Runner.execute("(add-year \"2017-09-30\" -5)", env).getValue());
        assertEquals("2001-02-28", Runner.execute("(add-year \"2000-02-29\" 1)", env).getValue());
    }

    @Test
    public void testAddDay() throws Exception {
        assertEquals("2017-09-25", Runner.execute("(add-day \"2017-09-20\" 5)", env).getValue());
        assertEquals("2017-10-05", Runner.execute("(add-day \"2017-09-30\" 5)", env).getValue());
        assertEquals("2017-08-29", Runner.execute("(add-day \"2017-08-31\" -2)", env).getValue());
    }

    @Test
    public void testAddWeek() throws Exception {
        assertEquals("2022-08-27", Runner.execute("(add-week \"2022-09-03\" -1)", env).getValue());
    }

    @Test
    public void testAddHour() throws Exception {
        assertEquals("2022-09-04 02:24:01", Runner.execute("(add-hour \"2022-09-03 22:24:01\" 4)", env).getValue());
    }

    @Test
    public void testAddMinute() throws Exception {
        assertEquals("2022-09-03 23:01:01", Runner.execute("(add-minute \"2022-09-03 22:57:01\" 4)", env).getValue());
    }

    @Test
    public void testAddSecond() throws Exception {
        assertEquals("2022-09-03 22:58:06", Runner.execute("(add-second \"2022-09-03 22:57:01\" 65)", env).getValue());
    }

    @Test
    public void testSecondsBetween() throws Exception {
        assertEquals(682, Runner.execute("(seconds-between \"2022-09-03 22:51:01\" \"2022-09-03 23:02:23\")", env).getValue());
        assertEquals(-12, Runner.execute("(seconds-between \"2022-09-03 22:51:01\" \"2022-09-03 22:50:49\")", env).getValue());
    }

    @Test
    public void testDaysBetween() throws Exception {
        assertEquals(60, Runner.execute("(days-between \"2022-09-03\" \"2022-11-02\")", env).getValue());
        assertEquals(-2, Runner.execute("(days-between \"2022-09-01\" \"2022-08-30\")", env).getValue());
    }
}
