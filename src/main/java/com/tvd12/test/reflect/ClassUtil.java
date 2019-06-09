package com.tvd12.test.reflect;

public final class ClassUtil {
	
	private static final char DOT = '.';
	
	private ClassUtil() {
	}
	
	public static String shortClassName(String className) {
		int lastIndex = className.lastIndexOf(DOT);
		if(lastIndex <= 0)
			return className;
		String classSimpleName = className.substring(lastIndex + 1);
		String packageName = className.substring(0, lastIndex);
		int acceptIndex = packageName.lastIndexOf(DOT);
		if(acceptIndex <= 0)
			return className;
		String acceptPackageName = packageName.substring(acceptIndex + 1, lastIndex);
		String accept = acceptPackageName + DOT + classSimpleName;
		return accept;
	}

}
