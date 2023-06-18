package jlisp.engine.function.text;

import java.util.Arrays;

import jlisp.engine.Expression;
import jlisp.engine.Function;
import jlisp.engine.ListExpression;
import jlisp.engine.Util;

/**
 * Inserts characters you specify to the left-side of a text string.
 */
public class LeftPad extends Function {

    private static final int PAD_LIMIT = 8192;

    @Override
    public Expression invoke(ListExpression args) throws Exception {
        String text = args.get(0).asText("");
        text = stripStart(text, null);
        int paddedLength = args.get(1).asNumber(0).intValue();
        String padString = " ";
        if (args.size() > 2) {
            padString = args.get(2).asText(" ");
        }

        String result;
        if (text.length() >= paddedLength) {
            result = text.substring(0, paddedLength);
            return Expression.of(result);
        }

        result = leftPad(text, paddedLength, padString);
        return Expression.of(result);
    }

    private static String leftPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return repeat(padChar, pads).concat(str);
    }

    private static String leftPad(final String str, final int size, String padStr) {
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
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        }
        if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        }
        final char[] padding = new char[pads];
        final char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; i++) {
            padding[i] = padChars[i % padLen];
        }
        return new String(padding).concat(str);
    }

    private static String repeat(final char ch, final int repeat) {
        if (repeat <= 0) {
            return "";
        }
        final char[] buf = new char[repeat];
        Arrays.fill(buf, ch);
        return new String(buf);
    }

    private static String stripStart(final String str, final String stripChars) {
        final int strLen = str.length();
        if (strLen == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while (start != strLen && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (start != strLen && stripChars.indexOf(str.charAt(start)) != -1) {
                start++;
            }
        }
        return str.substring(start);
    }

}
