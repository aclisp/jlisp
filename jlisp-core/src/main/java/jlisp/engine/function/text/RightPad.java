package jlisp.engine.function.text;

import java.util.Arrays;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

/**
 * Inserts characters that you specify to the right-side of a text string.
 */
public class RightPad extends Function {

    private static final int PAD_LIMIT = 8192;

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        text = stripEnd(text, null);
        int paddedLength = args.get(1).asNumber(0).intValue();
        String padString = " ";
        if (args.size() > 2) {
            padString = args.get(2).asText(" ");
        }

        String result;
        if (text.length() >= paddedLength) {
            result = text.substring(text.length()-paddedLength, text.length());
            return Expression.of(result);
        }

        result = rightPad(text, paddedLength, padString);
        return Expression.of(result);
    }

    private static String rightPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return rightPad(str, size, String.valueOf(padChar));
        }
        return str.concat(repeat(padChar, pads));
    }

    private static String rightPad(final String str, final int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (padStr.isEmpty()) {
            padStr = " ";
        }
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return str.concat(padStr);
        }
        if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        }
        final char[] padding = new char[pads];
        final char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; i++) {
            padding[i] = padChars[i % padLen];
        }
        return str.concat(new String(padding));
    }

    private static String repeat(final char ch, final int repeat) {
        if (repeat <= 0) {
            return "";
        }
        final char[] buf = new char[repeat];
        Arrays.fill(buf, ch);
        return new String(buf);
    }

    private static String stripEnd(final String str, final String stripChars) {
        int end = str.length();
        if (end == 0) {
            return str;
        }

        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                end--;
            }
        }
        return str.substring(0, end);
    }

}
