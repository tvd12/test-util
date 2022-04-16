package com.tvd12.test.reflect;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * Support to read stack trace
 * 
 * @author tvd12
 *
 */
public final class StackTraceUtil {

    private static final String UNKNOWN = "[Unknown]";

    private StackTraceUtil() {}

    public static String stackTraceToString(Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        StringBuffer buffer = sw.getBuffer();
        return buffer.toString();
    }

    public static String getCallerInfo(StackTraceElement[] stackTrace, int callerIndex) {
        if (stackTrace.length <= callerIndex) {
            return UNKNOWN;
        }
        StackTraceElement element = stackTrace[callerIndex];
        String callerClassName = element.getClassName();
        String callerSimpleName = ClassUtil.shortClassName(callerClassName);
        return callerSimpleName + ":" + element.getLineNumber();
    }
}
