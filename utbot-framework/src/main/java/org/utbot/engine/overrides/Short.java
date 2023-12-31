package org.utbot.engine.overrides;

import org.utbot.api.annotation.UtClassMock;
import org.utbot.engine.overrides.strings.UtStringBuilder;

import static org.utbot.api.mock.UtMock.assume;
import static org.utbot.api.mock.UtMock.assumeOrExecuteConcretely;
import static org.utbot.engine.overrides.UtLogicMock.ite;
import static org.utbot.engine.overrides.UtLogicMock.less;
import static org.utbot.engine.overrides.UtOverrideMock.executeConcretely;

@UtClassMock(target = java.lang.Short.class, internalUsage = true)
public class Short {
    @SuppressWarnings({"UnnecessaryBoxing", "unused", "deprecation"})
    public static java.lang.Short valueOf(short x) {
        return new java.lang.Short(x);
    }

    @SuppressWarnings({"unused", "DuplicatedCode", "IfStatementWithIdenticalBranches"})
    public static java.lang.Short parseShort(String s, int radix) {
        if (s == null) {
            throw new NumberFormatException();
        }
        if (radix < 2) { // MIN_RADIX
            throw new NumberFormatException();
        }
        if (radix > 36) { // MAX_RADIX
            throw new NumberFormatException();
        }
        if (s.length() == 0) {
            throw new NumberFormatException();
        }
        if ((s.charAt(0) == '-' || s.charAt(0) == '+') && s.length() == 1) {
            throw new NumberFormatException();
        }
        assumeOrExecuteConcretely(s.length() <= 10);
        // we need two branches to add more options for concrete executor to find both branches
        if (s.charAt(0) == '-') {
            executeConcretely();
            return java.lang.Short.parseShort(s);
        } else {
            executeConcretely();
            return java.lang.Short.parseShort(s);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static String toString(short s) {
        if (s == -32768) {
            return "-32768";
        }

        if (s == 0) {
            return "0";
        }

        // assumes are placed here to limit search space of solver
        // and reduce time of solving queries with bv2int expressions
        assume(s <= 32767);
        assume(s > -32768);
        assume(s != 0);

        // isNegative = s < 0
        boolean isNegative = less(s, (short) 0);
        String prefix = ite(isNegative, "-", "");

        int value = ite(isNegative, (short) -s, s);
        char[] reversed = new char[5];
        int offset = 0;
        while (value > 0) {
            reversed[offset] = (char) ('0' + (value % 10));
            value /= 10;
            offset++;
        }

        char[] buffer = new char[offset];
        int counter = 0;
        while (offset > 0) {
            offset--;
            buffer[counter++] = reversed[offset];
        }
        return prefix + new String(buffer);
    }
}
