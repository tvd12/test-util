package com.tvd12.test.reflect;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class StackTraceUtil {

	private static final String UNKNOWN = "[Unknown]";
	
	private StackTraceUtil() {
	}
	
	public static String stackTraceToString(Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        StringBuffer buffer = sw.getBuffer();
        String string = buffer.toString();
        return string;
    }
	
	public static String getCallerInfo(StackTraceElement[] stackTrace, int callerIndex) {
		if(stackTrace.length <= callerIndex)
			return UNKNOWN;
		StackTraceElement element = stackTrace[callerIndex];
		String callerClassName = element.getClassName();
		String callerSimpleName = ClassUtil.shortClassName(callerClassName);
		String info = callerSimpleName + ":" + element.getLineNumber();
		return info;
	}
	
}
